package de.webcode.tchallenges.utils.permission;

import de.webcode.tchallenges.TChallenges;
import org.bukkit.entity.Player;

public interface PermissionManagement {
    default boolean hasPermission(Player player, Permission permission){
        return hasPermission(player, permission.getSavePath());
    }

    default boolean hasPermission(Player player, String permission){
        return TChallenges.getInstance().getPermissionManager().hasPermission(player, permission);
    }

    default void setPermission(Player player, String permission, boolean value){
        TChallenges.getInstance().getPermissionManager().setPermission(player, permission, value);
    }

    default void setPermission(Player player, Permission permission, boolean value){
        setPermission(player, permission.getSavePath(), value);
    }
}
