package de.webcode.tchallenges.utils.challenge.impl.dontjumpchallenge;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import de.webcode.tchallenges.utils.challenge.api.TChallengesAPI;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class Eventlistener implements Listener {
    @EventHandler
    public void onJump(PlayerJumpEvent event){
        TChallengesAPI api = TChallengesAPI.getInstance();
        api.endChallenge(DontJumpChallenge.getChallengeInstance());
    }
}
