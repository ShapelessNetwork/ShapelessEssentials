package com.shapelessnetwork.shapelessessentials.exceptions;

import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;

public class MainHandItemException extends GeneralException {
    public MainHandItemException() {
        super("You must have an item in your main hand.");
    }
}
