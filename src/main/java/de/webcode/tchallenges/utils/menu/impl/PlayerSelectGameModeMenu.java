package de.webcode.tchallenges.utils.menu.impl;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.ItemFactory;
import de.webcode.tchallenges.utils.menu.Menu;
import de.webcode.tchallenges.utils.menu.playermenuutilitys.TargetPlayerMenuUtility;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerSelectGameModeMenu extends Menu {

    public PlayerSelectGameModeMenu(TargetPlayerMenuUtility playerMenuUtility){
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§e§lGamemode auswählen:";
    }

    @Override
    public int getSlots() {
        return 27;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();

        Player target = ((TargetPlayerMenuUtility) playerMenuUtility).getTarget();
        Player owner = playerMenuUtility.getOwner();
        switch(currentItem.getType()){
            case BARRIER:
                new PlayerMenu(playerMenuUtility).open();
                break;
            case STONE_SWORD:
                target.setGameMode(GameMode.SURVIVAL);
                owner.sendMessage(TChallenges.PREFIX + String.format("§aDer Gamemode für §6%s §awurde auf §6Survival §agesetzt", target.getName()));
                owner.closeInventory();

                break;
            case DIAMOND_SWORD:
                target.setGameMode(GameMode.ADVENTURE);
                owner.sendMessage(TChallenges.PREFIX + String.format("§aDer Gamemode für §6%s §awurde auf §6Adventure §agesetzt", target.getName()));
                owner.closeInventory();

                break;
            case ENCHANTED_GOLDEN_APPLE:
                target.setGameMode(GameMode.CREATIVE);
                owner.sendMessage(TChallenges.PREFIX + String.format("§aDer Gamemode für §6%s §awurde auf §6Creative §agesetzt", target.getName()));
                owner.closeInventory();

                break;
            case SPYGLASS:
                target.setGameMode(GameMode.SPECTATOR);
                owner.sendMessage(TChallenges.PREFIX + String.format("§aDer Gamemode für §6%s §awurde auf §6Spectator §agesetzt", target.getName()));
                owner.closeInventory();
                break;

        }
    }

    @Override
    public void setMenuItems() {
        ItemFactory itemFactory = TChallenges.getInstance().getItemFactory();

        ItemStack survival = itemFactory.create(Material.STONE_SWORD, "§aSurvival");
        ItemStack adventure = itemFactory.create(Material.DIAMOND_SWORD, "§aAdventure");
        ItemStack creative = itemFactory.create(Material.ENCHANTED_GOLDEN_APPLE, "§aCreative");
        ItemStack spectator = itemFactory.create(Material.SPYGLASS, "§aSpectator");
        ItemStack close = itemFactory.create(Material.BARRIER, "§cZurück");

        inventory.setItem(10, survival);
        inventory.setItem(12, adventure);
        inventory.setItem(14, creative);
        inventory.setItem(16, spectator);
        inventory.setItem(22, close);
    }
}
