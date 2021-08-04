package com.shapelessnetwork.shapelessessentials.commands;

import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;
import com.shapelessnetwork.shapelessessentials.exceptions.commands.ConsoleCommandException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.jetbrains.annotations.NotNull;

public abstract class ConsoleCommand extends AbstractCommand {
    public ConsoleCommand(String name) {
        super(name);
    }

    public ConsoleCommand(String name, String[] aliases) {
        super(name, aliases);
    }

    @Override
    public void run(@NotNull CommandSender sender, @NotNull Command bukkitCommand, @NotNull String label, @NotNull String[] args) throws GeneralException {
        if (!(sender instanceof ConsoleCommandSender)) throw new ConsoleCommandException();
            run((ConsoleCommandSender) sender, bukkitCommand, label, args);
    }

    public abstract void run(@NotNull ConsoleCommandSender console, @NotNull Command bukkitCommand, @NotNull String label, @NotNull String[] args) throws GeneralException;

}
