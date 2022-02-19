package de.webcode.tchallenges.utils.challenge.impl;

import de.webcode.tchallenges.event.Eventlistener;
import de.webcode.tchallenges.utils.challenge.api.TChallenge;
import de.webcode.tchallenges.utils.challenge.TChallengeKey;
import de.webcode.tchallenges.utils.challenge.api.TChallengeCommand;
import de.webcode.tchallenges.utils.challenge.api.TChallengesAPI;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class DontJumpChallenge extends TChallenge implements Listener {

    @Override
    public void onChallengeEnable() {
        Bukkit.broadcastMessage("§aChallenge gestartet!");
    }

    @Override
    public void onChallengeDisable() {
        Bukkit.broadcastMessage("§cChallenge beendet!");
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

    @Override
    public ArrayList<TChallengeCommand> getCommands() {
        ArrayList<TChallengeCommand> cmds = new ArrayList<>();
        cmds.add(new TestCommmand());
        return cmds;
    }

    @Override
    public ArrayList<Listener> getEventlisteners() {
        ArrayList<Listener> listeners = new ArrayList<>();
        listeners.add(this);

        return listeners;
    }

    public void enable(){
        TChallengesAPI api = TChallengesAPI.getInstance();
        api.addChallenge(this);
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
