package com.shapelessnetwork.shapelessessentials.services;

import com.shapelessnetwork.shapelessessentials.exceptions.GeneralException;
import com.shapelessnetwork.shapelessessentials.exceptions.PlayerNotOnlineException;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Service {

    public static Player getOnlinePlayer(String name) throws GeneralException {
        Player player = Bukkit.getPlayer(name);
        if (player != null && player.isOnline()) {
            return player;
        }
        throw new PlayerNotOnlineException(name);
    }

    public static Player getOnlinePlayer(String name, Player player) throws GeneralException {
        Player p = getOnlinePlayer(name);
        if (player.canSee(p)) {
            return p;
        }
        throw new PlayerNotOnlineException(name);
    }
    public static Player getOnlinePlayer(UUID uuid) throws GeneralException {
        Player player = Bukkit.getPlayer(uuid);
        if (player != null && player.isOnline()) {
            return player;
        }
        throw new PlayerNotOnlineException();
    }

    public static Player getOnlinePlayer(UUID uuid, Player player) throws GeneralException {
        Player p = getOnlinePlayer(uuid);
        if (player.canSee(p)) {
            return p;
        }
        throw new PlayerNotOnlineException();
    }


    public static List<Player> getOnlinePlayers() {
        return new ArrayList<Player>(Bukkit.getOnlinePlayers());
    }

    public static List<Player> getOnlinePlayers(Player player) {
        return getOnlinePlayers().stream().filter(player::canSee).collect(Collectors.toList());
    }

    public static List<String> getOnlinePlayersNames() {
        return getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());
    }

    public static List<String> getOnlinePlayersNames(Player player) {
        return getOnlinePlayers(player).stream().map(Player::getName).collect(Collectors.toList());
    }


}
