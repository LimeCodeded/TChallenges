package com.github.limecode.tchallenges.utils.menu.impl;

import com.github.limecode.tchallenges.utils.menu.Menu;
import com.github.limecode.tchallenges.utils.menu.playermenuutilitys.TargetPlayerMenuUtility;
import com.github.limecode.tchallenges.TChallenges;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class KillConfirmMenu extends Menu {
    public KillConfirmMenu(TargetPlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        TargetPlayerMenuUtility killPlayerMenuUtility = (TargetPlayerMenuUtility) playerMenuUtility;
        return "§a" + killPlayerMenuUtility.getTarget().getName() + " §etöten?";
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
                Player toKill = ((TargetPlayerMenuUtility) playerMenuUtility).getTarget();
                toKill.setHealth(0);
                e.getWhoClicked().sendMessage(TChallenges.PREFIX + String.format("§6%s §awurde getötet", toKill.getName()));
                break;
            case BARRIER:
                new KillPlayerMenu(playerMenuUtility).open();
                break;
        }

    }

    @Override
    public void setMenuItems() {

        ItemStack yes = new ItemStack(Material.EMERALD, 1);
        ItemMeta yes_meta = yes.getItemMeta();
        yes_meta.setDisplayName("§aJa");
        yes.setItemMeta(yes_meta);
        ItemStack no = new ItemStack(Material.BARRIER, 1);
        ItemMeta no_meta = no.getItemMeta();
        no_meta.setDisplayName("§cNein");
        no.setItemMeta(no_meta);

        inventory.setItem(3, yes);
        inventory.setItem(5, no);

        setFillerGlass();

    }

}
