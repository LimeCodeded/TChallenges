package de.webcode.tchallenges.utils.challenge;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.event.EventTarget;
import de.webcode.tchallenges.event.impl.PluginReadyEvent;
import de.webcode.tchallenges.utils.challenge.api.TChallengesAPI;
import de.webcode.tchallenges.utils.challenge.impl.DontJumpChallenge;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ChallengeManager {
    private HashMap<TChallengeKey, ArrayList<Listener>> challengeEventListeners;
    private HashMap<TChallengeKey, TChallenge> challenges;
    private HashMap<TChallenge, Boolean> challengeEnableMap;

    public ChallengeManager(){
        this.challenges = new HashMap<>();
        this.challengeEventListeners = new HashMap<>();
        this.challengeEnableMap = new HashMap<>();

        for(TChallenge challenge : challenges.values()){
            challengeEnableMap.put(challenge, false);
        }
    }

    public void add(TChallenge tChallenge){
        System.out.println("Challenge hinzugefügt: " + tChallenge.getName());
        challenges.put(tChallenge.getKey(), tChallenge);
        challengeEnableMap.put(tChallenge, false);
    }

    public void toggleChallenge(TChallengeKey key){
        if(!challenges.containsKey(key)) return;

        toggleChallenge(challenges.get(key));
    }

    public void toggleChallenge(TChallenge tChallenge){
        boolean value = isChallengeEnabled(tChallenge);

        if(value){
            disableChallenge(tChallenge);
        }else enableChallenge(tChallenge);
    }

    public void disableChallenge(TChallengeKey key){
        if(!challenges.containsKey(key)) return;

        disableChallenge(challenges.get(key));
    }

    public void disableChallenge(TChallenge challenge){
        TChallengeKey key = challenge.getKey();

        if (challengeEventListeners.containsKey(key)) {
            PluginManager pm = Bukkit.getPluginManager();
            ArrayList<Listener> listeners = challengeEventListeners.get(key);

            for(Listener listener : listeners){
                HandlerList.unregisterAll(listener);
            }
        }
        challenge.onChallengeDisable();
        challengeEnableMap.put(challenge, false);
    }

    public void enableChallenge(TChallengeKey key){
        if(!challenges.containsKey(key)) return;

        enableChallenge(challenges.get(key));
    }

    public void enableChallenge(TChallenge challenge){
        TChallengeKey key = challenge.getKey();
        if (challengeEventListeners.containsKey(key)) {
            PluginManager pm = Bukkit.getPluginManager();
            ArrayList<Listener> listeners = challengeEventListeners.get(key);

            for(Listener listener : listeners){
                pm.registerEvents(listener, TChallenges.getInstance());
            }
        }

        challengeEnableMap.put(challenge, true);
        challenge.onChallengeEnable();
    }

    public boolean isChallengeEnabled(TChallengeKey key){
        if(!challenges.containsKey(key)) return false;

        return isChallengeEnabled(challenges.get(key));
    }

    public boolean isChallengeEnabled(TChallenge challenge){
        if(!challengeEnableMap.containsKey(challenge)) return false;

        return challengeEnableMap.get(challenge);
    }

    public void setChallengeEventListener(TChallenge challenge, ArrayList<Listener> listeners){
        challengeEventListeners.put(challenge.getKey(), listeners);
    }

    public ArrayList<Listener> getChallengeListeners(TChallenge challenge){
        TChallengeKey key = challenge.getKey();

        if(!challengeEventListeners.containsKey(key)) return new ArrayList<>();

        return challengeEventListeners.get(key);
    }

    public ArrayList<TChallenge> getChallenges(){
        ArrayList<TChallenge> toReturn = new ArrayList<>();

        for(TChallenge challenge : challenges.values()){
            toReturn.add(challenge);
        }

        return toReturn;
    }

    public TChallengeKey getChallengeByName(String name){
        for(TChallenge challenge : challenges.values()){
            if(challenge.getName().equals(name)){
                return challenge.getKey();
            }
        }

        return null;
    }



}
