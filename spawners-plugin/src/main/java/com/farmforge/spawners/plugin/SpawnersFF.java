package com.farmforge.spawners.plugin;

import com.farmforge.spawners.core.api.SpawnerFacade;
import com.farmforge.spawners.core.api.type.SpawnerTypeRegistry;
import com.farmforge.spawners.plugin.commands.GiveSpawnerCommand;
import com.farmforge.spawners.plugin.commands.SpawnerUpgradeCommand;
import com.farmforge.spawners.plugin.listeners.SpawnerInteractListener;
import com.farmforge.spawners.plugin.listeners.SpawnerPlaceListener;
import com.farmforge.spawners.plugin.listeners.SpawnerBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpawnersFF extends JavaPlugin {

    @Override
    public void onEnable() {
        SpawnerTypeRegistry typeRegistry = new SpawnerTypeRegistry();
        SpawnerTypeRegistrar.registerAll(typeRegistry);


        SimpleEconomy economy = new SimpleEconomy();

        SpawnerFacade facade = SpawnerFacade.createInMemory(
                typeRegistry,
                economy
        );

        SpawnerItemUtil itemUtil = new SpawnerItemUtil(this, typeRegistry);
        SpawnerItemFactory itemFactory = new SpawnerItemFactory(facade, itemUtil);

        // --- LISTENERS ---
        getServer().getPluginManager().registerEvents(new SpawnerPlaceListener(facade, itemUtil), this);
        getServer().getPluginManager().registerEvents(new SpawnerBreakListener(facade), this);
        getServer().getPluginManager().registerEvents(new SpawnerInteractListener(facade, itemFactory, itemUtil), this);

        // --- TEST COMMAND ---
        getCommand("givespawner").setExecutor(new GiveSpawnerCommand(facade, itemUtil));
        getCommand("spawnerupgrade").setExecutor(new SpawnerUpgradeCommand(facade));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
