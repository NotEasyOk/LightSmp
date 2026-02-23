package me.lightsmp.listeners;

import me.lightsmp.Main;
import org.bukkit.*;
import org.bukkit.boss.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class CraftingSystem implements Listener {

    @EventHandler
    public void onCraft(InventoryClickEvent e) {
        if (e.getSlotType() != InventoryType.SlotType.RESULT) return;
        ItemStack item = e.getCurrentItem();
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasCustomModelData()) return;

        e.setCancelled(true); // Default craft cancel
        Player p = (Player) e.getWhoClicked();
        e.getInventory().clear();
        
        start10MinEvent(p, item);
    }

    private void start10MinEvent(Player p, ItemStack item) {
        Location loc = p.getLocation().add(0, 1, 0);
        BossBar bar = Bukkit.createBossBar(ChatColor.RED + "Crafting: " + item.getItemMeta().getDisplayName(), BarColor.RED, BarStyle.SOLID);
        for (Player all : Bukkit.getOnlinePlayers()) {
            bar.addPlayer(all);
            all.sendMessage(ChatColor.YELLOW + "ALERT! " + p.getName() + " is crafting a Legendary Weapon at " + loc.getBlockX() + ", " + loc.getBlockZ());
        }

        ArmorStand as = loc.getWorld().spawn(loc, ArmorStand.class, s -> {
            s.setGravity(false); s.setVisible(false); s.getEquipment().setHelmet(item);
        });

        new BukkitRunnable() {
            int time = 600; // 10 minutes
            @Override
            public void run() {
                if (time <= 0) {
                    loc.getWorld().dropItemNaturally(loc, item);
                    loc.getWorld().strikeLightningEffect(loc);
                    bar.removeAll(); as.remove(); cancel(); return;
                }
                as.teleport(as.getLocation().add(0, 0.016, 0)); // Moves up 10m total
                as.setRotation(as.getLocation().getYaw() + 15, 0);
                bar.setProgress(time / 600.0);
                time--;
            }
        }.runTaskTimer(Main.getInstance(), 0, 20L);
    }
                                           }
