package com.github.limecode.tchallenges.utils.challenge.api;

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

    public abstract ArrayList<TCommand> getCommands();

    public abstract ArrayList<String> getDescription();

    public abstract ArrayList<Listener> getEventlisteners();

    public abstract JavaPlugin getInstance();

}
