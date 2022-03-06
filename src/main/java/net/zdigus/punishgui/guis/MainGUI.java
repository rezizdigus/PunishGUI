package net.zdigus.punishgui.guis;

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

public class MainGUI implements Listener {
    private final Inventory inv;

    public MainGUI(Player sender, OfflinePlayer target) {
        inv = Bukkit.createInventory(null, 27, ChatColor.RED + "Punish " + target.getName());

        initializeItems();

        sender.openInventory(inv);
    }

    public void initializeItems() {
        addBackground(3);

        inv.setItem(12, addGUIItem(Material.ANVIL, ChatColor.RED + "Custom punishment", ChatColor.WHITE + "Let's you customize every option", ChatColor.WHITE + "of the punishment."));
        inv.setItem(14, addGUIItem(Material.BOOK, ChatColor.RED + "Punishment from Template", ChatColor.WHITE + "Choose a punishment from a", ChatColor.WHITE + "predefined template."));
        inv.setItem(18, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED + "Close"));
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
