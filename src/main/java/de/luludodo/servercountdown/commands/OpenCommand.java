package de.luludodo.servercountdown.commands;

import de.luludodo.servercountdown.ServerCountdown;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.ArrayList;
import java.util.List;

import static de.luludodo.servercountdown.commands.Messages.*;

public class OpenCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("servercountdown.open")) {
            sendNoPermission(sender, "servercountdown.open");
            return true;
        }
        ServerCountdown.getInstance().getConfig().set("ClosedUntil", "01.01.1970 00:00:00");
        sendOpen(sender);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return new ArrayList<>();
    }
}
