package com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge.commands;

import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.challenge.api.TCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpToNetherCommand extends TCommand {

    public TpToNetherCommand() {
        super(TChallenges.getInstance(), "tpnether");
        setDescription("Teleportiert alle Spieler in den Nether.");
        setUsage("/tpnether");
    }
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cDu kannst diesen Command nur als Spieler ausführen!");
            return false;
        }

        Player player = (Player) commandSender;

        World nether = Bukkit.getWorld("world_nether");
        Location spawnLoc = nether.getSpawnLocation();

        Bukkit.getOnlinePlayers().forEach(p -> {
            p.teleport(spawnLoc);
        });

        player.sendMessage("§aAlle Spieler wurden in den Nether teleportiert.");
        return true;
    }
}
