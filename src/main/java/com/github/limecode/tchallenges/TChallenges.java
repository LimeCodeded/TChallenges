package com.github.limecode.tchallenges;

import com.github.limecode.tchallenges.commands.KillPlayerCommand;
import com.github.limecode.tchallenges.commands.PermissionCommand;
import com.github.limecode.tchallenges.commands.SettingCommand;
import com.github.limecode.tchallenges.commands.SuicideCommand;
import com.github.limecode.tchallenges.event.EventManager;
import com.github.limecode.tchallenges.event.Eventlistener;
import com.github.limecode.tchallenges.event.impl.PluginReadyEvent;
import com.github.limecode.tchallenges.utils.ChallengeTimer;
import com.github.limecode.tchallenges.utils.ItemFactory;
import com.github.limecode.tchallenges.utils.challenge.ChallengeManager;
import com.github.limecode.tchallenges.utils.challenge.api.TChallengesAPI;
import com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge.BackwardsChallenge;
import com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge.commands.SetEndcityCommand;
import com.github.limecode.tchallenges.utils.file.FileManager;
import com.github.limecode.tchallenges.utils.menu.PlayerMenuUtilityManager;
import com.github.limecode.tchallenges.utils.permission.PermissionManager;
import com.github.limecode.tchallenges.utils.setting.SettingManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TChallenges extends JavaPlugin {
    public static final String PREFIX = "§8[§e§lServer§8] >> ";

    private static TChallenges instance;

    private ItemFactory itemFactory;
    private TChallengesAPI tChallengesAPI;
    private Eventlistener eventlistener;
    private ChallengeManager challengeManager;
    private PlayerMenuUtilityManager playerMenuUtilityManager;
    private PermissionManager permissionManager;
    private FileManager fileManager;
    private ChallengeTimer challengeTimer;
    private SettingManager settingManager;

    @Override
    public void onLoad() {
        this.tChallengesAPI = new TChallengesAPI();
        super.onLoad();
    }

    @Override
    public void onEnable() {
        instance = this;

        this.itemFactory = new ItemFactory();
        this.eventlistener = new Eventlistener();
        this.challengeManager = new ChallengeManager();
        this.playerMenuUtilityManager = new PlayerMenuUtilityManager();
        this.fileManager = new FileManager();
        this.settingManager = new SettingManager();
        this.permissionManager = new PermissionManager();
        this.challengeTimer = new ChallengeTimer();

        registerCommands();
        registerEvents();

        new PluginReadyEvent().call();

        tChallengesAPI.addChallenge(new BackwardsChallenge());
    }

    private void registerCommands(){
        getCommand("permission").setExecutor(new PermissionCommand());
        getCommand("settings").setExecutor(new SettingCommand());
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("killplayer").setExecutor(new KillPlayerCommand());
    }

    private void registerEvents(){
        EventManager.register(challengeManager);
        EventManager.register(eventlistener);

        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(eventlistener, this);
    }

    public ChallengeTimer getChallengeTimer() {
        return challengeTimer;
    }

    public PermissionManager getPermissionManager() {
        return permissionManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public TChallengesAPI gettChallengesAPI() {
        return tChallengesAPI;
    }

    public ChallengeManager getChallengeManager() {
        return challengeManager;
    }

    public ItemFactory getItemFactory() {
        return itemFactory;
    }

    public SettingManager getSettingManager() {
        return settingManager;
    }

    public Eventlistener getEventlistener() {
        return eventlistener;
    }

    public PlayerMenuUtilityManager getPlayerMenuUtilityManager() {
        return playerMenuUtilityManager;
    }

    public static TChallenges getInstance() {
        return instance;
    }
}
