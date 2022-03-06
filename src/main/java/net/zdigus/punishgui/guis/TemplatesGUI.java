package net.zdigus.punishgui.guis;

import net.zdigus.punishgui.PunishGUI;
import net.zdigus.punishgui.utils.Menu;
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
import java.util.Map;

public class TemplatesGUI implements Listener {
    private final Inventory inv;
    private final Menu menu;
    private final PunishGUI plugin;

    public TemplatesGUI(Player sender, OfflinePlayer target, Menu menu) {
        this.menu = menu;
        this.plugin = menu.getPlugin();

        inv = Bukkit.createInventory(null, 54, ChatColor.RED + "Punish " + target.getName());

        initializeItems();

        sender.openInventory(inv);
    }

    public void initializeItems() {
        addBottomBar();
        loadPunishments();
    }

    public void loadPunishments() {
        int i = 0;
        for (String path : this.plugin.getConfig().getConfigurationSection("punishmentTemplates").getKeys(false)) {
            if (i >=45) break; // prevent going onto the bottom bar
            String reason = this.plugin.getConfig().getString("punishmentTemplates."+path+".reason");
            String duration = this.plugin.getConfig().getString("punishmentTemplates."+path+".duration");
            String type = this.plugin.getConfig().getString("punishmentTemplates."+path+".type");

            inv.setItem(i, addGUIItem(Material.PAPER, ChatColor.RED + reason, ChatColor.GRAY + "Duration: " + ChatColor.WHITE + duration, ChatColor.GRAY + "Type: " + ChatColor.WHITE + type.substring(0, 1).toUpperCase() + type.substring(1)));

            i++;
        }
    }

    public void addBottomBar() { // this should always be called before adding other items to the inventory
        for (int i = 46; i <= inv.getSize(); i++) {
            inv.setItem(i - 1, backgroundGUIItem());
        }

        inv.setItem(45, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 14, ChatColor.RED + "Close"));
        inv.setItem(46, addGUIGlassItem(Material.STAINED_GLASS_PANE, (short) 4, ChatColor.RED + "Back"));
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
