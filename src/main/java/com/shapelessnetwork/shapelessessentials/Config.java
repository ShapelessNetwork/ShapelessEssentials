package com.shapelessnetwork.shapelessessentials;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Config {

    protected static FileConfiguration configuration;
    protected static JavaPlugin plugin;

    public static boolean tpaEnabled = false;
    public static int tpaCooldown = 0;
    public static int tpaTimeout = 1;
    public static ItemStack tpaItem = null;

    public static void initialize(JavaPlugin plugin) {
        Config.plugin = plugin;
        reload();
    }

    public static void reload() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        configuration = plugin.getConfig();
        setValues();
    }

    public static void setValues() {
        tpaEnabled = configuration.getBoolean("tpa.enabled");
        tpaCooldown = Math.max(configuration.getInt("tpa.cooldown"), 0);
        tpaTimeout = Math.max(configuration.getInt("tpa.timeout"), 1);
        tpaItem = configuration.getItemStack("tpa.item");
    }

    public static void setTpaItem(ItemStack item) {
        configuration.set("tpa.item", item);
        plugin.saveConfig();
        tpaItem = item;
    }
}
