package com.nedogeek.holdem.bot;

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


import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * User: Konstantin Demishev
 * Date: 19.04.13
 * Time: 2:12
 */
public class BotsTest {

    @Test
    public void shouldCallBotWhenBotsGetBotTypeByNameCallBot() throws Exception {
        assertEquals(Bots.CallBot, Bots.getBotTypeByName("CallBot"));
    }

    @Test
    public void shouldRiseBotWhenBotsGetBotTypeByNameRiseBot() throws Exception {
        assertEquals(Bots.RiseBot, Bots.getBotTypeByName("RiseBot"));
    }

    @Test
    public void shouldFoldBotWhenBotsGetBotTypeByNameFoldBot() throws Exception {
        assertEquals(Bots.FoldBot, Bots.getBotTypeByName("FoldBot"));
    }

    @Test
    public void shouldRandomBotWhenBotsGetBotTypeByNameRandomBot() throws Exception {
        assertEquals(Bots.RandomBot, Bots.getBotTypeByName("RandomBot"));
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldExceptionWhenBotsGetBotTypeByNameWrongName() throws Exception {
        Bots.getBotTypeByName("Wrong name");
    }

    @Test (expected = IllegalArgumentException.class)
    public void shouldExceptionWhenBotsGetBotTypeByNameNullName() throws Exception {
        Bots.getBotTypeByName(null);
    }
}
