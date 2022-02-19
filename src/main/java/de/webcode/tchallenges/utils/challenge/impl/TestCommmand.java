package de.webcode.tchallenges.utils.challenge.impl;

import de.webcode.tchallenges.utils.challenge.api.TChallengeCommand;
import net.minecraft.gametest.framework.GameTestHarnessTestCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class TestCommmand extends TChallengeCommand {

    public TestCommmand(){
        super("test");
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
        Bukkit.broadcastMessage("§aTest 1 Passed");
        return false;
    }
}
