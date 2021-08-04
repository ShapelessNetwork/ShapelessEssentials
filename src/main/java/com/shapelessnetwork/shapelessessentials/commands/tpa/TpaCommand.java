package com.shapelessnetwork.shapelessessentials.commands.tpa;

import com.shapelessnetwork.shapelessessentials.Config;
import com.shapelessnetwork.shapelessessentials.commands.PlayerCommand;
import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;
import com.shapelessnetwork.shapelessessentials.exceptions.commands.NotEnoughArgumentsException;
import com.shapelessnetwork.shapelessessentials.exceptions.commands.tpa.TpaDisabledException;
import com.shapelessnetwork.shapelessessentials.services.Tpa;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TpaCommand extends PlayerCommand {

    public TpaCommand() {
        super("tpa");
    }

    @Override
    public void run(@NotNull Player player, @NotNull Command bukkitCommand, @NotNull String label, @NotNull String[] args) throws GeneralException {
        if (!Config.tpaEnabled) throw new TpaDisabledException();
        if (args.length < 1) {
            throw new NotEnoughArgumentsException(1, 0);
        }
        Tpa.sendTpaRequest(player, args[0]);
    }

    @Override
    public List<String> getTabComplete(@NotNull Player player, @NotNull Command bukkitCommand, @NotNull String alias, @NotNull String[] args) {
        return Tpa.getOnlinePlayersNames(player);
    }
}
