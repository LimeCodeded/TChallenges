package com.github.limecode.tchallenges.utils.menu.impl;

import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.ItemFactory;
import com.github.limecode.tchallenges.utils.menu.PaginatedMenu;
import com.github.limecode.tchallenges.utils.menu.playermenuutilitys.PlayerMenuUtility;
import com.github.limecode.tchallenges.utils.menu.playermenuutilitys.TargetPlayerMenuUtility;
import com.github.limecode.tchallenges.utils.setting.Setting;
import com.github.limecode.tchallenges.utils.setting.SettingManager;
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
import java.util.Arrays;
import java.util.UUID;

public class ManageSettingsMenu extends PaginatedMenu {

    public ManageSettingsMenu(PlayerMenuUtility playerMenuUtility) {
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§e§lServereinstellungen:";
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

        if (currentItem.getType().equals(Material.REPEATING_COMMAND_BLOCK)) {
            Player player = (Player) e.getWhoClicked();
            Setting s = Setting.getByDisplayname(currentItem.getItemMeta().getDisplayName());

            if(s == null) return;

            SettingManager settingManager = TChallenges.getInstance().getSettingManager();
            boolean status = settingManager.getSetting(s);

            settingManager.set(s, !status);
            open();
        }
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();

        ArrayList<Setting> settings = new ArrayList<>(Arrays.asList(Setting.values()));
        ItemFactory itemFactory = TChallenges.getInstance().getItemFactory();
        SettingManager settingManager = TChallenges.getInstance().getSettingManager();

        for (int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;
            if (index >= settings.size()) break;

            if (settings.get(index) != null) {
                ItemStack playerItem = new ItemStack(Material.REPEATING_COMMAND_BLOCK, 1);
                ItemMeta meta = playerItem.getItemMeta();
                meta.displayName(Component.text("§e" + settings.get(index).getDisplayName()));
                playerItem.setItemMeta(meta);
                playerItem = itemFactory.addLore(playerItem, "§7Status: " + (settingManager.getSetting(settings.get(index)) ? "§aAktiv" : "§cDeaktiviert"));
                inventory.addItem(playerItem);
            }
        }
    }
}
