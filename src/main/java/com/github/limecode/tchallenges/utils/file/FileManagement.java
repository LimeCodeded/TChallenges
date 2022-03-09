package com.github.limecode.tchallenges.utils.file;

import com.github.limecode.tchallenges.TChallenges;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

public interface FileManagement {
    default YamlConfiguration getConfigFile(){
        return TChallenges.getInstance().getFileManager().getConfig();
    }

    default String colorTranslate(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    default void save(){
        TChallenges.getInstance().getFileManager().save();
    }
}
