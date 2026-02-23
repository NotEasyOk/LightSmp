package me.lightsmp.listeners;

import me.lightsmp.Main;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ElytraAbility implements Listener {
    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        if (item == null || item.getItemMeta().getCustomModelData() != 1002) return;

        if (e.getAction().name().contains("LEFT")) {
            // Orbital Shield
            new BukkitRunnable() {
                int t = 0;
                public void run() {
                    if (t++ > 200) cancel();
                    double angle = t * 0.3;
                    for (int i = 0; i < 3; i++) {
                        double finalAngle = angle + (i * Math.PI * 2 / 3);
                        Location l = p.getLocation().add(Math.cos(finalAngle) * 2, 1, Math.sin(finalAngle) * 2);
                        p.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, l, 2, 0,0,0,0);
                        l.getNearbyEntities(1, 1, 1).forEach(en -> { if (en instanceof Projectile) en.remove(); });
                    }
                }
            }.runTaskTimer(Main.getInstance(), 0, 1L);
        } else if (e.getAction().name().contains("RIGHT")) {
            if (p.isSneaking()) {
                // Knockback Aura
                p.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, p.getLocation(), 1);
                p.getNearbyEntities(5, 5, 5).forEach(en -> en.setVelocity(en.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(2)));
            } else {
                // Rocketless Flight
                p.setVelocity(p.getLocation().getDirection().multiply(1.5));
            }
        }
    }
}
