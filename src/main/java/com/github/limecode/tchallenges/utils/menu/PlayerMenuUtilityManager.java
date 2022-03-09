package com.github.limecode.tchallenges.utils.menu;

import com.github.limecode.tchallenges.utils.menu.playermenuutilitys.PlayerMenuUtility;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class PlayerMenuUtilityManager {
    private HashMap<Player, PlayerMenuUtility> playerMenuUtilityHashMap;

    public PlayerMenuUtilityManager(){
        this.playerMenuUtilityHashMap = new HashMap<>();
    }

    public PlayerMenuUtility getPlayerMenuUtility(Player player){
        if (playerMenuUtilityHashMap.containsKey(player)) {
            return playerMenuUtilityHashMap.get(player);
        }else{
            PlayerMenuUtility playerMenuUtility = new PlayerMenuUtility(player);
            playerMenuUtilityHashMap.put(player, playerMenuUtility);
            return playerMenuUtility;
        }
    }
}
