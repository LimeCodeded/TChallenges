package de.webcode.tchallenges.utils.challenge.impl.dontjumpchallenge;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.challenge.api.TChallenge;
import de.webcode.tchallenges.utils.challenge.api.TChallengeCommand;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DontJumpChallenge extends TChallenge {
    private static DontJumpChallenge challengeInstance;

    @Override
    public void onChallengeEnable() {
        challengeInstance = this;
    }

    @Override
    public void onChallengeDisable() {

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
    public Material getChallengeIcon() {
        return Material.RABBIT_FOOT;
    }

    @Override
    public ArrayList<TChallengeCommand> getCommands() {
        return null;
    }

    @Override
    public ArrayList<Listener> getEventlisteners() {
        ArrayList<Listener> listeners = new ArrayList<>();
        listeners.add(new Eventlistener());
        return listeners;
    }

    @Override
    public JavaPlugin getInstance() {
        return TChallenges.getInstance();
    }

    public static DontJumpChallenge getChallengeInstance() {
        return challengeInstance;
    }
}
