package me.lightsmp.listeners;

import me.lightsmp.Main;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SpearAbility implements Listener {
    @EventHandler
    public void onUse(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        if (item == null || item.getItemMeta().getCustomModelData() != 1003) return;

        if (e.getAction().name().contains("LEFT")) {
            // Dash with Flame Trail
            p.setVelocity(p.getLocation().getDirection().multiply(2));
            p.setFoodLevel(20); // No Hunger
            p.getWorld().spawnParticle(Particle.FLAME, p.getLocation(), 10, 0.1, 0.1, 0.1, 0.05);
        } 
        else if (e.getAction().name().contains("RIGHT")) {
            Player target = getTarget(p);
            if (target != null) {
                if (p.isSneaking()) {
                    // Shift Right Click: Freeze
                    target.setFreezeTicks(200);
                    target.sendMessage(ChatColor.AQUA + "You have been frozen by a Spear!");
                } else {
                    // Homing Shoot
                    launchHoming(p, target);
                }
            }
        }
    }

    private void launchHoming(Player p, Player target) {
        new BukkitRunnable() {
            Location loc = p.getEyeLocation();
            int life = 0;
            public void run() {
                if (life++ > 60 || loc.distance(target.getLocation()) < 1.5) {
                    target.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.BLINDNESS, 100, 1));
                    cancel(); return;
                }
                Vector v = target.getLocation().add(0, 1, 0).toVector().subtract(loc.toVector()).normalize();
                loc.add(v.multiply(1.5));
                p.getWorld().spawnParticle(Particle.DUST, loc, 5, new Particle.DustOptions(Color.RED, 1));
            }
        }.runTaskTimer(Main.getInstance(), 0, 1L);
    }

    private Player getTarget(Player p) {
        return p.getNearbyEntities(30, 30, 30).stream()
                .filter(en -> en instanceof Player && en != p)
                .map(en -> (Player) en).findFirst().orElse(null);
    }
                                                           }
