package com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge.commands;

import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.challenge.api.TCommand;
import com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge.BackwardsChallenge;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SetEndcityCommand extends TCommand {

    public SetEndcityCommand(){
        super(TChallenges.getInstance(), "se");
        setAliases("se", "setendcity", "endcitylocation");
        setDescription("Setzt die Location der Endcity und zeigt den Kompass für die Spieler an.");
        setUsage("/se X Y Z");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("§cDu kannst diesen Command nur als Spieler ausführen!");
            return false;
        }

        Player player = (Player) commandSender;

        try{
            int x = Integer.parseInt(args[0]);
            int y = Integer.parseInt(args[1]);
            int z = Integer.parseInt(args[2]);

            Location location = new Location(player.getWorld(), x, y, z);

            BackwardsChallenge.getChallengeInstance().getCompass().setTargetYaw(location);
            BackwardsChallenge.getChallengeInstance().getCompass().setShowCompass(true);
            player.sendMessage("§aDie Location der Endcity wurde erfolgreich gesetzt!");
            return true;
        }catch (NumberFormatException e){
            player.sendMessage("§cBitte gebe gültige Zahlen an!");
            return false;
        }
    }
}
