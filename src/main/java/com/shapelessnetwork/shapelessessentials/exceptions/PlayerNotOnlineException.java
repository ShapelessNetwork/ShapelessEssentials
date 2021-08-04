package com.shapelessnetwork.shapelessessentials.exceptions;

public class PlayerNotOnlineException extends GeneralException{
    public PlayerNotOnlineException() {
        super("That player is not online.");
    }

    public PlayerNotOnlineException(String player) {
        super("Player '" + player + "' is not online.");
    }

}
