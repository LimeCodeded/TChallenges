package de.webcode.tchallenges.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemFactory {

    public ItemStack create(Material material){
        return new ItemStack(material);
    }
    public ItemStack create(Material material, String name){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(Component.text(name));
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ItemStack create(Material material, String name, String... lore) {
        ItemStack itemStack = create(material, name);

        List<String> list = Arrays.asList(lore);
        ArrayList<Component> itemLore = stringListToTextComponent(list);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(itemLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ItemStack create(Material material, String name, int amount, String... lore) {
        ItemStack itemStack = create(material, name, lore);
        itemStack.setAmount(amount);

        return itemStack;
    }

    public ItemStack create(Material material, String name, int amount, boolean unbreakable, String... lore){
        ItemStack itemStack = create(material, name, amount, lore);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setUnbreakable(unbreakable);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public ArrayList<Component> stringListToTextComponent(List<String> list){
        ArrayList<Component> componentList = new ArrayList<>();

        list.forEach(s -> {
            Component component = Component.text(s);
            componentList.add(component);
        });

        return componentList;
    }

    public ItemStack getSkull(String skullTexture){
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put("textures", new Property("textures", skullTexture));

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = skull.getItemMeta();
        SkullMeta meta = (SkullMeta) itemMeta;
        try {
            Field f = meta.getClass().getDeclaredField("profile");
            f.setAccessible(true);
            f.set(meta, gameProfile);
            f.setAccessible(false);

        }catch (Exception e){
            e.printStackTrace();
        }

        skull.setItemMeta(meta);
        return skull;
    }

    public ItemStack setDisplayName(ItemStack itemStack, String displayName){
        ItemMeta meta = itemStack.getItemMeta();
        meta.displayName(Component.text(displayName));
        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public ItemStack addLore(ItemStack itemStack, String... lore){
        List<String> strings = Arrays.asList(lore);
        ArrayList<Component> itemLore = stringListToTextComponent(strings);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.lore(itemLore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }




}
