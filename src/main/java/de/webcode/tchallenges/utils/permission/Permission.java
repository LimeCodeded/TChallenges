package de.webcode.tchallenges.utils.permission;

public enum Permission {
    PLACE_BLOCKS("tchallenges.permission.place_blocks", "permission_place_blocks", "Blöcke Platzieren"),
    BREAK_BLOCKS("tchallenges.permission.break_blocks", "permission_break_blocks", "Blöcke entfernen"),
    OP_SELF("tchallenges.permission.op_self", "permission_op_self", "Selbst-OP"),
    COMMAND_SETTING("tchallenges.command.settings", "permission_command_settings", "Settings-Command"),
    COMMAND_SUICIDE("tchallenges.command.suicide", "permission_command_suicide", "Suicide-Command"),
    ;
    private String permissionName;
    private String savePath;
    private String displayName;

    Permission(String permissionName, String savePath, String displayName){
        this.permissionName = permissionName;
        this.savePath = savePath;
        this.displayName = displayName;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public String getSavePath() {
        return savePath;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static Permission getByName(String name){
        for(Permission permission : Permission.values()){
            if (permission.getPermissionName().toLowerCase().equals(name.toLowerCase())) {
                return permission;
            }
        }

        return null;
    }

    public static Permission getBySavePath(String path){
        for(Permission permission : Permission.values()){
            if (permission.getSavePath().toLowerCase().equals(path.toLowerCase())) {
                return permission;
            }
        }

        return null;
    }

    public static Permission getByDisplayName(String displayName){
        for(Permission permission : Permission.values()){
            if(permission.getDisplayName().toLowerCase().equals(displayName.toLowerCase())){
                return permission;
            }
        }

        return null;
    }
}
