package de.webcode.tchallenges.utils.challenge.api;

import de.webcode.tchallenges.utils.challenge.TChallengeKey;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public abstract class TChallenge {

    public abstract void onChallengeEnable();

    public abstract void onChallengeDisable();

    public abstract String getName();

    public abstract String getAuthor();

    public abstract String getVersion();

    public abstract Material getChallengeIcon();

    public abstract ArrayList<TChallengeCommand> getCommands();

    public abstract ArrayList<Listener> getEventlisteners();

    public abstract JavaPlugin getInstance();

}
