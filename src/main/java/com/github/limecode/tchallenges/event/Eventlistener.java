package com.github.limecode.tchallenges.event;

import com.github.limecode.tchallenges.event.impl.PluginReadyEvent;
import com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge.commands.SetEndcityCommand;
import com.github.limecode.tchallenges.utils.menu.Menu;
import com.github.limecode.tchallenges.utils.permission.Permission;
import com.github.limecode.tchallenges.utils.permission.PermissionManagement;
import com.github.limecode.tchallenges.utils.permission.PermissionManager;
import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.setting.Setting;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.chat.ChatBaseComponent;
import net.minecraft.network.chat.IChatBaseComponent;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitRunnable;


public class Eventlistener implements Listener, PermissionManagement {
    @EventTarget
    public void onPluginReady(PluginReadyEvent event){
    }

    @EventHandler
    public void onPlayerDeath(EntityDamageEvent event) {
        Entity entity = event.getEntity();

        if(!(entity instanceof Player)) return;

        Player player = (Player) entity;
        if ((player.getHealth() - event.getFinalDamage()) <= 0 && TChallenges.getInstance().getSettingManager().getSetting(Setting.RESPAWN_PLAYERS)) {
            event.setCancelled(true);
            respawnPlayer(player);
        }
    }

    private void respawnPlayer(Player player){
        GameMode pre = player.getGameMode();
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(player.getWorld().getSpawnLocation().add(0, 30, 0));
        player.showTitle(Title.title(Component.text("§eRespawning"), Component.text("§2-§e" + 5 + "§2-")));

        new BukkitRunnable() {
            int count = 5;

            @Override
            public void run() {
                if (count > 0) {
                    player.setSubtitle(new TextComponent("§2-§e" + count + "§2-"));
                    count--;
                } else {

                    Location spawnLoc = player.getBedSpawnLocation();
                    if(player.getBedSpawnLocation() == null) spawnLoc = player.getWorld().getSpawnLocation().add(0.5, 0, 0.5);
                    spawnLoc = getHighestBock(spawnLoc.getWorld(), spawnLoc.getBlockX(), spawnLoc.getBlockZ());
                    player.teleport(spawnLoc);
                    player.setGameMode(pre);
                    player.setHealth(20);
                    player.setFoodLevel(20);
                    player.setLevel(0);
                    player.setTotalExperience(0);
                    cancel();
                }
            }
        }.runTaskTimer(TChallenges.getInstance(), 0, 20);
    }


    public Location getHighestBock(World world, int x, int z){
        int i = world.getEnvironment() == World.Environment.NETHER ? 100 : 255;
        while(i>0){
            if(new Location(world, x, i, z).getBlock().getType()!= Material.AIR)
                return new Location(world, x, i, z).add(0,1,0);
            i--;
        }
        return new Location(world, x, 1, z);
    }



    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getItemMeta() == null) return;

        InventoryHolder holder = e.getInventory().getHolder();
        if (holder instanceof Menu) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) {
                return;
            }
            Menu menu = (Menu) holder;
            menu.handleMenu(e);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        PermissionManager permissionManager = TChallenges.getInstance().getPermissionManager();
        permissionManager.setupPermissions(player);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Player player = event.getPlayer();

        if (!hasPermission(player, Permission.BREAK_BLOCKS)) {
            player.sendMessage(TChallenges.PREFIX + "§cDu kannst keine Blöcke zerstören!");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();

        if (!hasPermission(player, Permission.PLACE_BLOCKS)) {
            event.setCancelled(true);
            player.sendMessage(TChallenges.PREFIX + "§cDu kannst keine Blöcle platzieren!");
        }
    }

}
