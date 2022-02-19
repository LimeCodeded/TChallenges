package de.webcode.tchallenges.utils.challenge.api;

import de.webcode.tchallenges.utils.challenge.TChallengeKey;
import org.bukkit.Material;

public abstract class TChallenge {

    public abstract void onChallengeEnable();

    public abstract void onChallengeDisable();

    public abstract TChallengeKey getKey();

    public abstract String getName();

    public abstract String getAuthor();

    public abstract String getVersion();

    public abstract Material getChallengeIcon();
}
