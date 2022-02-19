package de.webcode.tchallenges.utils.challenge.api;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.challenge.ChallengeManager;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class TChallengesAPI {


    public void addChallenge(TChallenge tChallenge){
        TChallenges instance = TChallenges.getInstance();

        ChallengeManager challengeManager = instance.getChallengeManager();
        challengeManager.add(tChallenge);

    }

    public static TChallengesAPI getInstance(){
        return TChallenges.getInstance().gettChallengesAPI();
    }
}
