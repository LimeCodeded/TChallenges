package com.github.limecode.tchallenges.utils;

import com.github.limecode.tchallenges.utils.file.FileManagement;
import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.event.impl.TimerResumeEvent;
import com.github.limecode.tchallenges.event.impl.TimerStartEvent;
import com.github.limecode.tchallenges.event.impl.TimerStopEvent;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class ChallengeTimer implements FileManagement {
    private boolean running; // true or false
    private boolean show;
    private int time;

    public ChallengeTimer() {
        this.running = false;
        this.show = true;

        if(getConfigFile().isSet("Timer.time")){
            this.time = getConfigFile().getInt("Timer.time");
        }else this.time = 0;
        run();
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isShow() {
        return show;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        if(isRunning() == running) return;

        this.running = running;

        if(isRunning()){
            new TimerStartEvent(TChallenges.getInstance().getChallengeTimer()).call();
            new TimerResumeEvent(TChallenges.getInstance().getChallengeTimer()).call();
        } else new TimerStopEvent(TChallenges.getInstance().getChallengeTimer()).call();
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void sendActionBar() {
        if(!show) return;

        for (Player player : Bukkit.getOnlinePlayers()) {

            if (!isRunning()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c§oTimer ist pausiert"));
                continue;
            }

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(getTimeString()));
        }
    }

    public String getTimeString(){

        int time = getTime();
        int hours = time / 3600;
        int minutes = (time % 3600) / 60;
        int seconds = time % 60;

        return String.format("§e§l%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void run() {
        new BukkitRunnable() {
            @Override
            public void run() {

                sendActionBar();

                if (!isRunning()) {
                    return;
                }

                setTime(getTime() + 1);
            }
        }.runTaskTimer(TChallenges.getInstance(), 20, 20);
    }
}
