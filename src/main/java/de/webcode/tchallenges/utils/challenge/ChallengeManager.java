package de.webcode.tchallenges.utils.challenge;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.event.EventTarget;
import de.webcode.tchallenges.event.impl.TimerStartEvent;
import de.webcode.tchallenges.event.impl.TimerStopEvent;
import de.webcode.tchallenges.utils.ChallengeTimer;
import de.webcode.tchallenges.utils.challenge.api.TChallenge;
import de.webcode.tchallenges.utils.challenge.api.TChallengeCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class ChallengeManager {
    private HashMap<TChallengeKey, ArrayList<Listener>> challengeEventListeners;
    private HashMap<TChallengeKey, TChallenge> challenges;
    private HashMap<TChallengeKey, ArrayList<TChallengeCommand>> challengeCommands;
    private HashMap<TChallenge, Boolean> challengeEnableMap;

    public ChallengeManager(){
        this.challenges = new HashMap<>();
        this.challengeCommands = new HashMap<>();
        this.challengeEventListeners = new HashMap<>();
        this.challengeEnableMap = new HashMap<>();

        for(TChallenge challenge : challenges.values()){
            challengeEnableMap.put(challenge, false);
        }
    }

    public void add(TChallenge tChallenge){
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
        ChallengeTimer timer = TChallenges.getInstance().getChallengeTimer();
        timer.setRunning(false);
        new TimerStopEvent(timer).call();
        challengeEnableMap.put(challenge, false);
    }

    public void enableChallenge(TChallengeKey key){
        if(!challenges.containsKey(key)) return;

        enableChallenge(challenges.get(key));
    }

    public void enableChallenge(TChallenge challenge){
        ChallengeTimer timer = TChallenges.getInstance().getChallengeTimer();
        timer.setRunning(false);
        new TimerStopEvent(timer).call();
        challengeEnableMap.put(challenge, true);
    }

    public boolean isChallengeEnabled(TChallengeKey key){
        if(!challenges.containsKey(key)) return false;

        return isChallengeEnabled(challenges.get(key));
    }

    public boolean isChallengeEnabled(TChallenge challenge){
        if(!challengeEnableMap.containsKey(challenge)) return false;

        return challengeEnableMap.get(challenge);
    }

    public void setChallengeCommands(TChallenge challenge, ArrayList<TChallengeCommand> cmds){
        challengeCommands.put(challenge.getKey(), cmds);
    }

    public ArrayList<TChallengeCommand> getChallengeCommands(TChallenge challenge){
        TChallengeKey key = challenge.getKey();

        if(!challengeCommands.containsKey(key)) return new ArrayList<>();

        return challengeCommands.get(key);
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
    

    @EventTarget
    public void onTimerStart(TimerStartEvent event){
        for(TChallenge challenge : challenges.values()){

            if(!challengeEnableMap.containsKey(challenge)) continue;

            if (challengeEnableMap.get(challenge)) {
                TChallengeKey key = challenge.getKey();
                if (challengeEventListeners.containsKey(key)) {
                    PluginManager pm = Bukkit.getPluginManager();
                    ArrayList<Listener> listeners = challengeEventListeners.get(key);

                    for(Listener listener : listeners){
                        pm.registerEvents(listener, TChallenges.getInstance());
                    }
                }

                if (challengeCommands.containsKey(key)) {

                    if (challengeCommands.containsKey(key)) {

                        for(TChallengeCommand command : challengeCommands.get(key)){
                            String name = command.getCommandName();

                            try {
                                Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                                if (!field.isAccessible()){
                                    field.setAccessible(true);
                                }

                                CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());
                                BukkitCommand bukkitCommand = (BukkitCommand) command;

                                commandMap.register(name, bukkitCommand);
                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }

                challenge.onChallengeEnable();
            }
        }
    }

    @EventTarget
    public void onTimerStop(TimerStopEvent event){
        for(TChallenge challenge : challenges.values()){
            if(!challengeEnableMap.containsKey(challenge)) continue;

            if (challengeEnableMap.get(challenge)) {
                TChallengeKey key = challenge.getKey();

                if (challengeEventListeners.containsKey(key)) {
                    PluginManager pm = Bukkit.getPluginManager();
                    ArrayList<Listener> listeners = challengeEventListeners.get(key);

                    for(Listener listener : listeners){
                        HandlerList.unregisterAll(listener);
                    }
                }

                if(challengeCommands.containsKey(key)){
                    for(TChallengeCommand command : challengeCommands.get(key)){
                        String name = command.getCommandName();

                        try {
                            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                            if (!field.isAccessible()){
                                field.setAccessible(true);
                            }

                            CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());
                            commandMap.getKnownCommands().remove(command.getName());


                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }

                challenge.onChallengeDisable();
                challengeEnableMap.put(challenge, false);
            }
        }
    }



}
