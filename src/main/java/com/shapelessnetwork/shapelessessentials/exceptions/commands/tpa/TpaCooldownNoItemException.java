package com.shapelessnetwork.shapelessessentials.exceptions.commands.tpa;

import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;
import com.shapelessnetwork.shapelessessentials.utils.DurationUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;
import java.util.Objects;

public class TpaCooldownNoItemException extends GeneralException {
    public TpaCooldownNoItemException(Duration duration, ItemStack item) {
        super(Component.text("Tpa on cooldown and you don't have tpa cost items (" + item.getAmount() + "x")
                .append(Component.text(Objects.requireNonNull(item.getI18NDisplayName())))
                .append(Component.text(").\n"))
                .append(Component.text(DurationUtils.formatDuration(duration) + " left.")).color(NamedTextColor.RED));
    }
}
