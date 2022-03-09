package com.github.limecode.tchallenges.utils.challenge;

import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.event.EventTarget;
import com.github.limecode.tchallenges.event.impl.TimerStartEvent;
import com.github.limecode.tchallenges.event.impl.TimerStopEvent;
import com.github.limecode.tchallenges.utils.ChallengeTimer;
import com.github.limecode.tchallenges.utils.challenge.api.TChallenge;
import com.github.limecode.tchallenges.utils.challenge.api.TCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class ChallengeManager {
    private HashMap<String, ArrayList<Listener>> challengeEventListeners;
    private HashMap<String, TChallenge> challenges;
    private HashMap<String, ArrayList<TCommand>> challengeCommands;
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
        challenges.put(tChallenge.getName(), tChallenge);
        challengeEnableMap.put(tChallenge, false);
    }

    public void toggleChallenge(TChallenge tChallenge){
        boolean value = isChallengeEnabled(tChallenge);

        if(value){
            disableChallenge(tChallenge);
        }else enableChallenge(tChallenge);
    }

    public void disableChallenge(TChallenge challenge){
        ChallengeTimer timer = TChallenges.getInstance().getChallengeTimer();
        timer.setRunning(false);
        challengeEnableMap.put(challenge, false);
    }

    public void enableChallenge(TChallenge challenge){
        ChallengeTimer timer = TChallenges.getInstance().getChallengeTimer();
        timer.setRunning(false);
        challengeEnableMap.put(challenge, true);
    }

    public boolean isChallengeEnabled(TChallenge challenge){
        if(!challengeEnableMap.containsKey(challenge)) return false;

        return challengeEnableMap.get(challenge);
    }

    public void setChallengeCommands(TChallenge challenge, ArrayList<TCommand> cmds){
        challengeCommands.put(challenge.getName(), cmds);
    }

    public ArrayList<TCommand> getChallengeCommands(TChallenge challenge){
        if(!challengeCommands.containsKey(challenge.getName())) return new ArrayList<>();

        return challengeCommands.get(challenge.getName());
    }

    public void setChallengeEventListener(TChallenge challenge, ArrayList<Listener> listeners){
        challengeEventListeners.put(challenge.getName(), listeners);
    }

    public ArrayList<Listener> getChallengeListeners(TChallenge challenge){
        if(!challengeEventListeners.containsKey(challenge.getName())) return new ArrayList<>();

        return challengeEventListeners.get(challenge.getName());
    }

    public ArrayList<TChallenge> getChallenges(){
        ArrayList<TChallenge> toReturn = new ArrayList<>();

        for(TChallenge challenge : challenges.values()){
            toReturn.add(challenge);
        }

        return toReturn;
    }

    public TChallenge getChallengeByName(String name){
        for(TChallenge challenge : challenges.values()){
            if(challenge.getName().equalsIgnoreCase(name)){
                return challenges.get(name);
            }
        }

        return null;
    }
    

    @EventTarget
    public void onTimerStart(TimerStartEvent event){
        for(TChallenge challenge : challenges.values()){

            if(!challengeEnableMap.containsKey(challenge)) continue;

            if (challengeEnableMap.get(challenge)) {
                PluginManager pm = Bukkit.getPluginManager();
                ArrayList<Listener> listeners = challenge.getEventlisteners();

                if(listeners != null){
                    for(Listener listener : listeners){
                        pm.registerEvents(listener, challenge.getInstance());
                    }
                }

                ArrayList<TCommand> challengeCmds = challenge.getCommands();

                if(challengeCmds != null){
                    try {
                        for (TCommand command : challengeCmds) {

                            try {
                                Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                                f.setAccessible(true);
                                CommandMap commandMap = (CommandMap) f.get(Bukkit.getServer());
                                commandMap.register(TChallenges.getInstance().getName(), command);
                            } catch (IllegalAccessException | NoSuchFieldException e) {
                                e.printStackTrace();
                            }
                        }
                    }catch (Exception e){

                    }
                }

                challenge.onChallengeEnable();
            }


            Bukkit.getOnlinePlayers().forEach(player -> {
                player.showTitle(Title.title(Component.text("§2" + challenge.getName()), Component.empty()));
            });
        }
    }

    @EventTarget
    public void onTimerStop(TimerStopEvent event){
        for(TChallenge challenge : challenges.values()){
            if(!challengeEnableMap.containsKey(challenge)) continue;

            if (challengeEnableMap.get(challenge)) {
                ArrayList<Listener> listeners = challenge.getEventlisteners();

                if(listeners != null){

                    HandlerList.unregisterAll(challenge.getInstance());

                    if(challenge.getInstance().getClass().getSimpleName().equals(TChallenges.getInstance().getClass().getSimpleName())){
                        PluginManager pm = Bukkit.getPluginManager();
                        pm.registerEvents(TChallenges.getInstance().getEventlistener(), TChallenges.getInstance());
                    }

                }

                ArrayList<TCommand> cmds = challenge.getCommands();

                if(cmds != null){

                    try {
                        Field f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
                        f.setAccessible(true);
                        CommandMap commandMap = (CommandMap) f.get(Bukkit.getServer());
                        for(TCommand command : cmds){
                            commandMap.getKnownCommands().remove(command.getName());
                        }
                    } catch (IllegalAccessException | NoSuchFieldException e) {
                        e.printStackTrace();
                    }
                }

                challenge.onChallengeDisable();
                challengeEnableMap.put(challenge, false);
            }
        }
    }



}
