package de.webcode.tchallenges.utils.challenge.impl;

import de.webcode.tchallenges.utils.challenge.TChallenge;
import de.webcode.tchallenges.utils.challenge.TChallengeKey;
import de.webcode.tchallenges.utils.challenge.api.TChallengesAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class DontJumpChallenge extends TChallenge implements Listener {

    @Override
    public void onChallengeEnable() {
    }

    @Override
    public void onChallengeDisable() {
    }

    @Override
    public TChallengeKey getKey() {
        return null;
    }

    @Override
    public String getName() {
        return "Dont Jump Challenge";
    }

    @Override
    public String getAuthor() {
        return "TheWebcode";
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    public void enable(){
        TChallengesAPI api = TChallengesAPI.getInstance();
        api.addChallenge(this);

        api.registerListener(this, this);
    }

    @Override
    public Material getChallengeIcon() {
        return Material.POTION;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();

        System.out.println("Test 1 Passed");
        player.sendMessage("§aEs geht");
    }
}
