package com.github.limecode.tchallenges.utils.menu.impl;

import com.github.limecode.tchallenges.utils.menu.anvilgui.AnvilGUI;
import com.github.limecode.tchallenges.utils.menu.playermenuutilitys.TargetPlayerMenuUtility;
import com.github.limecode.tchallenges.utils.permission.Permission;
import com.github.limecode.tchallenges.utils.permission.PermissionManagement;
import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.ItemFactory;
import com.github.limecode.tchallenges.utils.menu.PaginatedMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ManagePlayerPermissionsMenu extends PaginatedMenu implements PermissionManagement {

    public ManagePlayerPermissionsMenu(TargetPlayerMenuUtility playerMenuUtility){
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "Berechtigungen für " + ((TargetPlayerMenuUtility) playerMenuUtility).getTarget().getName();
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();
        String displayName = currentItem.getItemMeta().getDisplayName();

        if (currentItem.getType().equals(Material.BARRIER)) {
            new PlayerMenu(playerMenuUtility).open();
            return;
        }

        if (e.getCurrentItem().getType().equals(Material.DARK_OAK_BUTTON)) {
            if (displayName.contains("<")) {
                if (page != 0) {
                    page = page - 1;
                    super.open();
                }
            } else if (displayName.contains(">")) {

                ArrayList<String> permissions = new ArrayList<>();

                for (Permission permission : Permission.values()) {
                    permissions.add(permission.getSavePath());
                }

                if (!((index + 1) >= permissions.size())) {
                    page = page + 1;
                    super.open();
                }
            }
        }

        if (currentItem.getType().equals(Material.CHAIN_COMMAND_BLOCK)) {
            Player target = ((TargetPlayerMenuUtility) playerMenuUtility).getTarget();
            Permission permission = Permission.getByDisplayName(displayName);

            if(permission != null){
                boolean value = hasPermission(target, permission);
                setPermission(target, permission, !value);
                open();
                return;
            }

            return;
        }

        if (currentItem.getType().equals(Material.WRITABLE_BOOK)) {
            Player target = ((TargetPlayerMenuUtility) playerMenuUtility).getTarget();
            Player player = playerMenuUtility.getOwner();
            if(e.isLeftClick()){
                new AnvilGUI.Builder().text("Permission").plugin(TChallenges.getInstance()).onComplete((p, text) -> {
                    setPermission(target, text, true);
                    player.sendMessage(TChallenges.PREFIX + String.format("§aDu hast §6%s §adie Permission §6%s §ahinzugefügt!", target.getName(), text));
                    return AnvilGUI.Response.close();
                }).open(player);
            }else{
                new AnvilGUI.Builder().text("Permission").plugin(TChallenges.getInstance()).onComplete((p, text) -> {
                    setPermission(target, text, false);
                    player.sendMessage(TChallenges.PREFIX + String.format("§aDu hast §6%s §adie Permission §6%s §aentfernt!", target.getName(), text));
                    return AnvilGUI.Response.close();
                }).open(player);
            }
        }
    }

    @Override
    public void setMenuItems() {
        ItemFactory itemFactory = TChallenges.getInstance().getItemFactory();
        Player target = ((TargetPlayerMenuUtility) playerMenuUtility).getTarget();

        addMenuBorder();

        ArrayList<String> permissions = new ArrayList<>();

        for(Permission permission : Permission.values()){
            permissions.add(permission.getDisplayName());
        }

        for(int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;

            ItemStack[] contents = inventory.getContents();

            boolean addBook = true;
            if(contents != null){
                for(int j = 0; j < contents.length; j++){
                    if(contents[j] != null){
                        if(contents[j].getType().equals(Material.WRITABLE_BOOK)) addBook = false;
                    }else continue;
                }
            }

            if(page == 0 && addBook){
                ItemStack manual = itemFactory.create(Material.WRITABLE_BOOK, "§cManuell hinzufügen/entfernen");
                manual = itemFactory.addLore(manual, "§7Linksklick = Hinzufügen", "§7Rechtsklick = Entfernen");
                inventory.addItem(manual);
            }

            if(!(index >= permissions.size())){
                Permission perm = Permission.getByDisplayName(permissions.get(index));
                if (permissions.get(index) != null){
                    ItemStack permissionItem = itemFactory.create(Material.CHAIN_COMMAND_BLOCK, permissions.get(index));
                    boolean b = hasPermission(target, perm.getSavePath());
                    permissionItem = itemFactory.addLore(permissionItem, "§7Zurzeit auf " + (b ? "§atrue" : "§cfalse"));
                    inventory.addItem(permissionItem);
                }
            }else{

            }
        }
    }
}
