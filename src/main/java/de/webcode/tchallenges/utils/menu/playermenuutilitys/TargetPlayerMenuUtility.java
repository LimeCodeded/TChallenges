package de.webcode.tchallenges.utils.menu.playermenuutilitys;

import org.bukkit.entity.Player;

public class TargetPlayerMenuUtility extends PlayerMenuUtility{
    private Player target;

    public TargetPlayerMenuUtility(Player owner, Player target){
        super(owner);
        this.target = target;
    }

    public Player getTarget() {
        return target;
    }
}
