package com.nedogeek.holdem;

/**
 * User: Konstantin Demishev
 * Date: 19.05.13
 * Time: 20:35
 */
public interface PlayerCommand {
    String getLogin();

    PlayerCommandReceiver getReceiver();
}
