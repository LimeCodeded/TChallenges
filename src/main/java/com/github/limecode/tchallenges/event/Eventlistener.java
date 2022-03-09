package com.github.limecode.tchallenges.event;

import com.github.limecode.tchallenges.event.impl.PluginReadyEvent;
import com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge.commands.SetEndcityCommand;
import com.github.limecode.tchallenges.utils.menu.Menu;
import com.github.limecode.tchallenges.utils.permission.Permission;
import com.github.limecode.tchallenges.utils.permission.PermissionManagement;
import com.github.limecode.tchallenges.utils.permission.PermissionManager;
import com.github.limecode.tchallenges.TChallenges;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitRunnable;


public class Eventlistener implements Listener, PermissionManagement {
    @EventTarget
    public void onPluginReady(PluginReadyEvent event){
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
