package de.webcode.tchallenges.utils.menu.impl;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.ChallengeTimer;
import de.webcode.tchallenges.utils.ItemFactory;
import de.webcode.tchallenges.utils.menu.Menu;
import de.webcode.tchallenges.utils.menu.playermenuutilitys.PlayerMenuUtility;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class TimerMenu extends Menu {

    public TimerMenu(PlayerMenuUtility playerMenuUtility){
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§e§lTimer einstellen:";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();
        Material type = currentItem.getType();
        ChallengeTimer timer = TChallenges.getInstance().getChallengeTimer();
        Player player = playerMenuUtility.getOwner();

        if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
            new SettingMenu(playerMenuUtility).open();
            return;
        }

        switch(type){
            case LIME_CONCRETE:
                timer.setRunning(true);
                player.getInventory().close();
                break;
            case RED_CONCRETE:
                timer.setTime(0);
                timer.setRunning(false);
                player.getInventory().close();
            case YELLOW_CONCRETE:
                timer.setRunning(false);
                player.getInventory().close();
                break;
        }

    }

    @Override
    public void setMenuItems() {
        ItemFactory itemFactory = TChallenges.getInstance().getItemFactory();

        ItemStack back = itemFactory.create(Material.BARRIER, "§cZurück");
        ItemStack startTimer = itemFactory.create(Material.LIME_CONCRETE, "§aTimer starten");
        ItemStack stopTimer = itemFactory.create(Material.YELLOW_CONCRETE, "§cTimer anhalten");
        ItemStack resetTimer = itemFactory.create(Material.RED_CONCRETE, "§cTimer zurücksetzen");

        inventory.setItem(11, startTimer);
        inventory.setItem(13, stopTimer);
        inventory.setItem(15, resetTimer);
        inventory.setItem(22, back);
    }
}
