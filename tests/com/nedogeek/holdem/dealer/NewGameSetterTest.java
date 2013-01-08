package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameSettings;
import com.nedogeek.holdem.PlayerStatus;
import com.nedogeek.holdem.gamingStuff.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 07.12.12
 * Time: 7:24
 */
public class NewGameSetterTest {
    private NewGameSetter newGameSetter;

    private Dealer dealerMock;
    private PlayersManager playersManagerMock;
    private MoveManager moveManagerMock;
    private List<Player> players;
    private Player firstPlayerMock;
    private Player secondPlayerMock;
    private Player thirdPlayerMock;

    @Before
    public void setUp() throws Exception {
        dealerMock = mock(Dealer.class);
        resetPlayerManagerMock();
        resetMoveManagerMock();

        newGameSetter = new NewGameSetter(dealerMock, playersManagerMock, moveManagerMock);

    }

    private void resetMoveManagerMock() {
        moveManagerMock = mock(MoveManager.class);
    }

    private void resetPlayerManagerMock() {
        playersManagerMock = mock(PlayersManager.class);

        resetPlayers();

        when(playersManagerMock.getPlayers()).thenReturn(players);
        setDealerIs(0);
    }

    private void resetPlayers() {
        firstPlayerMock = mock(Player.class);
        secondPlayerMock = mock(Player.class);
        thirdPlayerMock = mock(Player.class);

        players = new ArrayList<Player>();
        players.add(firstPlayerMock);
        players.add(secondPlayerMock);
        players.add(thirdPlayerMock);

        for (Player player : players) {
            setPlayerStatus(player, PlayerStatus.NotMoved);
        }

        players.remove(thirdPlayerMock);

    }

    private void setDealerIs(int dealerNumber) {
        if (dealerNumber == 0) {
            when(playersManagerMock.smallBlindPlayer()).thenReturn(secondPlayerMock);
            when(playersManagerMock.bigBlindPlayer()).thenReturn(firstPlayerMock);
        }

        if (dealerNumber == 1) {
            when(playersManagerMock.smallBlindPlayer()).thenReturn(firstPlayerMock);
            when(playersManagerMock.bigBlindPlayer()).thenReturn(secondPlayerMock);
        }
    }

    private void setPlayerStatus(Player player, PlayerStatus playerStatus) {
        when(player.getStatus()).thenReturn(playerStatus);
    }

    @Test
    public void shouldSetFirstPlayerStatusNotMovedWhenDefaultNewGameSet() throws Exception {
        newGameSetter.setNewGame();

        verify(firstPlayerMock).setStatus(PlayerStatus.NotMoved);
    }

    @Test
    public void shouldSetSecondPlayerStatusNotMovedWhenDefaultNewGameSet() throws Exception {
        newGameSetter.setNewGame();

        verify(secondPlayerMock).setStatus(PlayerStatus.NotMoved);
    }

    @Test
    public void shouldNotLostPlayerSetNotMoves() throws Exception {
        players.add(thirdPlayerMock);

        setPlayerStatus(firstPlayerMock, PlayerStatus.Lost);

        newGameSetter.setNewGame();

        verify(firstPlayerMock, never()).setStatus(PlayerStatus.NotMoved);
    }

    @Test
    public void shouldBeGivenCardsToFirstPlayerWhenDefaultNewGame() throws Exception {
        newGameSetter.setNewGame();

        verify(dealerMock).giveCardsToPlayer(firstPlayerMock);
    }

    @Test
    public void shouldBeGivenCardsToSecondPlayerWhenDefaultNewGame() throws Exception {
        newGameSetter.setNewGame();

        verify(dealerMock).giveCardsToPlayer(secondPlayerMock);
    }

    @Test
    public void shouldBeGivenCardsToThirdPlayerWhen3PlayersNewGame() throws Exception {
        players.add(thirdPlayerMock);

        newGameSetter.setNewGame();

        verify(dealerMock).giveCardsToPlayer(thirdPlayerMock);
    }

    @Test
    public void shouldNotBeGivenCardsToThirdPlayerWhenDefaultNewGame() throws Exception {
        newGameSetter.setNewGame();

        verify(dealerMock, never()).giveCardsToPlayer(thirdPlayerMock);
    }

    @Test
    public void shouldNotBeGivenCardsToThirdPlayerWhen3PlayersAnd3IsLostNewGame() throws Exception {
        setPlayerStatus(secondPlayerMock, PlayerStatus.Lost);
        players.add(thirdPlayerMock);

        newGameSetter.setNewGame();

        verify(dealerMock, never()).giveCardsToPlayer(secondPlayerMock);
    }

    @Test
    public void shouldChangeDealerInPlayersManagerMockWhenNewGameSetterSetNewGame() throws Exception {
        newGameSetter.setNewGame();

        verify(playersManagerMock).changeDealer();
    }

    @Test
    public void shouldSmallBlindAddedToPotWhenGameStarted() throws Exception {
        newGameSetter.setNewGame();

        verify(moveManagerMock).makeInitialBet(secondPlayerMock, GameSettings.SMALL_BLIND_AT_START);
    }

    @Test
    public void shouldBigBlindAddedToPotWhenGameStarted() throws Exception {
        newGameSetter.setNewGame();

        verify(moveManagerMock).makeInitialBet(firstPlayerMock, GameSettings.SMALL_BLIND_AT_START * 2);
    }

    @Test
    public void shouldFirstPlayerSmallBlindAddedToPotWhenGameStartedAndDealerIsSecondPlayer() throws Exception {
        setDealerIs(1);
        newGameSetter.setNewGame();

        verify(moveManagerMock).makeInitialBet(firstPlayerMock, GameSettings.SMALL_BLIND_AT_START);
    }

    @Test
    public void shouldSecondPlayerBigBlindAddedToPotWhenGameStartedAndDealerIsSecondPlayer() throws Exception {
        setDealerIs(1);
        newGameSetter.setNewGame();

        verify(moveManagerMock).makeInitialBet(secondPlayerMock, GameSettings.SMALL_BLIND_AT_START * 2);
    }

    @Test
    public void shouldNotSetGameRound1WhenTickGameRoundIs1() throws Exception {
        newGameSetter.setNewGame();

        verify(dealerMock).setNextGameRound();
    }

    @Test
    public void shouldShuffleCardsWhenStartGaming() throws Exception {
        newGameSetter.setNewGame();

        verify(dealerMock).resetCards();
    }

    @Test
    public void shouldSetGameRound1WhenTick() throws Exception {
        newGameSetter.setNewGame();

        verify(dealerMock).setNextGameRound();
    }


}
