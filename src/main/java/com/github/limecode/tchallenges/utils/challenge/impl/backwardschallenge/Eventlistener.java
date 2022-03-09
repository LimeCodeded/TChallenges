package com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge;

import com.destroystokyo.paper.event.player.PlayerTeleportEndGatewayEvent;
import com.github.limecode.tchallenges.utils.challenge.api.TChallengesAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.*;

import java.util.ArrayList;

public class Eventlistener implements Listener {

    private PlayerPortalEvent event;

    @EventHandler(priority = EventPriority.HIGH)
    public void onPortal(EntityDeathEvent event){

        if(event.getEntity().getType().equals(EntityType.ENDER_DRAGON)){
            World nether = Bukkit.getWorld("world_nether");
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.teleport(nether.getSpawnLocation());
                player.setBedSpawnLocation(getHighestBock(player.getWorld(), player.getLocation().getBlockX(), player.getLocation().getBlockZ()));

            });
        }
    }

    public Location getHighestBock(World world, int x, int z){
        int i = 255;
        while(i>0){
            if(new Location(world, x, i, z).getBlock().getType()!= Material.AIR)
                return new Location(world, x, i, z).add(0,1,0);
            i--;
        }
        return new Location(world, x, 1, z);
    }

    @EventHandler
    public void onOverworldJoin(PlayerPortalEvent event){
        if (event.getCause().equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL)) {
            TChallengesAPI.getInstance().endChallenge(BackwardsChallenge.getChallengeInstance());
        }
    }
}
