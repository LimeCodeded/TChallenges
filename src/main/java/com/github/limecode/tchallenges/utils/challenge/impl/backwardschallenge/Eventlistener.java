package com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge;

import com.destroystokyo.paper.event.player.PlayerTeleportEndGatewayEvent;
import com.github.limecode.tchallenges.utils.challenge.api.TChallengesAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.*;

public class Eventlistener implements Listener {
    @EventHandler(priority = EventPriority.HIGH)
    public void onPortal(EntityDeathEvent event){

        if(event.getEntity().getType().equals(EntityType.ENDER_DRAGON)){
            World nether = Bukkit.getWorld("world_nether");
            Bukkit.getOnlinePlayers().forEach(player -> player.teleport(nether.getSpawnLocation()));
        }
    }

    @EventHandler
    public void onEnd(PlayerChangedWorldEvent event){
        Location location = event.getPlayer().getLocation();

        if(location.getWorld().getEnvironment().equals(World.Environment.NORMAL)){
            TChallengesAPI.getInstance().endChallenge(BackwardsChallenge.getChallengeInstance());
        }
    }
}
