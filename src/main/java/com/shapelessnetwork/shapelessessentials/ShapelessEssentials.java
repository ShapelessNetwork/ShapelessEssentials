package com.shapelessnetwork.shapelessessentials;

import com.shapelessnetwork.shapelessessentials.commands.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class ShapelessEssentials extends JavaPlugin {

    protected static ShapelessEssentials instance;

    @Override
    public void onEnable() {
        instance = this;
        Config.initialize(this);
        (new CommandHandler(this)).registerCommands();
    }

    public static ShapelessEssentials getInstance() {
        return instance;
    }

    public static void logWarning(String message) {
        getInstance().getLogger().warning(message);
    }


    @Override
    public void onDisable() {
        getServer().getScheduler().cancelTasks(this);
    }
}
