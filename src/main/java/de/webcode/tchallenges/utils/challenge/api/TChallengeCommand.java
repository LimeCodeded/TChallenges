package de.webcode.tchallenges.utils.challenge.api;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.command.defaults.BukkitCommand;
import org.jetbrains.annotations.NotNull;

public abstract class TChallengeCommand extends BukkitCommand {
    private String commandName;
    private CommandExecutor commandExecutor;
    private TabCompleter completer;

    public TChallengeCommand(String name){
        super(name);
        this.commandName = name;
    }

    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public void setCompleter(TabCompleter completer) {
        this.completer = completer;
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    public TabCompleter getCompleter() {
        return completer;
    }

    public String getCommandName() {
        return commandName;
    }
}
