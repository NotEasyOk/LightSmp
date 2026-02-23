package me.lightsmp;

import me.lightsmp.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        
        // Listeners Registration
        getServer().getPluginManager().registerEvents(new CraftingSystem(), this);
        getServer().getPluginManager().registerEvents(new MaceAbility(), this);
        getServer().getPluginManager().registerEvents(new ElytraAbility(), this);
        getServer().getPluginManager().registerEvents(new SpearAbility(), this);
        getServer().getPluginManager().registerEvents(new VanillaControl(), this);

        getLogger().info("LightSmp Active: Weapons, Recipes, and Vanilla Disabler Loaded!");
    }

    public static Main getInstance() { return instance; }
}
