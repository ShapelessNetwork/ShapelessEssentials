package com.shapelessnetwork.shapelessessentials.commands;

import com.shapelessnetwork.shapelessessentials.ShapelessEssentials;
import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;
import com.shapelessnetwork.shapelessessentials.utils.PermissionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractCommand implements Command {

    protected final Map<String, Command> subCommands = new LinkedHashMap<>();

    protected final String name;

    protected final String[] aliases;

    public AbstractCommand(String name) {
        this(name, new String[0]);
    }

    public AbstractCommand(String name, String... aliases) {
        this.name = name;
        this.aliases = aliases;
        List<Command> subCommandsToRegister = getSubCommandsToRegister();
        subCommandsToRegister.forEach(this::RegisterSubCommand);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getAliases() {
        return aliases;
    }

    @Override
    public boolean hasAlias(String alias) {
        return ArrayUtils.contains(aliases, alias);
    }

    protected List<Command> getSubCommandsToRegister() {
        return new ArrayList<>();
    }

    protected void RegisterSubCommand(Command command) {
        if (subCommands.containsKey(command.getName())) {
            ShapelessEssentials.logWarning("Skipping registering command '" + command.getName() + "' because it already exists.");
            return;
        }
        subCommands.put(command.getName(), command);
    }

    @Override
    public @Nullable Command getSubCommand(@NotNull String label) {
        Command subCommand = subCommands.get(label);
        if (subCommand == null) {
            Map.Entry<String, Command> subCommandEntry = subCommands.entrySet().stream().filter(entry -> entry.getValue().hasAlias(label)).findFirst().orElse(null);
            if (subCommandEntry != null) subCommand = subCommandEntry.getValue();
        }
        return subCommand;
    }

    @Override
    public boolean hasSubCommand(@NotNull String label) {
        return subCommands.containsKey(label);
    }

    public abstract void run(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command bukkitCommand, @NotNull String label, @NotNull String[] args) throws GeneralException;

    @Override
    public boolean canExecute(CommandSender sender) {
        if (permission() != null) {
            return PermissionUtils.hasPermission(sender, permission());
        }
        return true;
    }

    @Override
    public List<String> getTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command bukkitCommand, @NotNull String alias, @NotNull String[] args) throws GeneralException {
        return new ArrayList<>();
    }

    @Override
    public String usage() {
        return null;
    }

    protected String permission() {
        return null;
    }
}
