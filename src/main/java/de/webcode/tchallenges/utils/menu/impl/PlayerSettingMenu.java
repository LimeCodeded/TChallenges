package de.webcode.tchallenges.utils.menu.impl;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.ItemFactory;
import de.webcode.tchallenges.utils.menu.PaginatedMenu;
import de.webcode.tchallenges.utils.menu.anvilgui.AnvilGUI;
import de.webcode.tchallenges.utils.menu.playermenuutilitys.TargetPlayerMenuUtility;
import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerSettingMenu extends PaginatedMenu {

    private int pageSize = 2;

    public PlayerSettingMenu(TargetPlayerMenuUtility playerMenuUtility){
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§e§lSpieler §6§l" + ((TargetPlayerMenuUtility) playerMenuUtility).getTarget().getDisplayName();
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        String displayName = e.getCurrentItem().getItemMeta().getDisplayName();
        if (e.getCurrentItem().getType().equals(Material.BARRIER) && displayName.contains("Schließen")) {
            new PlayerMenu(playerMenuUtility).open();
            return;
        }

        Player player = (Player) e.getWhoClicked();
        Player target = ((TargetPlayerMenuUtility) playerMenuUtility).getTarget();

        switch (ChatColor.stripColor(displayName).toLowerCase()) {
            case "spieler töten":
                new KillConfirmMenu((TargetPlayerMenuUtility) playerMenuUtility).open();
                break;
            case "spieler hierher teleportieren":
                player.closeInventory();
                player.sendMessage(TChallenges.PREFIX + String.format("§aDu hast §6%s §azu dir Teleportiert!", target.getName()));
                target.teleport(player);
                break;
            case "zu spieler teleportieren":
                player.closeInventory();
                player.sendMessage(TChallenges.PREFIX + String.format("§aDu wurdest zu §6%s §ateleportiert!", target.getName()));
                player.teleport(target);
                break;
            case "berechtigungen verwalten":
                //TODO
                break;
            case "gamemode setzen":
                TargetPlayerMenuUtility targetPlayerMenuUtility = (TargetPlayerMenuUtility) playerMenuUtility;
                new PlayerSelectGameModeMenu(targetPlayerMenuUtility).open();
                break;
            case "inventar ansehen":
                player.openInventory(target.getInventory());
                player.sendMessage(TChallenges.PREFIX + String.format("§aDu hast das Inventar von §6%s §ageöffnet!", target.getName()));
                break;
            case "spieler kicken":
                new AnvilGUI.Builder().text("§eGrund für den Kick:").plugin(TChallenges.getInstance()).onComplete((p, text) -> {
                    player.sendMessage(TChallenges.PREFIX + String.format("§6%s §awurde gekickt", target.getName()));
                    target.kick(Component.text(text.replace("&", "§")));
                    return AnvilGUI.Response.close();
                }).open(player);
                break;
            case "spieler bannen":
                new AnvilGUI.Builder().text("§eGrund für den Ban:").plugin(TChallenges.getInstance()).onComplete((p, text) -> {
                    player.sendMessage(TChallenges.PREFIX + String.format("§6%s §awurde gebannt", target.getName()));
                    target.banPlayer(text);
                    return AnvilGUI.Response.close();
                }).open(player);
                break;
        }

        if (e.getCurrentItem().getType().equals(Material.DARK_OAK_BUTTON)) {
            if (displayName.contains("<")) {
                if (page != 0) {
                    page = page - 1;
                    super.open();
                }
            } else if (displayName.contains(">")) {
                if (!((page + 1) >= pageSize)) {
                    page = page + 1;
                    super.open();
                }
            }
        }
    }

    @Override
    public void setMenuItems () {
        addMenuBorder();

        ItemFactory itemFactory = TChallenges.getInstance().getItemFactory();
        ItemStack killPlayer = itemFactory.create(Material.BARRIER, "§cSpieler töten");
        ItemStack teleportPlayer = itemFactory.create(Material.ENDER_PEARL, "§aSpieler hierher teleportieren");
        ItemStack teleportToPlayer = itemFactory.create(Material.ENDER_EYE, "§aZu Spieler teleportieren");
        ItemStack managePermissions = itemFactory.create(Material.WRITABLE_BOOK, "§aBerechtigungen verwalten");
        ItemStack setGameMode = itemFactory.create(Material.COMMAND_BLOCK, "§aGamemode setzen");
        ItemStack openInventory = itemFactory.create(Material.CHEST, "§aInventar ansehen");
        ItemStack kickPlayer = itemFactory.create(Material.STRUCTURE_VOID, "§aSpieler kicken");
        ItemStack banPlayer = itemFactory.create(Material.BARRIER, "§cSpieler bannen");
        ItemStack spectatePlayer = itemFactory.create(Material.SPYGLASS, "§aSpieler beobachten");

        switch (page) {
            case 0:
                inventory.setItem(20, killPlayer);
                inventory.setItem(22, teleportPlayer);
                inventory.setItem(24, teleportToPlayer);
                inventory.setItem(29, managePermissions);
                inventory.setItem(31, setGameMode);
                inventory.setItem(33, openInventory);
                break;
            case 1:
                inventory.setItem(20, kickPlayer);
                inventory.setItem(22, banPlayer);
                break;
            }
        }
}
