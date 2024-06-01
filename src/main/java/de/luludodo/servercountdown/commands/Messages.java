package de.luludodo.servercountdown.commands;

import de.luludodo.servercountdown.ServerCountdown;
import org.bukkit.command.CommandSender;

public final class Messages {
    private static String getPrefix() {
        return ServerCountdown.getInstance().getConfig().getString("Prefix");
    }

    public static void sendInvalidAmountArgs(CommandSender sender) {
        sender.sendMessage(getPrefix() + ServerCountdown.getInstance().getConfig().getString("InvalidAmountArgsMsg"));
    }

    public static void sendInvalidArg(CommandSender sender, String arg) {
        sender.sendMessage(getPrefix() + ServerCountdown.getInstance().getConfig().getString("InvalidArgMsg").replace("%arg%", arg));
    }

    public static void sendNoPermission(CommandSender sender, String permission) {
        sender.sendMessage(getPrefix() + ServerCountdown.getInstance().getConfig().getString("NoPermissionMsg").replace("%permission%", permission));
    }

    public static void sendReload(CommandSender sender) {
        sender.sendMessage(getPrefix() + ServerCountdown.getInstance().getConfig().getString("ReloadMsg"));
    }

    public static void sendDate(CommandSender sender, String date) {
        sender.sendMessage(getPrefix() + ServerCountdown.getInstance().getConfig().getString("DateMsg").replace("%date%", date));
        ServerCountdown.getInstance().getLogger().info(ServerCountdown.getInstance().getConfig().getString("ConsoleDateMsg").replace("%date%", date));
    }

    public static void sendOpen(CommandSender sender) {
        sender.sendMessage(getPrefix() + ServerCountdown.getInstance().getConfig().getString("OpenMsg"));
        ServerCountdown.getInstance().getLogger().info(ServerCountdown.getInstance().getConfig().getString("ConsoleOpenMsg"));
    }
}
