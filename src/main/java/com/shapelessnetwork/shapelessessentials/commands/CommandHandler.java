package com.shapelessnetwork.shapelessessentials.commands;

import com.shapelessnetwork.shapelessessentials.ShapelessEssentials;
import com.shapelessnetwork.shapelessessentials.commands.shapeless.ShapelessCommand;
import com.shapelessnetwork.shapelessessentials.commands.tpa.SetTpaItemCommand;
import com.shapelessnetwork.shapelessessentials.commands.tpa.TpaAcceptCommand;
import com.shapelessnetwork.shapelessessentials.commands.tpa.TpaCommand;
import com.shapelessnetwork.shapelessessentials.commands.tpa.TpaHereCommand;
import com.shapelessnetwork.shapelessessentials.commands.warps.WarpCommand;
import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;
import com.shapelessnetwork.shapelessessentials.exceptions.MissingPermissionException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CommandHandler implements CommandExecutor, TabCompleter {

    protected final JavaPlugin plugin;

    protected final Map<String, Command> commands = new LinkedHashMap<>();

    public CommandHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void registerCommands() {
        registerCommand(new WarpCommand());
        registerCommand(new TpaCommand());
        registerCommand(new TpaHereCommand());
        registerCommand(new TpaAcceptCommand());
        registerCommand(new SetTpaItemCommand());
        registerCommand(new ShapelessCommand());
    }

    public void registerCommand(Command command) {
        org.bukkit.command.PluginCommand bukkitCommand = plugin.getCommand(command.getName());
        if (bukkitCommand == null) return;
        if (commands.containsKey(command.getName())) {
            ShapelessEssentials.logWarning("Skipping registering command '" + command.getName() + "' because it already exists.");
            return;
        }
        bukkitCommand.setExecutor(this);
        commands.put(command.getName(), command);

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command bukkitCommand, @NotNull String label, @NotNull String[] args) {
        try {
            CommandSet commandSet = getCommandWithArguments(label, args);
            Command command = commandSet.getCommand();
            if (!command.canExecute(sender))
                throw new MissingPermissionException();
            command.run(sender, bukkitCommand, command.getName(), commandSet.getArgs());
        } catch (GeneralException e) {
            sender.sendMessage(e.getComponent());
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command bukkitCommand, @NotNull String alias, @NotNull String[] args) {
        try {
            CommandSet commandSet = getCommandWithArguments(alias, args);
            Command command = commandSet.getCommand();
            return command.getTabComplete(sender, bukkitCommand, command.getName(), commandSet.getArgs());
        } catch (GeneralException ignored) {
            return null;
        }
    }

    protected CommandSet getCommandWithArguments(String label, String[] args) throws GeneralException {
        Command command = commands.get(label);
        if (command == null) {
            Map.Entry<String, Command> commandEntry = commands.entrySet().stream().filter(entry -> entry.getValue().hasAlias(label)).findFirst().orElse(null);
            if (commandEntry == null) throw new GeneralException("Command not found.");
            command = commandEntry.getValue();
        }

        int i = 0;
        if (args.length > 0) {
            for (i = 0; i < args.length; i++) {
                Command subCommand = command.getSubCommand(args[i]);
                if (subCommand != null) command = subCommand;
                else break;
            }
        }
        String[] newArgs;
        if (i > 0) newArgs = Arrays.copyOfRange(args, i, args.length);
        else newArgs = args;
        return new CommandSet(command, newArgs);
    }

    protected static class CommandSet {
        protected Command command;
        protected String[] args;

        public CommandSet(Command command, String[] args) {
            this.command = command;
            this.args = args;
        }

        public Command getCommand() {
            return command;
        }

        public String[] getArgs() {
            return args;
        }
    }

}
