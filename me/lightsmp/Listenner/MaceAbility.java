package me.lightsmp.listeners;

import me.lightsmp.Main;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class MaceAbility implements Listener {
    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        if (item == null || item.getItemMeta().getCustomModelData() != 1001) return;

        if (e.getAction() == Action.LEFT_CLICK_AIR) {
            // Dash towards where player is looking (Aim Based)
            p.setVelocity(p.getLocation().getDirection().multiply(2.2));
            p.getWorld().spawnParticle(Particle.FLASH, p.getLocation(), 5);
        } 
        else if (e.getAction().name().contains("RIGHT")) {
            if (p.isSneaking()) {
                // Loyalty Throw logic
                launchMace(p, item);
                p.getInventory().setItemInMainHand(null);
            } else {
                // Wind Burst + Explosion
                p.setVelocity(new Vector(0, 1.8, 0));
                p.getWorld().spawnParticle(Particle.WIND_BURST, p.getLocation(), 5);
                p.getWorld().createExplosion(p.getLocation(), 2.0f, false, false);
            }
        }
    }

    private void launchMace(Player p, ItemStack item) {
        ArmorStand as = p.getWorld().spawn(p.getEyeLocation(), ArmorStand.class, s -> {
            s.setVisible(false); s.getEquipment().setItemInMainHand(item); s.setSmall(true);
        });
        new BukkitRunnable() {
            int ticks = 0; boolean returning = false;
            public void run() {
                if (!returning) {
                    as.teleport(as.getLocation().add(p.getLocation().getDirection().multiply(1.5)));
                    as.getNearbyEntities(1.5,1.5,1.5).forEach(en -> {
                        if (en instanceof LivingEntity && en != p) ((LivingEntity) en).damage(12, p);
                    });
                    if (ticks++ > 25) returning = true;
                } else {
                    Vector v = p.getLocation().toVector().subtract(as.getLocation().toVector()).normalize();
                    as.teleport(as.getLocation().add(v.multiply(1.5)));
                    if (as.getLocation().distance(p.getLocation()) < 2) {
                        p.getInventory().addItem(item); as.remove(); cancel();
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1L);
    }
                            }
