package com.github.limecode.tchallenges.utils.challenge.api;

import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.ChallengeTimer;
import com.github.limecode.tchallenges.utils.challenge.ChallengeManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;

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

    public void toggleTimerShow(){
        ChallengeTimer challengeTimer = TChallenges.getInstance().getChallengeTimer();
        challengeTimer.setShow(!challengeTimer.isShow());
    }

    public void setShowChallengeTimer(boolean showTimer) {
        TChallenges.getInstance().getChallengeTimer().setShow(showTimer);
    }

    public void pauseChallengeTimer() {
        TChallenges.getInstance().getChallengeTimer().setRunning(false);
    }

    public static TChallengesAPI getInstance(){
        return TChallenges.getInstance().gettChallengesAPI();
    }
}
