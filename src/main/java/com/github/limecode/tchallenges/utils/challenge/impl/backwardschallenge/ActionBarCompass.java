package com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge;

import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.challenge.api.TChallengesAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.Vector;


public class ActionBarCompass {
    private Location targetLocation;
    private boolean showCompass;

    public ActionBarCompass(){
        this.showCompass = false;

    }

    public void setTargetYaw(Location location) {
        this.targetLocation = location;
    }

    public void setShowCompass(boolean showCompass) {
        this.showCompass = showCompass;
    }

    public boolean isShowCompass() {
        return showCompass;
    }

    public void start(){
        if(showCompass && targetLocation != null){
            TChallengesAPI.getInstance().setShowChallengeTimer(false);
            Bukkit.getOnlinePlayers().forEach(player -> {

                Location origin = player.getLocation();
                Vector t = targetLocation.toVector();
                origin.setDirection(t.subtract(origin.toVector()));
                int targetYaw = (int) origin.getYaw();


                int rotation = (int) ((player.getLocation().getYaw() - targetYaw) % 360.0F);

                StringBuilder b = new StringBuilder();

                int length = 70;

                if(rotation < -(360 / 2 - length) && !(rotation < -(360 - length))){
                    b.append("§2|§f");

                    for(int i = 0; i < length - 1; i++){
                        b.append("§f-");
                    }
                }else if(rotation < -(length / 2) && !(rotation < -(360 - length))){

                    for(int i = 0; i < length - 1; i++){
                        b.append("§f-");
                    }
                    b.append("§2|§f");
                } else{
                    for(int i = 0; i < length; i++){
                        if(rotation == 0 && i == 35){
                            b.append("§6§l|§f");
                        }

                        if(i == length / 2 + (rotation < 0 && rotation > -(360-length) ? -rotation : -(rotation + 360))){
                            b.append("§2§l|§f");
                        }else{
                            b.append("-");
                        }
                    }
                }



                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(b.toString()));
            });
        }
    }
}
