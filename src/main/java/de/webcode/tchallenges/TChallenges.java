package de.webcode.tchallenges;

import de.webcode.tchallenges.commands.*;
import de.webcode.tchallenges.event.EventManager;
import de.webcode.tchallenges.event.Eventlistener;
import de.webcode.tchallenges.event.impl.PluginReadyEvent;
import de.webcode.tchallenges.utils.ChallengeTimer;
import de.webcode.tchallenges.utils.ItemFactory;
import de.webcode.tchallenges.utils.challenge.ChallengeManager;
import de.webcode.tchallenges.utils.challenge.api.TChallengesAPI;
import de.webcode.tchallenges.utils.file.FileManager;
import de.webcode.tchallenges.utils.menu.PlayerMenuUtilityManager;
import de.webcode.tchallenges.utils.permission.PermissionManager;
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
        this.permissionManager = new PermissionManager();
        this.challengeTimer = new ChallengeTimer();

        registerCommands();
        registerEvents();

        new PluginReadyEvent().call();

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

    public PlayerMenuUtilityManager getPlayerMenuUtilityManager() {
        return playerMenuUtilityManager;
    }

    public static TChallenges getInstance() {
        return instance;
    }
}
