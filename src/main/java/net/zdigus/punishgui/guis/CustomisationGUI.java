package net.zdigus.punishgui.guis;

import net.zdigus.punishgui.utils.Menu;
import net.zdigus.punishgui.utils.Punishment;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class CustomisationGUI implements Listener {
    private final Inventory inv;
    private final Menu menu;

    public CustomisationGUI(Player sender, OfflinePlayer target, Menu menu) {
        inv = Bukkit.createInventory(null, 45, ChatColor.RED + "Punish " + target.getName());
        this.menu = menu;

        initializeItems();

        sender.openInventory(inv);
    }

    public void initializeItems() {
        Punishment punishment = menu.getPunishment();

        addBackground(5);

        if (punishment.isSilent().equals("Yes")) {
            inv.setItem(13, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 7, ChatColor.RED + "Silent", ChatColor.GRAY + "Current: " + ChatColor.WHITE + punishment.isSilent(), " ", ChatColor.RED + "Left click to change to " + ChatColor.DARK_RED + "public" + ChatColor.RED + "."));
        } else {
            inv.setItem(13, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED + "Silent", ChatColor.GRAY + "Current: " + ChatColor.WHITE + punishment.isSilent(), " ", ChatColor.RED + "Left click to change to " + ChatColor.DARK_RED + "silent" + ChatColor.RED + "."));
        }


        inv.setItem(21, addGUIItem(Material.BOOK_AND_QUILL, ChatColor.RED + "Reason", ChatColor.GRAY + "Current: " + ChatColor.WHITE + punishment.getReason(), " ", ChatColor.RED + "Left click to edit the reason."));
        inv.setItem(22, addGUIItem(Material.BOOK, ChatColor.RED + "Punishment", ChatColor.GRAY + "Submitter: " + ChatColor.WHITE + punishment.getSubmitter(), ChatColor.GRAY + "Punished: " + ChatColor.WHITE + punishment.getTarget(), ChatColor.GRAY + "Reason: " + ChatColor.WHITE + punishment.getReason(), ChatColor.GRAY + "Silent: " + ChatColor.WHITE + punishment.isSilent(), ChatColor.GRAY + "Type: " + ChatColor.WHITE + punishment.getType().substring(0, 1).toUpperCase() + punishment.getType().substring(1)));
        inv.setItem(23, addGUIItem(Material.BOOK_AND_QUILL, ChatColor.RED + "Duration", ChatColor.GRAY + "Current: " + ChatColor.WHITE + punishment.getDuration(), " ", ChatColor.RED + "Left click to edit the duration."));

        switch (punishment.getType()) {
            case "warn":
                inv.setItem(31, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.RED + "Type", ChatColor.GRAY + "Current: " + ChatColor.WHITE + "Warn", " ", ChatColor.RED + "Left click to change to " + ChatColor.DARK_RED + "Mute" + ChatColor.RED + "."));
                break;
            case "mute":
                inv.setItem(31, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 4, ChatColor.RED + "Type", ChatColor.GRAY + "Current: " + ChatColor.WHITE + "Mute", " ", ChatColor.RED + "Left click to change to " + ChatColor.DARK_RED + "Ban" + ChatColor.RED + "."));
                break;
            case "ban":
                inv.setItem(31, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED + "Type", ChatColor.GRAY + "Current: " + ChatColor.WHITE + "Ban", " ", ChatColor.RED + "Left click to change to " + ChatColor.DARK_RED + "IP-Ban" + ChatColor.RED + "."));
                break;
            case "ipban":
                inv.setItem(31, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 15, ChatColor.RED + "Type", ChatColor.GRAY + "Current: " + ChatColor.WHITE + "IP-Ban", " ", ChatColor.RED + "Left click to change to " + ChatColor.DARK_RED + "Warn" + ChatColor.RED + "."));
                break;
        }

        inv.setItem(36, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED + "Close"));
        inv.setItem(37, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 4, ChatColor.RED + "Back"));
        inv.setItem(44, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN + "Continue"));
    }

    public void addBackground(int rows) { // this should always be called before adding other items to the inventory
        int slots = rows * 9;

        for (int i = 1; i <= slots; i++) {
            inv.setItem(i - 1, backgroundGUIItem());
        }
    }

    protected ItemStack addGUIItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    protected ItemStack addGUIGlassItem(final Material material, short color, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1, color);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item;
    }

    protected ItemStack backgroundGUIItem() {
        final ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
        final ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(" ");
        item.setItemMeta(meta);

        return item;
    }
}
