package com.github.limecode.tchallenges.utils.menu;

import com.github.limecode.tchallenges.utils.menu.playermenuutilitys.PlayerMenuUtility;
import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.ItemFactory;
import org.bukkit.Material;

public abstract class PaginatedMenu extends Menu{
    protected int page = 0;
    protected int maxItemsPerPage = 28;
    protected int index = 0;

    public PaginatedMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    public void addMenuBorder(){
        ItemFactory itemFactory = TChallenges.getInstance().getItemFactory();
        inventory.setItem(48, itemFactory.create(Material.DARK_OAK_BUTTON, "§a<"));

        inventory.setItem(49, itemFactory.create(Material.BARRIER, "§cSchließen"));

        inventory.setItem(50, itemFactory.create(Material.DARK_OAK_BUTTON, "§a>"));

        for (int i = 0; i < 10; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }

        inventory.setItem(17, super.FILLER_GLASS);
        inventory.setItem(18, super.FILLER_GLASS);
        inventory.setItem(26, super.FILLER_GLASS);
        inventory.setItem(27, super.FILLER_GLASS);
        inventory.setItem(35, super.FILLER_GLASS);
        inventory.setItem(36, super.FILLER_GLASS);

        for (int i = 44; i < 54; i++) {
            if (inventory.getItem(i) == null) {
                inventory.setItem(i, super.FILLER_GLASS);
            }
        }
    }

    public int getMaxItemsPerPage() {
        return maxItemsPerPage;
    }

}
