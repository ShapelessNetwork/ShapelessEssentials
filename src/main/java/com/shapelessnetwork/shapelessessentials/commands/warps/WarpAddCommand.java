package com.shapelessnetwork.shapelessessentials.commands.warps;

import com.shapelessnetwork.shapelessessentials.commands.PlayerCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class WarpAddCommand extends PlayerCommand {

    public WarpAddCommand() {
        super("add", "addd");
    }

    @Override
    public void run(@NotNull Player player, @NotNull Command bukkitCommand, @NotNull String label, @NotNull String[] args) {
        player.sendMessage(Component.text("warp add command").color(NamedTextColor.GREEN));
        player.sendMessage(Component.text(Arrays.toString(args)).color(NamedTextColor.GREEN));
    }
}
