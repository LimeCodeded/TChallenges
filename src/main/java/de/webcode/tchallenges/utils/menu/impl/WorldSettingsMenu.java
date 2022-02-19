package de.webcode.tchallenges.utils.menu.impl;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.ItemFactory;
import de.webcode.tchallenges.utils.menu.Menu;
import de.webcode.tchallenges.utils.menu.playermenuutilitys.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class WorldSettingsMenu extends Menu {

    public WorldSettingsMenu(PlayerMenuUtility playerMenuUtility){
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§e§lWelt-Einstellungen:";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();

        switch(currentItem.getType()){
            case BARRIER:
                new ServerSettingsMenu(playerMenuUtility).open();
                break;
            case CLOCK:
                new WorldTimeMenu(playerMenuUtility).open();
                break;
            case PLAYER_HEAD:
                //TODO
                break;
            case WRITABLE_BOOK:
                new GameRuleMenu(playerMenuUtility).open();
                break;
        }
    }

    @Override
    public void setMenuItems() {
        ItemFactory itemFactory = TChallenges.getInstance().getItemFactory();

        ItemStack reset = itemFactory.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzI1MThkMDRmOWMwNmM5NWRkMGVkYWQ2MTdhYmI5M2QzZDg2NTdmMDFlNjU5MDc5ZDMzMGNjYTZmNjViY2NmNyJ9fX0=");
        itemFactory.setDisplayName(reset, "§cWelt neugenerieren");

        ItemStack time = itemFactory.create(Material.CLOCK, "§aZeit setzen");
        ItemStack itemStack = itemFactory.getSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDZiYTYzMzQ0ZjQ5ZGQxYzRmNTQ4OGU5MjZiZjNkOWUyYjI5OTE2YTZjNTBkNjEwYmI0MGE1MjczZGM4YzgyIn19fQ==");
        itemStack = itemFactory.setDisplayName(itemStack, "§cComing Soon");
        ItemStack gamerules = itemFactory.create(Material.WRITABLE_BOOK, "§aGamerules");
        ItemStack back = itemFactory.create(Material.BARRIER, "§cZurück");

        inventory.setItem(11, time);
        inventory.setItem(13, itemStack);
        inventory.setItem(15, gamerules);
        inventory.setItem(22, back);
    }
    /*
    *
    * World Settings:
    * Time
    * Reset
    * GameRules
    * */


}
