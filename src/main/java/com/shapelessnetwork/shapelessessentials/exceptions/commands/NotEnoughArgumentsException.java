package com.shapelessnetwork.shapelessessentials.exceptions.commands;

import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;

public class NotEnoughArgumentsException extends GeneralException {
    public NotEnoughArgumentsException() {
        super("Missing command arguments.");
    }

    public NotEnoughArgumentsException(int min) {
        super("Missing command arguments. ( " + min + " arguments needed)");
    }

    public NotEnoughArgumentsException(int min, int provided) {
        super("Missing command arguments. (" + min + " required, "+ provided +" provided)");
    }
}
