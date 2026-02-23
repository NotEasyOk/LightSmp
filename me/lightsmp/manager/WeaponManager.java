package me.lightsmp.weapons;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;
import me.lightsmp.Main;

public class WeaponManager {
    public static ItemStack DOOM_MACE, DRAGON_WING, SPEAR;

    public static void init() {
        createDoomMace();
        createDragonWing();
        createSpear();
    }

    private static void createDoomMace() {
        ItemStack item = new ItemStack(Material.MACE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_RED + "Doom Mace");
        meta.setCustomModelData(1001);
        item.setItemMeta(meta);
        DOOM_MACE = item;

        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(), "doom_mace"), item);
        r.shape("BBB", "BTB", "SSS"); // B=Netherite Block, T=Totem, S=Nether Star
        r.setIngredient('B', Material.NETHERITE_BLOCK);
        r.setIngredient('T', Material.TOTEM_OF_UNDYING);
        r.setIngredient('S', Material.NETHER_STAR);
        Bukkit.addRecipe(r);
    }

    private static void createDragonWing() {
        ItemStack item = new ItemStack(Material.ELYTRA);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Dragon Wing");
        meta.setCustomModelData(1002);
        item.setItemMeta(meta);
        DRAGON_WING = item;

        ShapedRecipe r = new ShapedRecipe(new NamespacedKey(Main.getInstance(), "dragon_wing"), item);
        r.shape("ENE", "ETE", "ENE"); // E=Elytra, N=Netherite, T=Totem
        r.setIngredient('E', Material.ELYTRA);
        r.setIngredient('N', Material.NETHERITE_INGOT);
        r.setIngredient('T', Material.TOTEM_OF_UNDYING);
        Bukkit.addRecipe(r);
    }

    private static void createSpear() {
        ItemStack item = new ItemStack(Material.TRIDENT);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GOLD + "Spear");
        meta.setCustomModelData(1003);
        item.setItemMeta(meta);
        SPEAR = item;
        // Recipe logic similar to above...
    }
}
