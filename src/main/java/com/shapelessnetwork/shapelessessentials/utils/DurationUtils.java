package com.shapelessnetwork.shapelessessentials.utils;

import java.time.Duration;

public class DurationUtils {

    public static String formatDuration(Duration duration) {
        int totalSeconds = (int) Math.ceil(duration.getSeconds());
        int secs = totalSeconds % 60;
        int minutes = totalSeconds / 60;
        int hours = totalSeconds / 3600;
        StringBuilder builder = new StringBuilder(24);
        if (hours != 0) {
            builder.append(hours).append(" Hour(s)");
        }
        if (minutes != 0) {
            builder.append(minutes).append(" Minutes(s)");
        }
        if (secs != 0) {
            builder.append(secs).append(" Second(s)");
        }
        return builder.toString();
    }
}
