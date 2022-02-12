package de.webcode.tchallenges.utils.menu.impl;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.menu.Menu;
import de.webcode.tchallenges.utils.menu.playermenuutilitys.PlayerMenuUtility;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SuicideConfirmMenu extends Menu {

    public SuicideConfirmMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§eSicher?";
    }

    @Override
    public int getSlots() {
        return 9;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        switch (e.getCurrentItem().getType()){
            case EMERALD:
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().setHealth(0.0);
                break;
            case BARRIER:
                e.getWhoClicked().sendMessage(TChallenges.PREFIX + "Abgebrochen");
                e.getWhoClicked().closeInventory();
                break;
        }

    }

    @Override
    public void setMenuItems() {

        ItemStack yes = new ItemStack(Material.EMERALD, 1);
        ItemMeta yes_meta = yes.getItemMeta();
        yes_meta.displayName(Component.text("§aJa"));
        yes.setItemMeta(yes_meta);
        ItemStack no = new ItemStack(Material.BARRIER, 1);
        ItemMeta no_meta = no.getItemMeta();
        no_meta.displayName(Component.text("§cNein"));
        no.setItemMeta(no_meta);

        inventory.setItem(3, yes);
        inventory.setItem(5, no);

        setFillerGlass();

    }

}
