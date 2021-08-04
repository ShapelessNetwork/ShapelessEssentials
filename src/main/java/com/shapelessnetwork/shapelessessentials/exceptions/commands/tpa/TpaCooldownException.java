package com.shapelessnetwork.shapelessessentials.exceptions.commands.tpa;

import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;
import com.shapelessnetwork.shapelessessentials.utils.DurationUtils;

import java.time.Duration;

public class TpaCooldownException extends GeneralException {

    public TpaCooldownException(Duration duration) {
        super("Tpa on cooldown. " + DurationUtils.formatDuration(duration) + " left.");
    }
}
