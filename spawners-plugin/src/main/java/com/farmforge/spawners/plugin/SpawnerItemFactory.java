package com.farmforge.spawners.plugin;

import com.farmforge.spawners.core.api.Spawner;
import com.farmforge.spawners.core.api.SpawnerFacade;
import org.bukkit.inventory.ItemStack;

public class SpawnerItemFactory {

    private final SpawnerFacade facade;
    private final SpawnerItemUtil itemUtil;

    public SpawnerItemFactory(SpawnerFacade facade, SpawnerItemUtil itemUtil) {
        this.facade = facade;
        this.itemUtil = itemUtil;
    }

    public ItemStack createExistingItem(int spawnerId) {
        Spawner spawner = facade.getSpawner(spawnerId);

        return itemUtil.createExistingItem(
                spawner.getId(),
                spawner.getType().getId(),
                spawner.getTier()
        );
    }
}