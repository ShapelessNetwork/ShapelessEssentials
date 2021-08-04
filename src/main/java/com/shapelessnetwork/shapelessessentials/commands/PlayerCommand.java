package com.shapelessnetwork.shapelessessentials.commands;

import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;
import com.shapelessnetwork.shapelessessentials.exceptions.commands.PlayerCommandException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public abstract class PlayerCommand extends AbstractCommand {
    public PlayerCommand(String name) {
        super(name);
    }

    public PlayerCommand(String name, String... aliases) {
        super(name, aliases);
    }

    @Override
    public void run(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command bukkitCommand, @NotNull String label, @NotNull String[] args) throws GeneralException {
        if (!(sender instanceof Player)) throw new PlayerCommandException();
        run((Player) sender, bukkitCommand, label, args);
    }

    public abstract void run(@NotNull Player player, @NotNull org.bukkit.command.Command bukkitCommand, @NotNull String label, @NotNull String[] args) throws GeneralException;


    @Override
    public List<String> getTabComplete(@NotNull CommandSender sender, @NotNull Command bukkitCommand, @NotNull String alias, @NotNull String[] args) throws GeneralException {
        if (!(sender instanceof Player)) return new ArrayList<>();
        return getTabComplete((Player) sender, bukkitCommand, alias, args);
    }

    public List<String> getTabComplete(@NotNull Player player, @NotNull Command bukkitCommand, @NotNull String alias, @NotNull String[] args) throws GeneralException {
        return super.getTabComplete(player, bukkitCommand, alias, args);
    }
}
