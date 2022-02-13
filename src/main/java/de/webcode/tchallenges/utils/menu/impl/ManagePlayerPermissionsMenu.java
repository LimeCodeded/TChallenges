package de.webcode.tchallenges.utils.menu.impl;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.ItemFactory;
import de.webcode.tchallenges.utils.menu.PaginatedMenu;
import de.webcode.tchallenges.utils.menu.anvilgui.AnvilGUI;
import de.webcode.tchallenges.utils.menu.playermenuutilitys.TargetPlayerMenuUtility;
import de.webcode.tchallenges.utils.permission.Permission;
import de.webcode.tchallenges.utils.permission.PermissionManagement;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

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

        if (currentItem.getType().equals(Material.CHAIN_COMMAND_BLOCK)) {
            Player target = ((TargetPlayerMenuUtility) playerMenuUtility).getTarget();
            Permission permission = Permission.getByDisplayName(displayName);
            boolean value = hasPermission(target, permission);
            setPermission(target, permission, !value);
            open();
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

        ArrayList<Permission> permissions = new ArrayList<>(Arrays.asList(Permission.values()));
        for(int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;
            if(!(index >= permissions.size())){
                if (permissions.get(index) != null){
                    ItemStack permissionItem = itemFactory.create(Material.CHAIN_COMMAND_BLOCK, permissions.get(index).getDisplayName());
                    boolean b = hasPermission(target, permissions.get(index));
                    permissionItem = itemFactory.addLore(permissionItem, "§7Zurzeit auf " + (b ? "§atrue" : "§cfalse"));
                    inventory.addItem(permissionItem);
                }
            }else{
                ItemStack manual = itemFactory.create(Material.WRITABLE_BOOK, "§cManuell hinzufügen/entfernen");
                manual = itemFactory.addLore(manual, "§7Linksklick = Hinzufügen", "§7Rechtsklick = Entfernen");
                inventory.addItem(manual);
                break;
            }
        }
    }
}
