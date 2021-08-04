package com.shapelessnetwork.shapelessessentials.commands.tpa;

import com.shapelessnetwork.shapelessessentials.Config;
import com.shapelessnetwork.shapelessessentials.commands.PlayerCommand;
import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;
import com.shapelessnetwork.shapelessessentials.exceptions.commands.NotEnoughArgumentsException;
import com.shapelessnetwork.shapelessessentials.exceptions.commands.tpa.TpaDisabled;
import com.shapelessnetwork.shapelessessentials.services.Tpa;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpaHereCommand extends PlayerCommand {
    public TpaHereCommand() {
        super("tpahere");
    }
    @Override
    public void run(@NotNull Player player, @NotNull Command bukkitCommand, @NotNull String label, @NotNull String[] args) throws GeneralException {
        if (!Config.tpaEnabled) throw new TpaDisabled();
        if (args.length < 1) {
            throw new NotEnoughArgumentsException(1, 0);
        }
        Tpa.sendTpaHereRequest(player, args[0]);
    }
}
