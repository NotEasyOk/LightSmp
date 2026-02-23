package me.lightsmp;

import me.lightsmp.listeners.*;
import me.lightsmp.weapons.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        
        // Register Managers & Recipes
        WeaponManager.init();
        
        // Register Listeners
        getServer().getPluginManager().registerEvents(new CraftingSystem(), this);
        getServer().getPluginManager().registerEvents(new MaceAbility(), this);
        getServer().getPluginManager().registerEvents(new ElytraAbility(), this);
        getServer().getPluginManager().registerEvents(new SpearAbility(), this);
        getServer().getPluginManager().registerEvents(new VanillaLimiter(), this);

        getLogger().info("LightSmp Plugin Started - Ready for War!");
    }

    public static Main getInstance() { return instance; }
  }
