package de.webcode.tchallenges.utils.menu.impl;

import de.webcode.tchallenges.TChallenges;
import de.webcode.tchallenges.utils.ItemFactory;
import de.webcode.tchallenges.utils.menu.Menu;
import de.webcode.tchallenges.utils.menu.PaginatedMenu;
import de.webcode.tchallenges.utils.menu.playermenuutilitys.PlayerMenuUtility;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;

public class GameRuleMenu extends PaginatedMenu {

    public GameRuleMenu(PlayerMenuUtility playerMenuUtility){
        super(playerMenuUtility);
    }

    @Override
    public String getMenuName() {
        return "§e§lGamerules Setzen:";
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void handleMenu(InventoryClickEvent e) {

        ArrayList<GameRule<?>> gameRules = new ArrayList<>(Arrays.asList(GameRule.values()));


        ItemStack currentItem = e.getCurrentItem();
        if (currentItem.getType().equals(Material.BARRIER)) {
            new WorldSettingsMenu(playerMenuUtility).open();
        }else if(currentItem.getType().equals(Material.DARK_OAK_BUTTON)){
            if (currentItem.getItemMeta().getDisplayName().contains("<")){
                if (page != 0){
                    page = page - 1;
                    super.open();
                }
            }else if (currentItem.getItemMeta().getDisplayName().contains(">")){
                if (!((index + 1) >= gameRules.size())){
                    page = page + 1;
                    super.open();
                }
            }
        }

        Player owner = playerMenuUtility.getOwner();
        World world = owner.getWorld();

        if(currentItem.getType().equals(Material.STRUCTURE_VOID)){
            for(GameRule gameRule : GameRule.values()){
                world.setGameRule(gameRule, world.getGameRuleDefault(gameRule));
            }
            open();
            return;
        }

        if (currentItem.getType().equals(Material.BOOK)) {

            GameRule gameRule = getGameRulyByName(e.getCurrentItem().getItemMeta().getDisplayName());

            if(gameRule == null){
                owner.closeInventory();
                owner.sendMessage(TChallenges.PREFIX + "§cEs ist ein Fehler aufgetreten!");
                return;
            }
            Class type = gameRule.getType();

            if(type == Boolean.class){
                boolean value = (boolean) world.getGameRuleValue(gameRule);
                world.setGameRule(gameRule, !value);
                open();
            }

        }
    }

    private GameRule getGameRulyByName(String displayName){
        displayName = ChatColor.stripColor(displayName);
        displayName = displayName.replace(" ", "");
        displayName = displayName.replaceFirst(new String("" + displayName.charAt(0)), new String("" + Character.toLowerCase(displayName.charAt(0))));
        return GameRule.getByName(displayName);
    }


    @Override
    public void setMenuItems() {
        addMenuBorder();

        World world = playerMenuUtility.getOwner().getWorld();
        ItemFactory itemFactory = TChallenges.getInstance().getItemFactory();
        ArrayList<GameRule<?>> gameRules = new ArrayList<>(Arrays.asList(GameRule.values()));

        for(int i = 0; i < getMaxItemsPerPage(); i++) {
            index = getMaxItemsPerPage() * page + i;
            if(!(index >= gameRules.size())){
                if (gameRules.get(index) != null){
                    GameRule<?> gameRule = gameRules.get(index);
                    String name = gameRule.getName();
                    String s = capitalizeString(addSpaces(name), true);
                    Class<?> type = gameRule.getType();

                    String finalName = "";
                    ItemStack gameruleItem = itemFactory.create(Material.BOOK);
                    if(type == Boolean.class){
                        boolean value = (boolean) world.getGameRuleValue(gameRule);
                        finalName = value ? "§a"  + s : "§c" + s;
                        gameruleItem = itemFactory.addLore(gameruleItem, "§7Zurzeit" + (value ? " §a" : " §cnicht ") + "aktiviert");
                    }else if (type == Integer.class){
                        finalName = "§e" + s;
                        gameruleItem = itemFactory.addLore(gameruleItem, "§7Zurzeit: §e" + (int) world.getGameRuleValue(gameRule), "§7Linksklick = +", "§7Rechtsklick = -", "§7Shiftklick = Wert eingeben");
                    }

                    gameruleItem = itemFactory.setDisplayName(gameruleItem, finalName);

                    inventory.addItem(gameruleItem);
                }
            }else{
                ItemStack reset = itemFactory.create(Material.STRUCTURE_VOID, "§cZurücksetzen");
                inventory.addItem(reset);
                break;
            }
        }
    }

    private String addSpaces(String s){
        StringBuilder builder = new StringBuilder();
        builder.append(s);

        int count = 0;

        for(int i : getUpperCaseLetters(s)){
            builder.insert(count + i, " ");
            count++;
        }

        return builder.toString();
    }

    public String capitalizeString(String string, boolean touppercase) {
        char[] chars = string.toLowerCase().toCharArray();
        boolean found = false;
        for (int i = 0; i < chars.length; i++) {
            if (!found && Character.isLetter(chars[i])) {
                chars[i] = touppercase ? Character.toUpperCase(chars[i]) : Character.toLowerCase(chars[i]);
                found = true;
            } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'' || Character.isSpaceChar(chars[i])) {
                found = false;
            }
        }
        return String.valueOf(chars);
    }



    private ArrayList<Integer> getUpperCaseLetters(String s){
        ArrayList<Integer> r = new ArrayList<>();
        for(int i = 0; i < s.length(); i++){
            if (Character.isUpperCase(s.charAt(i))) {
                r.add(i);
            }
        }

        return r;
    }
}
