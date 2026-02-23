package me.lightsmp;

import me.lightsmp.listeners.*;
import me.lightsmp.manager.CooldownManager;
import me.lightsmp.recipes.WeaponRecipes;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        instance = this;
        this.cooldownManager = new CooldownManager();
        saveDefaultConfig();
        
        // Register Everything
        WeaponRecipes.register();
        getServer().getPluginManager().registerEvents(new CraftingSystem(), this);
        getServer().getPluginManager().registerEvents(new MaceAbility(), this);
        getServer().getPluginManager().registerEvents(new ElytraAbility(), this);
        getServer().getPluginManager().registerEvents(new SpearAbility(), this);
        getServer().getPluginManager().registerEvents(new VanillaLimiter(), this);

        getLogger().info("LightSmp v1.0 - All Systems Operational!");
    }

    public static Main getInstance() { return instance; }
    public CooldownManager getCooldownManager() { return cooldownManager; }
            }
