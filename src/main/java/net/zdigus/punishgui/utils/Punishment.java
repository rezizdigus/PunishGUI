package net.zdigus.punishgui.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class Punishment {
    private final Player sender;

    private final String submitter;
    private final String target;
    private String reason;
    private boolean silent;
    private String duration;
    private String type; // warn, mute, ban, ipban
    private boolean permanent = false;
    private final boolean litebans;

    public Punishment(Player submitter, OfflinePlayer target, FileConfiguration config) {

        this.submitter = submitter.getName();
        this.target = target.getName();

        this.reason = config.getString("defaultReason");
        this.silent = config.getBoolean("silentByDefault");
        this.litebans = config.getBoolean("litebans");
        this.duration = config.getString("defaultDuration");
        this.setDuration(this.duration);
        this.type = config.getString("defaultType");

        this.sender = submitter;
    }

    public String getSubmitter() { return submitter; }
    public String getTarget() { return target; }
    public String getReason() { return reason; }
    public String getType() { return type; }
    public String getDuration() { return duration; }
    public String isSilent() {
        if (silent) return "Yes";
        else return "No";
    }

    public void setReason(String reason) { this.reason = reason; }
    public void setSilent(boolean silent) { this.silent = silent; }
    public void setPermanent(boolean permanent) { this.permanent = permanent; }
    public void setType(String type) { this.type = type; }
    public void setDuration(String duration) {
        if (duration.equalsIgnoreCase("") || duration.equalsIgnoreCase("p") || duration.equalsIgnoreCase("permanent") || duration.equalsIgnoreCase("perm")) {
            setPermanent(true);
            this.duration = "Permanent";
        } else {
            this.duration = duration;
        }
    }

    public void submit() {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), this.type + " " + this.target + " " + this.reason + (!this.permanent ? " " + this.duration : "") + (this.silent ? " -s" : "") + (this.litebans ? " --sender=" + this.submitter + " --sender-uuid=" + this.sender.getUniqueId() : ""));
        this.sender.sendMessage(ChatColor.GREEN + "You have confirmed the punishment for " + this.target);
        this.sender.closeInventory();
    }
}
