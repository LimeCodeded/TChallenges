package com.github.limecode.tchallenges.utils.menu.impl;

import com.github.limecode.tchallenges.utils.menu.anvilgui.AnvilGUI;
import com.github.limecode.tchallenges.utils.menu.playermenuutilitys.PlayerMenuUtility;
import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.ItemFactory;
import com.github.limecode.tchallenges.utils.menu.Menu;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class WorldTimeMenu extends Menu {

    public WorldTimeMenu(PlayerMenuUtility playerMenuUtility){
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§e§lZeit setzen:";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();
        World world = playerMenuUtility.getOwner().getWorld();


        switch(currentItem.getType()){
            case PAPER:
                new AnvilGUI.Builder().text("Zeit eingeben").plugin(TChallenges.getInstance()).onComplete((p, text) -> {
                    try{
                        long time = Long.parseLong(text);
                        world.setTime(time);
                        p.sendMessage(TChallenges.PREFIX + String.format("§aDu hast die Zeit auf §6%s §agesetzt!", time));
                        return AnvilGUI.Response.close();
                    }catch(NumberFormatException exception){
                        return AnvilGUI.Response.text("Bitte gebe eine Gültige Zahl ein");
                    }
                }).open(playerMenuUtility.getOwner());
                break;
            case CLOCK:
                String displayName = currentItem.getItemMeta().getDisplayName();
                if (displayName.contains("Tag")) {
                    world.setTime(0);
                    playerMenuUtility.getOwner().sendMessage(TChallenges.PREFIX + "§aDu hast die Zeit auf §6Tag §agestellt!");

                } else if (displayName.contains("Nacht")) {
                    world.setTime(18000);
                    playerMenuUtility.getOwner().sendMessage(TChallenges.PREFIX + "§aDu hast die Zeit auf §6Nacht §agestellt!");
                }
                break;
            case BARRIER:
                new WorldSettingsMenu(playerMenuUtility).open();
                break;
        }

    }

    @Override
    public void setMenuItems() {
        ItemFactory itemFactory = TChallenges.getInstance().getItemFactory();

        ItemStack day = itemFactory.create(Material.CLOCK, "§aTag");
        ItemStack night = itemFactory.create(Material.CLOCK, "§aNacht");
        ItemStack manual = itemFactory.create(Material.PAPER, "§aManuelle Eingabe");
        ItemStack back = itemFactory.create(Material.BARRIER, "§cZurück");

        inventory.setItem(11, day);
        inventory.setItem(13, night);
        inventory.setItem(15, manual);
        inventory.setItem(22, back);
    }
}
