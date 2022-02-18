package de.webcode.tchallenges.commands;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.permission.Permission;
import de.webcode.tchallenges.utils.permission.PermissionManagement;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PermissionCommand implements CommandExecutor, PermissionManagement {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage(TChallenges.PREFIX + "§cDu kannst diesen Command nur als Spieler ausführen!");
            return false;
        }

        Player player = (Player) sender;

        if(!player.isOp()){
            player.sendMessage(TChallenges.PREFIX + "§cDu hast zu wenig Berechtigungen, um diesen Command auszuführen!");
            return false;
        }

        //permission <add|remove|get|list> <Player> <Permission>

        if (args.length == 2 && args[0].equalsIgnoreCase("list")) {
            Player target = Bukkit.getPlayer(args[1]);

            if(target == null){
                player.sendMessage(TChallenges.PREFIX + "§cDer Spieler wurde nicht gefunden!");
                return false;
            }

            StringBuilder b = new StringBuilder();

            target.getEffectivePermissions().forEach(permission -> {
                b.append(String.format("§e%s §7 -> §a%s", permission.getPermission(), permission.getValue()) + "\n");
            });

            player.sendMessage(b.toString());
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("get")) {
            Player target = Bukkit.getPlayer(args[1]);

            if(target == null){
                player.sendMessage(TChallenges.PREFIX + "§cDer Spieler wurde nicht gefunden!");
                return false;
            }

            Permission permission = Permission.getByName(args[2]);

            String path = permission == null ? args[2] : permission.getSavePath();
            boolean value = hasPermission(target, path);

            if (value) {
                player.sendMessage(TChallenges.PREFIX + String.format("§aDer Spieler §6%s §ahat die Permission §6%s §a.", target.getName(), path));
            }else{
                player.sendMessage(TChallenges.PREFIX + String.format("§aDer Spieler §6%s §ahat die Permission §6%s §cnicht.", target.getName(), path));
            }
            return true;
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("add")) {
            Player target = Bukkit.getPlayer(args[1]);

            if(target == null){
                player.sendMessage(TChallenges.PREFIX + "§cDer Spieler wurde nicht gefunden");
                return false;
            }

            Permission permission = Permission.getByName(args[2]);

            String path = permission == null ? args[2] : permission.getSavePath();

            setPermission(target, path, true);
            player.sendMessage(TChallenges.PREFIX + String.format("§aDu hast §6%s §adie Permission §6%s §ahinzugefügt!", target.getName(), path));
            return true;
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("remove")) {
            Player target = Bukkit.getPlayer(args[1]);

            if(target == null){
                player.sendMessage(TChallenges.PREFIX + "§cDer Spieler wurde nicht gefunden");
                return false;
            }

            Permission permission = Permission.getByName(args[2]);

            if(permission == null){
                System.out.println("Default permission nicht gefunden!");
            }

            String path = permission == null ? args[2] : permission.getSavePath();

            setPermission(target, path, false);
            player.sendMessage(TChallenges.PREFIX + String.format("§aDu hast §6%s §adie Permission §6%s §aentfernt!", target.getName(), path));
            return true;
        }

        return false;
    }
}
