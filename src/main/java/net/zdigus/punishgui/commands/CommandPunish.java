package net.zdigus.punishgui.commands;

import net.zdigus.punishgui.PunishGUI;
import net.zdigus.punishgui.guis.MainGUI;
import net.zdigus.punishgui.utils.Menu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class CommandPunish implements CommandExecutor {
    private final PunishGUI plugin;

    public CommandPunish(PunishGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be executed by a player!");
            return true;
        }
        Player player = (Player) sender;

        FileConfiguration config = plugin.getConfig();

        if (player.hasPermission(config.getString("requiredPermission")) || player.isOp()) {
            if (args.length != 0) {
                OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(args[0]);
                if (!targetPlayer.hasPlayedBefore()) {
                    player.sendMessage(ChatColor.RED + "This player has never played on this server!");
                    return true;
                }

                plugin.menuManager.add(player.getUniqueId(), new Menu(player, targetPlayer, "punishMainMenu", plugin));
            } else {
                player.sendMessage(ChatColor.RED + "Invalid usage: " + command.getUsage());
            }
        } else {
            player.sendMessage(ChatColor.RED + "Insufficient permissions.");
        }

        return true;
    }
}
