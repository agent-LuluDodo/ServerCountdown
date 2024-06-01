package de.luludodo.servercountdown.commands;

import de.luludodo.servercountdown.ServerCountdown;
import de.luludodo.servercountdown.listeners.AsyncPlayerPreLoginEventListener;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static de.luludodo.servercountdown.commands.Messages.*;

public class ServerCountdownCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        ServerCountdown plugin = ServerCountdown.getInstance();
        if (args.length == 0) {
            sendInvalidAmountArgs(sender);
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("servercountdown.reload")) {
                sendNoPermission(sender, "servercountdown.reload");
                return true;
            }
            sendReload(sender);
            plugin.reloadConfig();
            plugin.getConfig().options().copyDefaults(true);
            plugin.saveDefaultConfig();
            return true;
        }
        if (args[0].equalsIgnoreCase("start")) {
            if (!sender.hasPermission("servercountdown.start")) {
                sendNoPermission(sender, "servercountdown.start");
                return true;
            }
            if (args.length <= 2) {
                sendInvalidAmountArgs(sender);
                return true;
            }
            if (matchDate(args[1]) && matchTime(args[2])) {
                String date = args[1] + " " + args[2];
                ServerCountdown.getInstance().getConfig().set("ClosedUntil", date);
                sendDate(sender, date);
                for (Player player : plugin.getServer().getOnlinePlayers()) {
                    AsyncPlayerPreLoginEventListener.doAction(
                            player.getDisplayName(),
                            player::kickPlayer
                    );
                }
                return true;
            }
            sendInvalidArg(sender, args[1]);
            return true;
        }
        sendInvalidArg(sender, args[0]);
        return true;
    }

    private boolean matchDate(String s) {
        return s.matches("^(0[1-9]|[12][0-9]|30|31)\\.(0[1-9]|1[0-2])\\.[0-9]{4}$");
    }

    private boolean matchTime(String s) {
        return s.matches("^([01][0-9]|2[0-4])(:[0-5][0-9]){2}$");
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        List<String> tabComplete = new ArrayList<>();
        if (args.length == 1) {
            tabComplete.add("reload");
            tabComplete.add("start");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("reload")) {
                tabComplete.add("config");
                tabComplete.add("plugin");
            } else if (args[0].equalsIgnoreCase("start")) {
                tabComplete.add("dd.MM.yyyy");
            }
        } else if (args.length == 3) {
            if (args[0].equalsIgnoreCase("start")) {
                tabComplete.add("HH:mm:ss");
            }
        }
        return tabComplete;
    }
}
