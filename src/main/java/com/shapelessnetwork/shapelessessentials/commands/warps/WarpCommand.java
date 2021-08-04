package com.shapelessnetwork.shapelessessentials.commands.warps;

import com.shapelessnetwork.shapelessessentials.commands.Command;
import com.shapelessnetwork.shapelessessentials.commands.PlayerCommand;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WarpCommand extends PlayerCommand {

    public WarpCommand() {
        super("warp", "warps");
    }

    @Override
    protected List<Command> getSubCommandsToRegister() {
        List<Command> subCommands =  new ArrayList<>();
        subCommands.add(new WarpAddCommand());
        return subCommands;
    }

    @Override
    public void run(@NotNull Player player, @NotNull org.bukkit.command.Command bukkitCommand, @NotNull String label, @NotNull String[] args) {
        player.sendMessage(Component.text("warp command").color(NamedTextColor.GREEN));
        player.sendMessage(Component.text(Arrays.toString(args)).color(NamedTextColor.GREEN));
    }
}
