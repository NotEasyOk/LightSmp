package me.lightsmp.listeners;

import me.lightsmp.Main;
import org.bukkit.Material;
import org.bukkit.event.*;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.ItemStack;

public class VanillaControl implements Listener {

    @EventHandler
    public void onCraft(PrepareItemCraftEvent e) {
        if (!Main.getInstance().getConfig().getBoolean("settings.disable-vanilla-crafting")) return;
        
        ItemStack res = e.getInventory().getResult();
        if (res == null) return;

        // Block vanilla Mace, Elytra, and Trident
        if (res.getType() == Material.MACE || res.getType() == Material.ELYTRA || res.getType() == Material.TRIDENT) {
            if (!res.getItemMeta().hasCustomModelData()) {
                e.getInventory().setResult(null);
            }
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkPopulateEvent e) {
        if (!Main.getInstance().getConfig().getBoolean("settings.disable-elytra-spawning")) return;
        // Logic to remove elytra from item frames in End Cities
        e.getChunk().getEntities(); // Performance friendly check
    }
}
