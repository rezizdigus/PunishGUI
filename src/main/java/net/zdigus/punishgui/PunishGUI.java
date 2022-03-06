package net.zdigus.punishgui;

import net.zdigus.punishgui.commands.CommandPunish;
import net.zdigus.punishgui.commands.CommandPunishGUI;
import net.zdigus.punishgui.utils.Menu;
import net.zdigus.punishgui.utils.MenuManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class PunishGUI extends JavaPlugin implements Listener {

    public MenuManager menuManager = new MenuManager();

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Hewwo!");

        this.saveDefaultConfig();

        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("punish").setExecutor(new CommandPunish(this));
        this.getCommand("punishgui").setExecutor(new CommandPunishGUI(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye!");
        menuManager.clear();
    }

    @EventHandler
    private void onInventoryClick(final InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        
        Menu menu = menuManager.get(player.getUniqueId());

        if (menu == null) return;

        event.setCancelled(true);

        menu.onClick(event.getSlot());
    }

    @EventHandler
    private void onInventoryClose(final InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        Menu menu = menuManager.get(player.getUniqueId());
        if (menu == null) return;

        String menuState = menu.getState();
        if ((menuState.equals("changing")) || (menuState.equals("awaitinginput-duration")) || (menuState.equals("awaitinginput-reason"))) return;

        menuManager.remove(event.getPlayer().getUniqueId());

        player.sendMessage(ChatColor.RED + "You cancelled the punishment for " + menu.getTargetUsername() + ChatColor.RED + ".");
    }

    @EventHandler
    private void onPlayerChat(final AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        Menu menu = menuManager.get(player.getUniqueId());
        if (menu == null) return;

        String menuState = menu.getState();
        if (!(menuState.equals("awaitinginput-duration") || menuState.equals("awaitinginput-reason"))) return;

        event.setCancelled(true);

        if (menuState.equals("awaitinginput-duration")) {
            menu.getPunishment().setDuration(event.getMessage());
        } else {
            menu.getPunishment().setReason(event.getMessage());
        }

        menu.changeMenu(menu.getMenuId());
    }

    @EventHandler
    private void onPlayerLeftClick(final PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Menu menu = menuManager.get(player.getUniqueId());
        if (menu == null) return;

        String menuState = menu.getState();
        if (!(menuState.equals("awaitinginput-duration") || menuState.equals("awaitinginput-reason"))) return;

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            player.sendMessage(ChatColor.RED + "Cancelled input.");
            menu.changeMenu(menu.getMenuId());
        }
    }
}
