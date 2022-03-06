package net.zdigus.punishgui.commands;

import net.zdigus.punishgui.PunishGUI;
import net.zdigus.punishgui.utils.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandPunishGUI implements CommandExecutor {
    private final PunishGUI plugin;

    public CommandPunishGUI(PunishGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        FileConfiguration config = plugin.getConfig();

        if (player.hasPermission(config.getString("requiredPermissionAdmin")) || player.isOp()) {
            if (args.length != 0) {
                if (args[0].equals("reload")) {
                    plugin.reloadConfig();
                    player.sendMessage(ChatColor.GREEN + "Config reloaded.");
                } else {
                    player.sendMessage(ChatColor.RED + "Invalid usage: " + command.getUsage());
                }
            } else {
                player.sendMessage(ChatColor.RED + "Invalid usage: " + command.getUsage());
            }
        } else {
            player.sendMessage(ChatColor.RED + "Insufficient permissions.");
        }

        return true;
    }
}
