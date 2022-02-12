package de.webcode.tchallenges.utils.menu.impl;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.ItemFactory;
import de.webcode.tchallenges.utils.menu.Menu;
import de.webcode.tchallenges.utils.menu.PaginatedMenu;
import de.webcode.tchallenges.utils.menu.playermenuutilitys.PlayerMenuUtility;
import org.bukkit.GameRule;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SettingMenu extends Menu {

    public SettingMenu(PlayerMenuUtility playerMenuUtility){
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§e§lEinstellungen";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        if (e.getCurrentItem().getType().equals(Material.BARRIER) && e.getCurrentItem().getItemMeta().getDisplayName().contains("Schließen")) {
            e.getWhoClicked().closeInventory();
        }


        switch(e.getCurrentItem().getType()){
            case COMMAND_BLOCK:
                new ServerSettingsMenu(playerMenuUtility).open();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemFactory itemFactory = TChallenges.getInstance().getItemFactory();
        ItemStack close = itemFactory.create(Material.BARRIER, "§cSchließen");
        ItemStack timer = itemFactory.create(Material.CLOCK, "§aTimer", "§aTimer einstellen");
        ItemStack challenges = itemFactory.create(Material.WRITABLE_BOOK, "§aChallenges", "§aChallenges aktivieren und deaktivieren");
        ItemStack server = itemFactory.create(Material.COMMAND_BLOCK, "§aServer", "§aServer Einstellungen");

        inventory.setItem(10, timer);
        inventory.setItem(12, server);
        inventory.setItem(14, challenges);
        inventory.setItem(16, close);
    }
}
