package com.shapelessnetwork.shapelessessentials.exceptions.commands;

import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;

public class ConsoleCommandException extends GeneralException {

    public ConsoleCommandException() {
        super("This command can only be executed from the console.");
    }
}
