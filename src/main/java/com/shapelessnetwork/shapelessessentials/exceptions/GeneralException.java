package com.shapelessnetwork.shapelessessentials.exceptions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

public class GeneralException extends Exception{

    protected Component component = null;

    public GeneralException() {
        super();
    }

    public GeneralException(String message) {
        super(message);
    }

    public GeneralException(Component component) {
        this.component = component;
    }

    public Component getComponent() {
        if (component != null) return component;
        return Component.text(getMessage()).color(NamedTextColor.RED);
    }
}
