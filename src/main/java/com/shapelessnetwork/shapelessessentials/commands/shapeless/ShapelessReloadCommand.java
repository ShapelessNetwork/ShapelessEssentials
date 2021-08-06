package com.shapelessnetwork.shapelessessentials.commands.shapeless;

import com.shapelessnetwork.shapelessessentials.Config;
import com.shapelessnetwork.shapelessessentials.commands.AbstractCommand;
import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class ShapelessReloadCommand extends AbstractCommand {
    public ShapelessReloadCommand() {
        super("reload");
    }

    @Override
    public void run(@NotNull CommandSender sender, @NotNull Command bukkitCommand, @NotNull String label, @NotNull String[] args) throws GeneralException {
        Config.reload();
    }

    @Override
    protected String permission() {
        return "shapeless.reload";
    }
}
