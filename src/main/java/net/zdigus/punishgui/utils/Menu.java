package net.zdigus.punishgui.utils;

import net.zdigus.punishgui.PunishGUI;
import net.zdigus.punishgui.guis.ConfirmGUI;
import net.zdigus.punishgui.guis.CustomisationGUI;
import net.zdigus.punishgui.guis.TemplatesGUI;
import net.zdigus.punishgui.guis.MainGUI;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class Menu {
    private final Player sender;
    private final OfflinePlayer target;
    private String menuId;
    private String state = "stable"; // stable - user is currently in a menu; changing - the menu is changing, ignore inventoryclosed event; awaitinginput-[type] - awaiting user input in chat, ignore inventoryclosed event
    private Punishment punishment;
    private final PunishGUI plugin;

    private String previousPage;

    public Menu(Player sender, OfflinePlayer target, String menuId, PunishGUI plugin) {
        this.plugin = plugin;
        this.sender = sender;
        this.target = target;
        this.menuId = menuId;

        this.punishment = new Punishment(sender, target, plugin.getConfig());

        this.open();
    }

    public Punishment getPunishment() { return punishment; }
    public String getPreviousPage() { return previousPage; }
    public String getMenuId() { return menuId; }
    public PunishGUI getPlugin() { return plugin; }

    public String getTargetUsername() {
        return this.target.getName();
    }

    public void changeMenu(String menuId) {
        this.state = "changing";
        this.previousPage = this.menuId;

        this.menuId = menuId;

        this.open();
    }

    public String getState() { return this.state; }

    public void open() {
        this.sender.closeInventory();

        switch (this.menuId) {
            case "punishMainMenu":
                new MainGUI(this.sender, this.target);
                break;
            case "templatesMenu":
                new TemplatesGUI(this.sender, this.target, this);
                break;
            case "customisationMenu":
                new CustomisationGUI(this.sender, this.target, this);
                break;
            case "confirmMenu":
                new ConfirmGUI(this.sender, this.target, this);
                break;
            default:
                return;
        }
        this.state = "stable";
    }

    public void onClick(int slot) {
        switch (this.menuId) {
            case "punishMainMenu":
                if (slot == 12) this.changeMenu("customisationMenu");
                else if (slot == 14) this.changeMenu("templatesMenu");
                else if (slot == 18) this.sender.closeInventory();

                break;
            case "templatesMenu":
                if (slot == 45) this.sender.closeInventory();
                else if (slot == 46) this.changeMenu(this.previousPage);
                else {
                    if (this.plugin.getConfig().getString("punishmentTemplates.punishment"+ (slot + 1) + ".reason") == null) return;
                    this.punishment.setReason(this.plugin.getConfig().getString("punishmentTemplates.punishment"+ (slot + 1) + ".reason"));
                    this.punishment.setDuration(this.plugin.getConfig().getString("punishmentTemplates.punishment"+ (slot + 1) + ".duration"));
                    this.punishment.setType(this.plugin.getConfig().getString("punishmentTemplates.punishment"+ (slot + 1) + ".type"));

                    this.changeMenu("customisationMenu");
                }

                break;
            case "customisationMenu":
                if (slot == 36) this.sender.closeInventory();
                else if (slot == 21) {
                    this.state = "awaitinginput-reason";
                    sender.closeInventory();
                    sender.sendMessage(ChatColor.GREEN + "Please type the reason in the chat, or left click to cancel.");
                } else if (slot == 23) {
                    this.state = "awaitinginput-duration";
                    sender.closeInventory();
                    sender.sendMessage(ChatColor.GREEN + "Please type the duration in the chat, or left click to cancel. Type \"p\", \"perm\" or \"permanent\" for a permanent punishment.");
                }  else if (slot == 13) {
                    if (this.punishment.isSilent().equals("Yes")) {
                        this.punishment.setSilent(false);
                    } else {
                        this.punishment.setSilent(true);
                    }

                    this.changeMenu("customisationMenu");
                } else if (slot == 31) {
                    switch (this.punishment.getType()) {
                        case "warn":
                            this.punishment.setType("mute");
                            break;
                        case "mute":
                            this.punishment.setType("ban");
                            break;
                        case "ban":
                            this.punishment.setType("ipban");
                            break;
                        case "ipban":
                            this.punishment.setType("warn");
                            break;
                    }

                    this.changeMenu("customisationMenu");
                }
                else if (slot == 37) this.changeMenu(this.previousPage);
                else if (slot == 44) this.changeMenu("confirmMenu");

                break;
            case "confirmMenu":
                if (slot == 36) this.changeMenu(this.previousPage);
                else if (slot == 10 || slot == 11 || slot == 12 || slot == 19 || slot == 20 || slot == 21 || slot == 28 || slot == 29 || slot == 30) {
                    this.state = "changing"; // to prevent the punishment cancelled message
                    this.punishment.submit();
                }
                else if (slot == 14 || slot == 15 || slot == 16 || slot == 23 || slot == 24 || slot == 25 || slot == 32 || slot == 33 || slot == 34) this.sender.closeInventory();

                break;
            default:
                break;
        }
    }
}
