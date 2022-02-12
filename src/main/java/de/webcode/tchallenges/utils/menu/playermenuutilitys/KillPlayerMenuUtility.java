package de.webcode.tchallenges.utils.menu.playermenuutilitys;

import org.bukkit.entity.Player;

public class KillPlayerMenuUtility extends PlayerMenuUtility {

    private Player playerToKill;

    public KillPlayerMenuUtility(Player owner, Player playerToKill){
        super(owner);
        this.playerToKill = playerToKill;
    }

    public Player getPlayerToKill() {
        return playerToKill;
    }
}
