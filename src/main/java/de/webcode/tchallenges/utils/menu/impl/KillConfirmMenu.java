package de.webcode.tchallenges.utils.menu.impl;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.menu.Menu;
import de.webcode.tchallenges.utils.menu.playermenuutilitys.KillPlayerMenuUtility;
import de.webcode.tchallenges.utils.menu.playermenuutilitys.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class KillConfirmMenu extends Menu {
    public KillConfirmMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        KillPlayerMenuUtility killPlayerMenuUtility = (KillPlayerMenuUtility) playerMenuUtility;
        return "§a" + killPlayerMenuUtility.getPlayerToKill().getName() + " §etöten?";
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
                Player toKill = ((KillPlayerMenuUtility) playerMenuUtility).getPlayerToKill();
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
