package com.nedogeek.holdem.dealer;

import com.nedogeek.holdem.GameRound;
import com.nedogeek.holdem.GameStatus;
import com.nedogeek.holdem.gamingStuff.Player;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * User: Konstantin Demishev
 * Date: 22.11.12
 * Time: 0:52
 */
public class DealerTest {
    private final GameStatus DEFAULT_GAME_STATUS = GameStatus.NOT_READY;
    private final GameRound DEFAULT_GAME_ROUND = GameRound.INITIAL;

    private Dealer dealer;

    private MoveManager moveManagerMock;
    private NewGameSetter newGameSetterMock;
    private PlayersManager playersManagerMock;
    private GameCycleManager gameCycleManagerMock;
    private EndGameManager endGameManagerMock;

    private Player mover;

    @Before
    public void setUp() throws Exception {
        moveManagerMock = mock(MoveManager.class);
        newGameSetterMock = mock(NewGameSetter.class);
        resetPlayerManager();
        gameCycleManagerMock = mock(GameCycleManager.class);
        endGameManagerMock = mock(EndGameManager.class);

        dealer = new Dealer(moveManagerMock, newGameSetterMock, playersManagerMock,
                gameCycleManagerMock, endGameManagerMock, DEFAULT_GAME_STATUS, DEFAULT_GAME_ROUND);
    }

    private void resetPlayerManager() {
        playersManagerMock = mock(PlayersManager.class);
        mover = mock(Player.class);

        when(playersManagerMock.getMover()).thenReturn(mover);
    }

    private void setGameStatus(GameStatus gameStatus) {
        dealer = new Dealer(moveManagerMock, newGameSetterMock, playersManagerMock,
                gameCycleManagerMock, endGameManagerMock, gameStatus, DEFAULT_GAME_ROUND);
    }

    private void setGameData(GameStatus gameStatus, GameRound gameRound) {
        dealer = new Dealer(moveManagerMock, newGameSetterMock, playersManagerMock,
                gameCycleManagerMock, endGameManagerMock, gameStatus, gameRound);
    }

    @Test
    public void shouldGameCycleManagerEndCycleWhenGameStatusCYCLE_ENDED() throws Exception {
        setGameStatus(GameStatus.CYCLE_ENDED);

        dealer.tick();

        verify(gameCycleManagerMock).endGameCycle();
    }

    @Test
    public void shouldNoPlayerManagerHasAvailableMovesWhenGameStatusSTARTEDAndGameRoundINITIAL() throws Exception {
        setGameData(GameStatus.STARTED, GameRound.INITIAL);

        dealer.tick();

        verify(playersManagerMock, never()).hasAvailableMovers();
    }

    @Test
    public void shouldMoveManagerMakesMoveWhenGameStatusSTARTEDHasAvailableMovesAndGameRoundTHREE_CARDS() throws Exception {
        setGameData(GameStatus.STARTED, GameRound.THREE_CARDS);
        when(playersManagerMock.hasAvailableMovers()).thenReturn(true);

        dealer.tick();

        verify(moveManagerMock).makeMove(mover);
    }

    @Test
    public void shouldNoMoveManagerMakesMoveWhenGameStatusSTARTEDHasAvailableMovesAndGameRoundFINAL() throws Exception {
        setGameData(GameStatus.STARTED, GameRound.FINAL);
        when(playersManagerMock.hasAvailableMovers()).thenReturn(true);

        dealer.tick();

        verify(moveManagerMock, never()).makeMove(mover);
    }

    @Test
    public void shouldEndGameEndGameManagerWhenGameStatusStartedAndGameRoundFinal() throws Exception {
        setGameData(GameStatus.STARTED, GameRound.FINAL);

        dealer.tick();

        verify(endGameManagerMock).endGame();
    }

    @Test
    public void shouldGameCycleManagerPrepareNewGameCycleWhenGameStatusReady() throws Exception {
        setGameStatus(GameStatus.READY);

        dealer.tick();

        verify(gameCycleManagerMock).prepareNewGameCycle();
    }
}
