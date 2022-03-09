package com.github.limecode.tchallenges.utils.setting;

import com.github.limecode.tchallenges.utils.file.FileManagement;
import org.bukkit.configuration.file.YamlConfiguration;

public class SettingManager implements FileManagement {

    public boolean getSetting(Setting setting) {
        YamlConfiguration configFile = getConfigFile();

        return configFile.isSet("ServerSettings." + setting.getSavePath()) && configFile.getBoolean("ServerSettings." + setting.getSavePath());
    }

    public boolean getSetting(String name) {
        Setting s = Setting.valueOf(name);

        if(s == null) return false;

        return getSetting(s);
    }

    public void set(Setting setting, boolean value) {
        getConfigFile().set("ServerSettings." + setting.getSavePath(), value);
        save();
    }

}
