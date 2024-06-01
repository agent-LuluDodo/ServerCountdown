package de.luludodo.servercountdown.listeners;

import de.luludodo.servercountdown.ServerCountdown;
import de.luludodo.servercountdown.util.TimeUtil;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.function.Consumer;

public class AsyncPlayerPreLoginEventListener implements Listener {
    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        doAction(
                event.getName(),
                message -> event.disallow(
                        AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                        ChatColor.translateAlternateColorCodes(
                                '&',
                                message
                        )
                )
        );
    }

    public static void doAction(String name, Consumer<String> action) {
        ServerCountdown sc = ServerCountdown.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyy HH:mm:ss");
        long timeMs = 0;

        String message = null;
        String console = null;

        try {
            timeMs = sdf.parse(sc.getConfig().getString("ClosedUntil")).getTime() - System.currentTimeMillis();
        } catch (ParseException e) {
            message = sc.getConfig().getString("ErrorMsg").replace("%err%", e.getMessage()).replace("\\n", "\n");
            console = sc.getConfig().getString("ConsoleErrorMsg").replace("%err%", e.getMessage());
        }

        if (timeMs > 0) {
            message = sc.getConfig().getString("KickMsg").replace("%player%", name).replace("%time%", TimeUtil.generateTime(timeMs, ServerCountdown.getInstance())).replace("\\n", "\n");
            console = sc.getConfig().getString("ConsoleKickMsg").replace("%player%", name).replace("%time%", TimeUtil.generateTime(timeMs, ServerCountdown.getInstance()));
        }

        if (message != null) {
            sc.getLogger().info(console);
            action.accept(ChatColor.translateAlternateColorCodes('&', message));
        }
    }
}
