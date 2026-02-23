package me.lightsmp.listeners;

import me.lightsmp.Main;
import org.bukkit.*;
import org.bukkit.boss.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftingSystem implements Listener {

    @EventHandler
    public void onCraft(CraftItemEvent e) {
        ItemStack res = e.getRecipe().getResult();
        if (!res.hasItemMeta() || !res.getItemMeta().hasCustomModelData()) return;

        e.setCancelled(true);
        Player p = (Player) e.getWhoClicked();
        e.getInventory().clear();
        
        Location loc = p.getLocation();
        String name = res.getItemMeta().getDisplayName();
        
        // Global BossBar
        BossBar bar = Bukkit.createBossBar(ChatColor.YELLOW + "Crafting " + name, BarColor.RED, BarStyle.SOLID);
        Bukkit.getOnlinePlayers().forEach(bar::addPlayer);

        // Floating Item Animation
        ArmorStand as = loc.getWorld().spawn(loc.add(0.5, 1, 0.5), ArmorStand.class, s -> {
            s.setVisible(false); s.setGravity(false); s.getEquipment().setHelmet(res);
        });

        new BukkitRunnable() {
            int ticks = 12000; // 10 Minutes = 12000 Ticks
            @Override
            public void run() {
                if (ticks <= 0) {
                    loc.getWorld().dropItemNaturally(loc, res);
                    loc.getWorld().strikeLightningEffect(loc);
                    bar.removeAll(); as.remove(); cancel(); return;
                }
                
                // Animation & BossBar Update
                as.teleport(as.getLocation().add(0, 0.0008, 0).setDirection(as.getLocation().getDirection().rotateAroundY(0.1)));
                if (ticks % 20 == 0) {
                    int seconds = ticks / 20;
                    bar.setTitle(ChatColor.GOLD + name + ChatColor.WHITE + " at " + ChatColor.RED + loc.getBlockX() + " " + loc.getBlockZ() + ChatColor.GRAY + " (" + seconds + "s)");
                    bar.setProgress(ticks / 12000.0);
                }
                ticks--;
            }
        }.runTaskTimer(Main.getInstance(), 0, 1L);
    }
}
