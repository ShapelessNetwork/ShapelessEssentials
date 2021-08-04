package com.shapelessnetwork.shapelessessentials.commands;

import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface Command {

    String getName();

    String[] getAliases();

    boolean hasAlias(String alias);

    void run(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command bukkitCommand, @NotNull String label, @NotNull String[] args) throws GeneralException;

    @Nullable Command getSubCommand(@NotNull String label);

    boolean hasSubCommand(@NotNull String label);

    boolean canExecute(CommandSender sender);

    List<String> getTabComplete(@NotNull CommandSender sender, org.bukkit.command.@NotNull Command bukkitCommand, @NotNull String alias, @NotNull String[] args) throws GeneralException;

    String usage();
}
