package de.webcode.tchallenges.utils.challenge.api;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.ChallengeTimer;
import de.webcode.tchallenges.utils.challenge.ChallengeManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;

import java.util.ArrayList;

public class TChallengesAPI {


    public void addChallenge(TChallenge tChallenge){
        TChallenges instance = TChallenges.getInstance();

        ChallengeManager challengeManager = instance.getChallengeManager();
        challengeManager.add(tChallenge);

    }

    public void endChallenge(TChallenge challenge){
        ChallengeTimer challengeTimer = TChallenges.getInstance().getChallengeTimer();
        challengeTimer.setRunning(false);
        Bukkit.getOnlinePlayers().forEach(player -> {
            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage("§e----------------------------------------\n" +
                    "§7Challenge: "+ challenge.getName() + "\n" +
                    "§7Die Challenge ist beendet.\n" +
                    "§7Zeit: " + challengeTimer.getTimeString() +
                    "\n§e----------------------------------------");
        });
    }

    public static TChallengesAPI getInstance(){
        return TChallenges.getInstance().gettChallengesAPI();
    }
}
