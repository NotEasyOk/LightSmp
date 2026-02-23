package me.lightsmp.manager;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.List;

public class WeaponManager {

    public static ItemStack getDoomMace() {
        ItemStack item = new ItemStack(Material.MACE);
        ItemMeta meta = item.getItemMeta();
        
        // Professional Name with Prefix
        meta.setDisplayName(ChatColor.DARK_RED + "" + ChatColor.BOLD + "【LEGENDARY】 " + ChatColor.RED + "Doom Mace");
        
        // Professional Lore
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "A weapon forged in the depths of Helheim.");
        lore.add("");
        lore.add(ChatColor.GOLD + "ABILITIES:");
        lore.add(ChatColor.YELLOW + "● Left-Click: " + ChatColor.WHITE + "Thor's Dash (Aim-Based)");
        lore.add(ChatColor.YELLOW + "● Right-Click: " + ChatColor.WHITE + "Wind Burst & Explosive Jump");
        lore.add(ChatColor.YELLOW + "● Shift + Right-Click: " + ChatColor.WHITE + "Mjolnir Throw (Loyalty)");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "ID: LIGHTSMP_DOOM_MACE");
        
        meta.setLore(lore);
        meta.setCustomModelData(1001); // Resource pack link
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getDragonWing() {
        ItemStack item = new ItemStack(Material.ELYTRA);
        ItemMeta meta = item.getItemMeta();
        
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "【MYTHIC】 " + ChatColor.LIGHT_PURPLE + "Dragon Wing");
        
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "Infused with the essence of the Ender Dragon.");
        lore.add("");
        lore.add(ChatColor.GOLD + "ABILITIES:");
        lore.add(ChatColor.YELLOW + "● Left-Click: " + ChatColor.WHITE + "Orbital Soul Shield (10s)");
        lore.add(ChatColor.YELLOW + "● Right-Click: " + ChatColor.WHITE + "Infinite Flight (No Rockets)");
        lore.add(ChatColor.YELLOW + "● Shift + Right-Click: " + ChatColor.WHITE + "Knockback Aura");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "ID: LIGHTSMP_DRAGON_WING");
        
        meta.setLore(lore);
        meta.setCustomModelData(1002);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getSpear() {
        ItemStack item = new ItemStack(Material.TRIDENT);
        ItemMeta meta = item.getItemMeta();
        
        meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "【RELIC】 " + ChatColor.YELLOW + "Spear of Destiny");
        
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "A divine spear that never misses its mark.");
        lore.add("");
        lore.add(ChatColor.GOLD + "ABILITIES:");
        lore.add(ChatColor.YELLOW + "● Left-Click: " + ChatColor.WHITE + "Flame Trail Dash (No Hunger)");
        lore.add(ChatColor.YELLOW + "● Right-Click: " + ChatColor.WHITE + "Homing Projectile (Auto-Track)");
        lore.add(ChatColor.YELLOW + "● Shift + Right-Click: " + ChatColor.WHITE + "Cryo-Freeze Target (7s)");
        lore.add("");
        lore.add(ChatColor.DARK_GRAY + "ID: LIGHTSMP_SPEAR");
        
        meta.setLore(lore);
        meta.setCustomModelData(1003);
        item.setItemMeta(meta);
        return item;
    }
  }
