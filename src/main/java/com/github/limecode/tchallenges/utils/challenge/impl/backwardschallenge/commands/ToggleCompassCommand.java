package com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge.commands;

import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.challenge.api.TChallengesAPI;
import com.github.limecode.tchallenges.utils.challenge.api.TCommand;
import com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge.ActionBarCompass;
import com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge.BackwardsChallenge;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class ToggleCompassCommand extends TCommand {

    public ToggleCompassCommand(){
        super(TChallenges.getInstance(), "togglecompass");
        setUsage("/togglecompass");
        setDescription("Schaltet den Kompass entweder auf sichtbar oder auf unsichtbar");
        setAliases("tc", "compass");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cDu kannst diesen Command nur als Spieler ausführen!");
            return false;
        }

        Player player = (Player) commandSender;

        ActionBarCompass compass = BackwardsChallenge.getChallengeInstance().getCompass();
        compass.setShowCompass(!compass.isShowCompass());

        if (!compass.isShowCompass()) {
            TChallengesAPI.getInstance().setShowChallengeTimer(true);
        }
        player.sendMessage(String.format("§aDer Kompass wird nun %s", (compass.isShowCompass() ? "§aangezeigt." : "§cnicht mehr angezeigt.")));
        return true;
    }
}
