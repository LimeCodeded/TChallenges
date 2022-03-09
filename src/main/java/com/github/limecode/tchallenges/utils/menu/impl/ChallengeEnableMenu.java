package com.github.limecode.tchallenges.utils.menu.impl;

import com.github.limecode.tchallenges.utils.challenge.ChallengeManager;
import com.github.limecode.tchallenges.utils.challenge.api.TChallenge;
import com.github.limecode.tchallenges.utils.menu.playermenuutilitys.PlayerMenuUtility;
import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.ItemFactory;
import com.github.limecode.tchallenges.utils.menu.PaginatedMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ChallengeEnableMenu extends PaginatedMenu {

    public ChallengeEnableMenu(PlayerMenuUtility playerMenuUtility){
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§e§lChallenges: ";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {
        ItemStack currentItem = e.getCurrentItem();

        ChallengeManager challengeManager = TChallenges.getInstance().getChallengeManager();
        ArrayList<TChallenge> challenges = challengeManager.getChallenges();

        if (currentItem.getType().equals(Material.BARRIER)) {
            new SettingMenu(playerMenuUtility).open();
            return;
        }else if(currentItem.getType().equals(Material.DARK_OAK_BUTTON)){
            if (currentItem.getItemMeta().getDisplayName().contains("<")){
                if (page != 0){
                    page = page - 1;
                    super.open();
                }
            }else if (currentItem.getItemMeta().getDisplayName().contains(">")){
                if (!((index + 1) >= challenges.size())){
                    page = page + 1;
                    super.open();
                }
            }
        }

        if(currentItem.getType().equals(super.FILLER_GLASS.getType())) return;

        String name = ChatColor.stripColor(currentItem.getItemMeta().getDisplayName());
        TChallenge challenge = challengeManager.getChallengeByName(name);

        challengeManager.toggleChallenge(challenge);
        open();
    }

    @Override
    public void setMenuItems() {
        addMenuBorder();

        ItemFactory itemFactory = TChallenges.getInstance().getItemFactory();

        ArrayList<TChallenge> challenges = TChallenges.getInstance().getChallengeManager().getChallenges();

        for(int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;
            if(!(index >= challenges.size())){
                if (challenges.get(index) != null){
                    TChallenge challenge = challenges.get(index);

                    ItemStack challengeitem = itemFactory.create(challenge.getChallengeIcon(), "§a" + challenge.getName());
                    boolean enabled = TChallenges.getInstance().getChallengeManager().isChallengeEnabled(challenge);
                    challengeitem = itemFactory.addLore(challengeitem, "§7Von: §e" + challenge.getAuthor(), "§7Status: " + (enabled ? "§aAktiv" : "§cDeaktiviert"));
                    ArrayList<String> description = challenge.getDescription();
                    description.add("§7Status: " + (enabled ? "§aAktiv" : "§cDeaktiviert"));
                    challengeitem = itemFactory.addLore(challengeitem, description);


                    inventory.addItem(challengeitem);
                }
            }
        }
    }
}
