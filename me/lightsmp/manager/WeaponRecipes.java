package me.lightsmp.recipes;

import me.lightsmp.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponRecipes {
    public static void register() {
        // 1. DOOM MACE
        ItemStack mace = createItem(Material.MACE, ChatColor.DARK_RED + "Doom Mace", 1001);
        ShapedRecipe maceR = new ShapedRecipe(new NamespacedKey(Main.getInstance(), "doom_mace"), mace);
        maceR.shape("BNB", "BTB", "SSS"); // B=Netherite Block, N=Nether Star, T=Totem, S=Netherite Ingot
        maceR.setIngredient('B', Material.NETHERITE_BLOCK);
        maceR.setIngredient('N', Material.NETHER_STAR);
        maceR.setIngredient('T', Material.TOTEM_OF_UNDYING);
        maceR.setIngredient('S', Material.NETHERITE_INGOT);
        Bukkit.addRecipe(maceR);

        // 2. DRAGON WING
        ItemStack wing = createItem(Material.ELYTRA, ChatColor.DARK_PURPLE + "Dragon Wing", 1002);
        ShapedRecipe wingR = new ShapedRecipe(new NamespacedKey(Main.getInstance(), "dragon_wing"), wing);
        wingR.shape("DND", "ETE", "DND"); // D=Diamond Block, N=Netherite, E=Elytra, T=Totem
        wingR.setIngredient('D', Material.DIAMOND_BLOCK);
        wingR.setIngredient('N', Material.NETHERITE_INGOT);
        wingR.setIngredient('E', Material.ELYTRA);
        wingR.setIngredient('T', Material.TOTEM_OF_UNDYING);
        Bukkit.addRecipe(wingR);

        // 3. SPEAR
        ItemStack spear = createItem(Material.TRIDENT, ChatColor.GOLD + "Spear", 1003);
        ShapedRecipe spearR = new ShapedRecipe(new NamespacedKey(Main.getInstance(), "spear"), spear);
        spearR.shape(" N ", " D ", " D "); // Simpler but needs 9-slot fill (Adjusted for logic)
        spearR.shape("NNN", "SDS", "SDS"); // N=Netherite, S=Star, D=Diamond Block
        spearR.setIngredient('N', Material.NETHERITE_INGOT);
        spearR.setIngredient('S', Material.NETHER_STAR);
        spearR.setIngredient('D', Material.DIAMOND_BLOCK);
        Bukkit.addRecipe(spearR);
    }

    private static ItemStack createItem(Material mat, String name, int cmd) {
        ItemStack item = new ItemStack(mat);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setCustomModelData(cmd);
        item.setItemMeta(meta);
        return item;
    }
}
