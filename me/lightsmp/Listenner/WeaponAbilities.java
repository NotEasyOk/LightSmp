package me.lightsmp.listeners;

import me.lightsmp.Main;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class WeaponAbilities implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack item = e.getItem();
        if (item == null || !item.hasItemMeta() || !item.getItemMeta().hasCustomModelData()) return;

        int id = item.getItemMeta().getCustomModelData();

        // --- 1. DOOM MACE (1001) ---
        if (id == 1001) {
            e.setCancelled(true);
            // Left Click: Thor Dash
            if (e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK) {
                p.setVelocity(p.getLocation().getDirection().multiply(2.5));
                p.getWorld().spawnParticle(Particle.FLASH, p.getLocation(), 10);
                p.getWorld().playSound(p.getLocation(), Sound.ENTITY_BAT_TAKEOFF, 1, 1);
            } 
            // Right Click (Normal): High Jump & Explosion
            else if (e.getAction().name().contains("RIGHT") && !p.isSneaking()) {
                p.setVelocity(new Vector(0, 2.0, 0));
                p.getWorld().spawnParticle(Particle.WIND_BURST, p.getLocation(), 15);
                p.getWorld().createExplosion(p.getLocation(), 4.0f, false, false);
            }
            // Shift + Right Click: Mjolnir Throw (Loyalty)
            else if (e.getAction().name().contains("RIGHT") && p.isSneaking()) {
                throwMace(p, item);
                p.getInventory().setItemInMainHand(null);
            }
        }

        // --- 2. DRAGON WING (1002) ---
        if (id == 1002) {
            // Left Click: Orbital Shield
            if (e.getAction().name().contains("LEFT")) {
                startOrbitalShield(p);
            }
            // Right Click: Ultimate Fly (Auto Rocket)
            else if (e.getAction().name().contains("RIGHT") && !p.isSneaking()) {
                p.setVelocity(p.getLocation().getDirection().multiply(1.8));
                p.getWorld().spawnParticle(Particle.DRAGON_BREATH, p.getLocation(), 5);
            }
            // Shift + Right Click: Knockback Aura
            else if (e.getAction().name().contains("RIGHT") && p.isSneaking()) {
                p.getWorld().spawnParticle(Particle.EXPLOSION_EMITTER, p.getLocation(), 1);
                p.getNearbyEntities(6, 6, 6).forEach(en -> {
                    if (en instanceof LivingEntity && en != p) {
                        en.setVelocity(en.getLocation().toVector().subtract(p.getLocation().toVector()).normalize().multiply(2.5));
                    }
                });
            }
        }

        // --- 3. SPEAR (1003) ---
        if (id == 1003) {
            // Left Click: Flame Dash (No Hunger)
            if (e.getAction().name().contains("LEFT")) {
                p.setFoodLevel(20);
                p.setVelocity(p.getLocation().getDirection().multiply(2.0));
                p.getWorld().spawnParticle(Particle.FLAME, p.getLocation(), 20, 0.2, 0.2, 0.2, 0.1);
            }
            // Right Click: Homing Spear (Shoot)
            else if (e.getAction().name().contains("RIGHT") && !p.isSneaking()) {
                launchHomingSpear(p);
            }
            // Shift + Right Click: Cryo Freeze
            else if (e.getAction().name().contains("RIGHT") && p.isSneaking()) {
                Player target = getNearestTarget(p, 15);
                if (target != null) {
                    target.setFreezeTicks(140); // 7s Freeze
                    target.getWorld().spawnParticle(Particle.SNOWFLAKE, target.getLocation(), 50);
                }
            }
        }
    }

    // --- HELPER METHODS (DANGEROUS LOGIC) ---

    private void throwMace(Player p, ItemStack item) {
        ArmorStand as = p.getWorld().spawn(p.getEyeLocation(), ArmorStand.class, s -> {
            s.setVisible(false); s.setGravity(false); s.getEquipment().setItemInMainHand(item);
        });
        new BukkitRunnable() {
            int ticks = 0; boolean returning = false;
            public void run() {
                if (!returning) {
                    as.teleport(as.getLocation().add(p.getLocation().getDirection().multiply(1.5)));
                    as.getNearbyEntities(1.5,1.5,1.5).forEach(en -> {
                        if (en instanceof LivingEntity && en != p) ((LivingEntity)en).damage(18, p);
                    });
                    if (ticks++ > 30) returning = true;
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

    private void startOrbitalShield(Player p) {
        new BukkitRunnable() {
            int t = 0;
            public void run() {
                if (t++ > 200) cancel(); // 10s
                for (int i = 0; i < 3; i++) {
                    double angle = t * 0.3 + (i * Math.PI * 2 / 3);
                    Location l = p.getLocation().add(Math.cos(angle) * 2, 1, Math.sin(angle) * 2);
                    p.getWorld().spawnParticle(Particle.SOUL_FIRE_FLAME, l, 2, 0,0,0,0);
                    l.getNearbyEntities(1, 1, 1).forEach(en -> { if (en instanceof Projectile) en.remove(); });
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1L);
    }

    private void launchHomingSpear(Player p) {
        Player target = getNearestTarget(p, 30);
        if (target == null) return;
        new BukkitRunnable() {
            Location curr = p.getEyeLocation();
            public void run() {
                Vector v = target.getLocation().add(0,1,0).toVector().subtract(curr.toVector()).normalize();
                curr.add(v.multiply(1.5));
                p.getWorld().spawnParticle(Particle.DUST, curr, 5, new Particle.DustOptions(Color.RED, 1));
                if (curr.distance(target.getLocation()) < 2) {
                    target.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.BLINDNESS, 100, 1));
                    target.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.LEVITATION, 60, 1));
                    cancel();
                }
            }
        }.runTaskTimer(Main.getInstance(), 0, 1L);
    }

    private Player getNearestTarget(Player p, double range) {
        return p.getNearbyEntities(range, range, range).stream()
                .filter(en -> en instanceof Player && en != p)
                .map(en -> (Player) en).findFirst().orElse(null);
    }
        }
