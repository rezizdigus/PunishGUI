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

public class ConfirmGUI implements Listener {
    private final Inventory inv;
    private final Menu menu;

    public ConfirmGUI(Player sender, OfflinePlayer target, Menu menu) {
        inv = Bukkit.createInventory(null, 45, ChatColor.RED + "Punish " + target.getName());
        this.menu = menu;

        initializeItems();

        sender.openInventory(inv);
    }

    public void initializeItems() {
        Punishment punishment = menu.getPunishment();

        addBackground(5);

        inv.setItem(10, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN + "Confirm"));
        inv.setItem(11, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN + "Confirm"));
        inv.setItem(12, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN + "Confirm"));
        inv.setItem(19, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN + "Confirm"));
        inv.setItem(20, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN + "Confirm"));
        inv.setItem(21, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN + "Confirm"));
        inv.setItem(28, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN + "Confirm"));
        inv.setItem(29, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN + "Confirm"));
        inv.setItem(30, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 5, ChatColor.GREEN + "Confirm"));

        inv.setItem(22, addGUIItem(Material.BOOK, ChatColor.RED + "Summary", ChatColor.GRAY + "Submitter: " + ChatColor.WHITE + punishment.getSubmitter(), ChatColor.GRAY + "Punished: " + ChatColor.WHITE + punishment.getTarget(), ChatColor.GRAY + "Reason: " + ChatColor.WHITE + punishment.getReason(), ChatColor.GRAY + "Silent: " + ChatColor.WHITE + punishment.isSilent(), ChatColor.GRAY + "Duration: " + ChatColor.WHITE + punishment.getDuration(), ChatColor.GRAY + "Type: " + ChatColor.WHITE + punishment.getType().substring(0, 1).toUpperCase() + punishment.getType().substring(1)));

        inv.setItem(14, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED + "Cancel"));
        inv.setItem(15, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED + "Cancel"));
        inv.setItem(16, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED + "Cancel"));
        inv.setItem(23, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED + "Cancel"));
        inv.setItem(24, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED + "Cancel"));
        inv.setItem(25, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED + "Cancel"));
        inv.setItem(32, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED + "Cancel"));
        inv.setItem(33, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED + "Cancel"));
        inv.setItem(34, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED + "Cancel"));

        inv.setItem(36, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 4, ChatColor.RED + "Back"));
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
