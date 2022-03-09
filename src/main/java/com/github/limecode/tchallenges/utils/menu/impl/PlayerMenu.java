package com.github.limecode.tchallenges.utils.menu.impl;

import com.github.limecode.tchallenges.utils.menu.playermenuutilitys.PlayerMenuUtility;
import com.github.limecode.tchallenges.utils.menu.playermenuutilitys.TargetPlayerMenuUtility;
import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.menu.PaginatedMenu;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerMenu extends PaginatedMenu {

    public PlayerMenu(PlayerMenuUtility playerMenuUtility){
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§eSpieler";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();
        if (currentItem.getType().equals(Material.BARRIER)) {
            new ServerSettingsMenu(playerMenuUtility).open();
        }else if(currentItem.getType().equals(Material.DARK_OAK_BUTTON)){
            if (currentItem.getItemMeta().getDisplayName().contains("<")){
                if (page != 0){
                    page = page - 1;
                    super.open();
                }
            }else if (currentItem.getItemMeta().getDisplayName().contains(">")){
                if (!((index + 1) >= Bukkit.getOnlinePlayers().size())){
                    page = page + 1;
                    super.open();
                }
            }
        }

        if (currentItem.getType().equals(Material.PLAYER_HEAD)) {
            Player player = (Player) e.getWhoClicked();
            PlayerMenuUtility playerMenuUtility = TChallenges.getInstance().getPlayerMenuUtilityManager().getPlayerMenuUtility(player);
            Player target = Bukkit.getPlayer(UUID.fromString(currentItem.getItemMeta().getPersistentDataContainer().get(new NamespacedKey(TChallenges.getInstance(), "uuid"), PersistentDataType.STRING)));

            new PlayerSettingMenu(new TargetPlayerMenuUtility(player, target)).open();
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();

        ArrayList<Player> players = new ArrayList<Player>(Bukkit.getServer().getOnlinePlayers());

        if(players != null && !players.isEmpty()) {
            for(int i = 0; i < getMaxItemsPerPage(); i++) {
                index = getMaxItemsPerPage() * page + i;
                if(index >= players.size()) break;
                if (players.get(index) != null){
                    ItemStack playerItem = new ItemStack(Material.PLAYER_HEAD, 1);
                    ItemMeta playerMeta = playerItem.getItemMeta();
                    SkullMeta meta = (SkullMeta) playerMeta;
                    meta.displayName(Component.text("§e" + players.get(index).getDisplayName()));

                    meta.getPersistentDataContainer().set(new NamespacedKey(TChallenges.getInstance(), "uuid"), PersistentDataType.STRING, players.get(index).getUniqueId().toString());
                    meta.setOwningPlayer(players.get(index));
                    playerItem.setItemMeta(playerMeta);

                    inventory.addItem(playerItem);
                }
            }
        }
    }
}
