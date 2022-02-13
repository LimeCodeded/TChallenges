package de.webcode.tchallenges;

import de.webcode.tchallenges.commands.SettingCommand;
import de.webcode.tchallenges.commands.SuicideCommand;
import de.webcode.tchallenges.event.Eventlistener;
import de.webcode.tchallenges.utils.ItemFactory;
import de.webcode.tchallenges.utils.menu.PlayerMenuUtilityManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TChallenges extends JavaPlugin {
    public static final String PREFIX = "§8[§e§lServer§8] >> ";

    private static TChallenges instance;

    private ItemFactory itemFactory;
    private PlayerMenuUtilityManager playerMenuUtilityManager;

    @Override
    public void onEnable() {
        instance = this;

        this.itemFactory = new ItemFactory();
        this.playerMenuUtilityManager = new PlayerMenuUtilityManager();

        registerCommands();
        registerEvents();
    }

    /*
    * Für Server Menü:
    * Gamerules
    * Welt
    * Commands
    * */

    private void registerCommands(){
        getCommand("settings").setExecutor(new SettingCommand());
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("killplayer").setExecutor(new KillPlayerCommand());
    }

    private void registerEvents(){
        PluginManager pm = Bukkit.getPluginManager();

        pm.registerEvents(new Eventlistener(), this);
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
