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

public class KillPlayerMenu extends PaginatedMenu {
    public KillPlayerMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§eSpieler auswählen:";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        ArrayList<Player> players = new ArrayList<>(Bukkit.getServer().getOnlinePlayers());

        if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {

            PlayerMenuUtility playerMenuUtility = TChallenges.getInstance().getPlayerMenuUtilityManager().getPlayerMenuUtility(p);
            Player target = Bukkit.getPlayer(UUID.fromString(e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(new NamespacedKey(TChallenges.getInstance(), "uuid"), PersistentDataType.STRING)));

            new KillConfirmMenu(new TargetPlayerMenuUtility(playerMenuUtility.getOwner(), target)).open();

        }else if (e.getCurrentItem().getType().equals(Material.BARRIER)) {

            p.closeInventory();

        }else if(e.getCurrentItem().getType().equals(Material.DARK_OAK_BUTTON)){
            if (e.getCurrentItem().getItemMeta().getDisplayName().contains("<")){
                if (page != 0){
                    page = page - 1;
                    super.open();
                }
            }else if (e.getCurrentItem().getItemMeta().getDisplayName().contains(">")){
                if (!((index + 1) >= players.size())){
                    page = page + 1;
                    super.open();
                }
            }
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
                    SkullMeta skullMeta = (SkullMeta) playerMeta;
                    skullMeta.displayName(Component.text("§e" + players.get(index).getDisplayName()));

                    skullMeta.getPersistentDataContainer().set(new NamespacedKey(TChallenges.getInstance(), "uuid"), PersistentDataType.STRING, players.get(index).getUniqueId().toString());
                    skullMeta.setOwningPlayer(players.get(index));
                    playerItem.setItemMeta(playerMeta);

                    inventory.addItem(playerItem);
                }
            }
        }
    }

}
