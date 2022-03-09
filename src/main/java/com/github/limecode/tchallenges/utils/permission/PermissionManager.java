package com.github.limecode.tchallenges.utils.permission;

import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.file.FileManagement;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class PermissionManager implements FileManagement {

    public void setPermission(Player player, Permission permission, boolean value){
        setPermission(player, permission.getSavePath(), value);
    }

    public void setPermission(Player player, String permission, boolean value){
        getConfigFile().set("Permission." + player.getName() + "." + permission, value);
        save();
        setSpigotPermission(player, permission, value);
    }

    public void removePermission(Player player, Permission permission){
        setPermission(player, permission, false);
    }

    public void addPermission(Player player, Permission permission){
        setPermission(player, permission, true);
    }

    public boolean hasPermission(Player player, String permission){
        return player.hasPermission(permission);
    }

    public boolean hasPermission(Player player, Permission permission){
        return hasPermission(player, permission.getSavePath());
    }

    public void setupPermissions(Player player){
        if(!getConfigFile().isSet("Permission." + player.getName())) return;
        getConfigFile().getConfigurationSection("Permission." + player.getName()).getKeys(false).forEach(key -> {
            boolean value = getConfigFile().getBoolean("Permission." + player.getName() + "." + key);
            setSpigotPermission(player, key, value);
        });
    }

    private void setSpigotPermission(Player player, String permission, boolean value){
        PermissionAttachment attachment = player.addAttachment(TChallenges.getInstance());
        attachment.setPermission(permission, value);
    }

    private void setSpigotPermission(Player player, Permission permission, boolean value){
        setSpigotPermission(player, permission.getPermissionName(), value);
    }
}
