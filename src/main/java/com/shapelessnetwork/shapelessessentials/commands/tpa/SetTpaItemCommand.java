package com.shapelessnetwork.shapelessessentials.commands.tpa;

import com.shapelessnetwork.shapelessessentials.Config;
import com.shapelessnetwork.shapelessessentials.commands.PlayerCommand;
import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;
import com.shapelessnetwork.shapelessessentials.exceptions.MainHandItemException;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetTpaItemCommand extends PlayerCommand {
    public SetTpaItemCommand() {
        super("settpaitem");
    }

    @Override
    public void run(@NotNull Player player, @NotNull Command bukkitCommand, @NotNull String label, @NotNull String[] args) throws GeneralException {
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) throw new MainHandItemException();
        Config.setTpaItem(player.getInventory().getItemInMainHand());
    }

    @Override
    protected String permission() {
        return "shapeless.tpa.setitem";
    }
}
