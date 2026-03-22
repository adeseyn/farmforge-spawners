package com.farmforge.spawners.plugin;

import com.farmforge.spawners.core.Spawner;
import com.farmforge.spawners.core.SpawnerRepository;
import org.bukkit.inventory.ItemStack;

public class SpawnerItemFactory {

    private final SpawnerRepository repo;
    private final SpawnerItemUtil itemUtil;

    public SpawnerItemFactory(SpawnerRepository repo, SpawnerItemUtil itemUtil) {
        this.repo = repo;
        this.itemUtil = itemUtil;
    }

    public ItemStack createExistingItem(int spawnerId) {
        Spawner spawner = repo.findById(spawnerId);
        if (spawner == null) {
            throw new IllegalArgumentException("Unknown spawner id: " + spawnerId);
        }

        return itemUtil.createExistingItem(
                spawner.getId(),
                spawner.getType().getId(),
                spawner.getTier()
        );
    }
}