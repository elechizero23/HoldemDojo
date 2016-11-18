package com.nedogeek.holdem.combinations;

/*-
 * #%L
 * Holdem dojo project is a server-side java application for playing holdem pocker in DOJO style.
 * %%
 * Copyright (C) 2016 Holdemdojo
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */


import com.nedogeek.holdem.gamingStuff.Card;
import com.nedogeek.holdem.gamingStuff.CardSuit;
import com.nedogeek.holdem.gamingStuff.CardValue;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 * User: Konstantin Demishev
 * Date: 03.12.12
 * Time: 18:21
 */
public class CombinationTest {
    private static final Card HEARTS_ACE = new Card(CardSuit.HEARTS, CardValue.ACE);
    private static final Card HEARTS_KING = new Card(CardSuit.HEARTS, CardValue.KING);
    private static final Card HEARTS_QUEEN = new Card(CardSuit.HEARTS, CardValue.QUEEN);
    private static final Card HEARTS_JACK = new Card(CardSuit.HEARTS, CardValue.JACK);
    private static final Card HEARTS_TEN = new Card(CardSuit.HEARTS, CardValue.TEN);

    private static final Card HEARTS_TWO = new Card(CardSuit.HEARTS, CardValue.TWO);
    private static final Card HEARTS_THREE = new Card(CardSuit.HEARTS, CardValue.THREE);
    private static final Card HEARTS_FOUR = new Card(CardSuit.HEARTS, CardValue.FOUR);
    private static final Card HEARTS_FIVE = new Card(CardSuit.HEARTS, CardValue.FIVE);

    private static final Card DIAMONDS_ACE = new Card(CardSuit.DIAMONDS, CardValue.ACE);

    @Test
    public void shouldRoyalFlashWhenHearts10JQKAGetCombinationType() throws Exception {
        Card[] cards = new Card[]{HEARTS_TEN, HEARTS_JACK, HEARTS_QUEEN, HEARTS_KING, HEARTS_ACE};

        assertEquals(Combination.ROYAL_FLASH, Combination.getCombinationType(cards));


    }

    @Test
    public void shouldPairWhenHeartsJQKAAndDiamondsAceGetCombinationType() throws Exception {
        Card[] cards = new Card[]{HEARTS_JACK, HEARTS_QUEEN, HEARTS_KING, HEARTS_ACE, DIAMONDS_ACE};

        assertEquals(Combination.PAIR, Combination.getCombinationType(cards));

        cards = new Card[]{HEARTS_JACK, HEARTS_QUEEN, HEARTS_KING, new Card(CardSuit.CLUBS, CardValue.FIVE), new Card(CardSuit.SPADES, CardValue.FIVE)};

        assertEquals(Combination.PAIR, Combination.getCombinationType(cards));
    }


    @Test
    public void shouldNoCardsWhenHearts10JQKAGetCombinationCards() throws Exception {
        Card[] cards = new Card[]{HEARTS_TEN, HEARTS_JACK, HEARTS_QUEEN, HEARTS_KING, HEARTS_ACE};

        assertTrue(Arrays.deepEquals(new Card[0], Combination.getCombinationCards(cards)));
    }

    @Test
    public void shouldAKQJWhenHeartsJQKAAndDiamondsAceGetCombinationCards() throws Exception {
        Card[] cards = new Card[]{HEARTS_JACK, HEARTS_QUEEN, HEARTS_KING, HEARTS_ACE, DIAMONDS_ACE};

        Card[] expectedCards = new Card[]{HEARTS_ACE, HEARTS_KING, HEARTS_QUEEN, HEARTS_JACK};
        assertTrue(Arrays.deepEquals(expectedCards, Combination.getCombinationCards(cards)));
    }

    @Test
    public void shouldSmallerStraightWhenDiamondsAHearts2345() throws Exception {
        Card[] cards = new Card[]{DIAMONDS_ACE, HEARTS_TWO, HEARTS_THREE, HEARTS_FOUR, HEARTS_FIVE};

        assertEquals(Combination.SMALLER_STRAIGHT, Combination.getCombinationType(cards));
    }

    @Test
    public void shouldSmallerStraightFlashWhenHeartsA2345() throws Exception {
        Card[] cards = new Card[]{HEARTS_ACE, HEARTS_TWO, HEARTS_THREE, HEARTS_FOUR, HEARTS_FIVE};

        assertEquals(Combination.SMALLER_STRAIGHT_FLUSH, Combination.getCombinationType(cards));
    }

    @Test
    public void shouldNoCardCombinationWhenNoCardsGetCardCombination() throws Exception {
        Card[] cards = new Card[0];

        assertEquals(Combination.NO_CARD_COMBINATION, Combination.getCombinationType(cards));
    }
}
