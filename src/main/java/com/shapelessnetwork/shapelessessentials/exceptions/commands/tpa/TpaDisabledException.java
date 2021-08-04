package com.shapelessnetwork.shapelessessentials.exceptions.commands.tpa;

import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;

public class TpaDisabledException extends GeneralException {
    public TpaDisabledException() {
        super("Tpa is not enabled.");
    }
}
