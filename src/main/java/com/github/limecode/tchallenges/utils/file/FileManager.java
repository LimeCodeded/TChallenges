package com.github.limecode.tchallenges.utils.file;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileManager {
    private File FOLDER, configFile;

    private YamlConfiguration config;

    public FileManager(){
        this.FOLDER = new File("./plugins/tchallenges/");
        this.configFile = new File(FOLDER, "config.yml");

        try{
            if(!FOLDER.exists()) FOLDER.mkdirs();
            boolean setupConfig = false;

            if(!configFile.exists()){
                configFile.createNewFile();
                setupConfig = true;
            }

            config = YamlConfiguration.loadConfiguration(configFile);

            if(setupConfig){
                config.set("Prefix", "&8[&e&lServer&8] >> ");
                config.set("Message.Join", "&6{Player} &aist dem Server beigetreten");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void save(){
        try{
            config.save(configFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }
}
