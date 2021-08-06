package com.shapelessnetwork.shapelessessentials.commands.shapeless;

import com.shapelessnetwork.shapelessessentials.commands.AbstractCommand;
import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class ShapelessCommand extends AbstractCommand {
    public ShapelessCommand() {
        super("shapeless");
    }
    @Override
    public void run(@NotNull CommandSender sender, @NotNull Command bukkitCommand, @NotNull String label, @NotNull String[] args) throws GeneralException {

    }

    @Override
    protected List<com.shapelessnetwork.shapelessessentials.commands.Command> getSubCommandsToRegister() {
        return Collections.singletonList(new ShapelessReloadCommand());
    }

    @Override
    protected String permission() {
        return "shapeless.shapeless";
    }
}
