package de.webcode.tchallenges.utils;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.file.FileManagement;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;


public class ChallengeTimer implements FileManagement {
    private boolean running; // true or false
    private int time;

    public ChallengeTimer() {
        this.running = false;

        if(getConfigFile().isSet("Timer.time")){
            this.time = getConfigFile().getInt("Timer.time");
        }else this.time = 0;
        run();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void sendActionBar() {
        for (Player player : Bukkit.getOnlinePlayers()) {

            if (!isRunning()) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§c§oTimer ist pausiert"));
                continue;
            }

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(getTimeString()));
        }
    }

    private String getTimeString(){

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
