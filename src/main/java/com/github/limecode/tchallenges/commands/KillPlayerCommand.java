package com.github.limecode.tchallenges.commands;

import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.menu.PlayerMenuUtilityManager;
import com.github.limecode.tchallenges.utils.menu.impl.KillPlayerMenu;
import com.github.limecode.tchallenges.utils.permission.Permission;
import com.github.limecode.tchallenges.utils.permission.PermissionManagement;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class KillPlayerCommand implements CommandExecutor, PermissionManagement {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(TChallenges.PREFIX + "§cDu kannst diesen Command nur als Spieler ausführen!");
            return false;
        }

        Player player = (Player) sender;

        if (!hasPermission(player, Permission.COMMAND_KILL_PLAYER)) {
            player.sendMessage(TChallenges.PREFIX + "§cDu kannst diesen Command nicht ausführen!");
            return false;
        }

        PlayerMenuUtilityManager playerMenuUtilityManager = TChallenges.getInstance().getPlayerMenuUtilityManager();
        new KillPlayerMenu(playerMenuUtilityManager.getPlayerMenuUtility(player)).open();

        return false;
    }
}
