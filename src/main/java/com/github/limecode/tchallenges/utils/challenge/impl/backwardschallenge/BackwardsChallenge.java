package com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge;

import com.github.limecode.tchallenges.TChallenges;
import com.github.limecode.tchallenges.utils.ItemFactory;
import com.github.limecode.tchallenges.utils.challenge.api.TChallenge;
import com.github.limecode.tchallenges.utils.challenge.api.TCommand;
import com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge.commands.SetEndcityCommand;
import com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge.commands.ToggleCompassCommand;
import com.github.limecode.tchallenges.utils.challenge.impl.backwardschallenge.commands.TpToNetherCommand;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;

public class BackwardsChallenge extends TChallenge {
    private static BackwardsChallenge challengeInstance;
    private ActionBarCompass compass;

    @Override
    public void onChallengeEnable() {
        challengeInstance = this;
        Bukkit.getOnlinePlayers().forEach(this::setupPlayer);
        this.compass = new ActionBarCompass();
        new BukkitRunnable() {
            @Override
            public void run() {
                compass.start();
            }
        }.runTaskTimer(TChallenges.getInstance(), 0, 1);

    }

    private void setupPlayer(Player player){
        World end = Bukkit.getWorld("world_the_end");
        player.teleport(end.getHighestBlockAt(end.getSpawnLocation()).getLocation());
        ItemFactory itemFactory = TChallenges.getInstance().getItemFactory();

        ItemStack compass = itemFactory.create(Material.COMPASS, "§eStructure Finder");
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        ItemStack chestPlate = new ItemStack(Material.DIAMOND_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);
        boots.addEnchantment(Enchantment.PROTECTION_FALL, 3);

        ItemStack sword = new ItemStack(Material.IRON_SWORD);
        sword.addEnchantment(Enchantment.DAMAGE_ALL, 1);

        ItemStack food = new ItemStack(Material.COOKED_BEEF, 12);

        ItemStack tool1 = new ItemStack(Material.IRON_PICKAXE);
        tool1.addEnchantment(Enchantment.DURABILITY, 2);

        ItemStack tool2 = new ItemStack(Material.IRON_AXE);
        ItemStack tool3 = new ItemStack(Material.END_STONE, 64);

        ArrayList<ItemStack> items = new ArrayList<>();
        items.add(sword);
        items.add(tool1);
        items.add(tool2);
        items.add(tool3);
        items.add(helmet);
        items.add(chestPlate);
        items.add(leggings);
        items.add(boots);
        items.add(food);
        items.add(compass);

        Location loc = player.getLocation();
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                if (count >= items.size()) {
                    cancel();
                } else {
                    player.playSound(loc, Sound.BLOCK_BARREL_OPEN, 5, 2);
                    player.getInventory().addItem(items.get(count));
                    count++;
                }
            }
        }.runTaskTimer(TChallenges.getInstance(), 0, 14);

    }

    @Override
    public void onChallengeDisable() {

    }

    @Override
    public String getName() {
        return "Rückwärts durchspielen";
    }

    @Override
    public String getAuthor() {
        return "TheWebcode";
    }

    @Override
    public ArrayList<String> getDescription() {
        return new ArrayList<>(Arrays.asList("Minecraft rückwärts durchspielen.", "Man beginnt im Ende", "und arbeitet sich vor", "bis zur overworld"));
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    @Override
    public Material getChallengeIcon() {
        return Material.DRAGON_HEAD;
    }

    @Override
    public ArrayList<TCommand> getCommands() {
        return new ArrayList<TCommand>(Arrays.asList(
                new SetEndcityCommand(),
                new ToggleCompassCommand(),
                new TpToNetherCommand()));
    }

    @Override
    public ArrayList<Listener> getEventlisteners() {
        ArrayList<Listener> listeners = new ArrayList<>();
        listeners.add(new Eventlistener());
        return listeners;
    }

    public ActionBarCompass getCompass() {
        return compass;
    }

    public static BackwardsChallenge getChallengeInstance() {
        return challengeInstance;
    }

    @Override
    public JavaPlugin getInstance() {
        return TChallenges.getInstance();
    }
}
