package com.github.limecode.tchallenges.commands;

import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.menu.PlayerMenuUtilityManager;
import com.github.limecode.tchallenges.utils.menu.impl.SuicideConfirmMenu;
import com.github.limecode.tchallenges.utils.permission.Permission;
import com.github.limecode.tchallenges.utils.permission.PermissionManagement;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class SuicideCommand implements CommandExecutor, PermissionManagement {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TChallenges.PREFIX + "§cDu kannst diesen Command nur als Spieler benutzen!");
            return false;
        }
        
        Player player = (Player) sender;

        if (!hasPermission(player, Permission.COMMAND_SUICIDE)) {
            player.sendMessage(TChallenges.PREFIX + "§cDu kannst diesen Command nicht benutzen!");
            return false;
        }

        PlayerMenuUtilityManager playerMenuUtilityManager = TChallenges.getInstance().getPlayerMenuUtilityManager();
        new SuicideConfirmMenu(playerMenuUtilityManager.getPlayerMenuUtility(player)).open();

        return true;
    }
}
