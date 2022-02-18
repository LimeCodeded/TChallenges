package de.webcode.tchallenges.utils.challenge;

import org.bukkit.Material;
import org.bukkit.event.Listener;

public abstract class TChallenge {

    public abstract void onChallengeEnable();

    public abstract void onChallengeDisable();

    public abstract TChallengeKey getKey();

    public abstract String getName();

    public abstract String getAuthor();

    public abstract String getVersion();

    public abstract Material getChallengeIcon();
}
