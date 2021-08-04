package com.shapelessnetwork.shapelessessentials.exceptions.commands;

import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;

public class PlayerCommandException extends GeneralException {

    public PlayerCommandException() {
        super("This command can only be executed by a player.");
    }
}
