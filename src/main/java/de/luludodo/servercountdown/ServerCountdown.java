package de.luludodo.servercountdown;

import de.luludodo.servercountdown.commands.OpenCommand;
import de.luludodo.servercountdown.commands.ServerCountdownCommand;
import de.luludodo.servercountdown.listeners.AsyncPlayerPreLoginEventListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class ServerCountdown extends JavaPlugin {
    private static ServerCountdown instance;
    public static ServerCountdown getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        getCommand("sc").setExecutor(new ServerCountdownCommand());
        getCommand("sc").setTabCompleter(new ServerCountdownCommand());
        getCommand("open").setExecutor(new OpenCommand());
        getCommand("open").setTabCompleter(new OpenCommand());

        getServer().getPluginManager().registerEvents(new AsyncPlayerPreLoginEventListener(), this);

        getLogger().info("Enabled ServerCountdown");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled ServerCountdown");
    }
}
