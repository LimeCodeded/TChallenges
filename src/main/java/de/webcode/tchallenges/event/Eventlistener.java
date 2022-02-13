package de.webcode.tchallenges.event;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.menu.Menu;
import de.webcode.tchallenges.utils.permission.PermissionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.InventoryHolder;


public class Eventlistener implements Listener {
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

}
