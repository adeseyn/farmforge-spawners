package com.farmforge.spawners.plugin;

import com.farmforge.spawners.core.*;
import com.farmforge.spawners.core.internal.OwnershipService;
import com.farmforge.spawners.core.internal.cost.CostService;
import com.farmforge.spawners.core.internal.upgrade.UpgradeService;
import com.farmforge.spawners.core.type.SpawnerTypeRegistry;
import com.farmforge.spawners.core.type.SpawnerValidator;
import com.farmforge.spawners.plugin.commands.GiveSpawnerCommand;
import com.farmforge.spawners.plugin.commands.SpawnerUpgradeCommand;
import com.farmforge.spawners.plugin.listeners.SpawnerInteractListener;
import com.farmforge.spawners.plugin.listeners.SpawnerPlaceListener;
import com.farmforge.spawners.plugin.listeners.SpawnerBreakListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SpawnersFF extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        SpawnerRepository repo = new InMemorySpawnerRepository();

        SpawnerTypeRegistry typeRegistry = new SpawnerTypeRegistry();
        SpawnerTypeRegistrar.registerAll(typeRegistry);

        SpawnerValidator validator = new SpawnerValidator(typeRegistry);
        SpawnerFactory factory = new SpawnerFactory(typeRegistry);
        SpawnerItemUtil itemUtil = new SpawnerItemUtil(this, typeRegistry);

        SpawnerItemFactory itemFactory = new SpawnerItemFactory(repo, itemUtil);

        SpawnerService spawnerService = new SpawnerService(repo, factory);
        UpgradeService upgradeService = new UpgradeService(repo);
        CostService costService = new CostService();

        SpawnerFacade facade = new SpawnerFacade(
                spawnerService,
                upgradeService,
                costService,
                repo
        );

        // --- LISTENERS ---
        getServer().getPluginManager().registerEvents(new SpawnerPlaceListener(facade, itemUtil), this);
        getServer().getPluginManager().registerEvents(new SpawnerBreakListener(facade), this);
        getServer().getPluginManager().registerEvents(new SpawnerInteractListener(facade, itemFactory, itemUtil), this);

        // --- TEST COMMAND ---
        getCommand("givespawner").setExecutor(new GiveSpawnerCommand(itemUtil, validator));
        getCommand("spawnerupgrade").setExecutor(new SpawnerUpgradeCommand(facade));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
