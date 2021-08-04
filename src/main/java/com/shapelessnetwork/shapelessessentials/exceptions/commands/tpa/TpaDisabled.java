package com.shapelessnetwork.shapelessessentials.exceptions.commands.tpa;

import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;

public class TpaDisabled extends GeneralException {
    public TpaDisabled() {
        super("Tpa is not enabled.");
    }
}
