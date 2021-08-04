package com.shapelessnetwork.shapelessessentials.exceptions;

public class MissingPermissionException extends GeneralException{
    public MissingPermissionException() {
        super("You do not have the required permissions to perform this action.");
    }
}
