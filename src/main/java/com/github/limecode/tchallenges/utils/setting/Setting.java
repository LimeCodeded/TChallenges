package com.github.limecode.tchallenges.utils.setting;

import org.bukkit.ChatColor;

public enum Setting {
    RESPAWN_PLAYERS("setting.respawnplayers", "setting.respawnplayers", "§eSpieler Respawnen"),
    ;
    private String name, savePath, displayName;


    Setting(String name, String savePath, String displayName) {
        this.name = name;
        this.savePath = savePath;
        this.displayName = displayName;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getSavePath() {
        return savePath;
    }

    public static Setting getByDisplayname(String displayname) {
        for (Setting s : Setting.values()) {
            if(ChatColor.stripColor(s.getDisplayName()).equalsIgnoreCase(ChatColor.stripColor(displayname))) return s;
        }
        return null;
    }
}
