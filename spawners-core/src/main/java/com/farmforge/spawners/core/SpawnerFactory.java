package com.farmforge.spawners.core;

import com.farmforge.spawners.core.state.SpawnerState;
import com.farmforge.spawners.core.tier.SpawnerTier;
import com.farmforge.spawners.core.type.SpawnerType;
import com.farmforge.spawners.core.type.SpawnerTypeRegistry;

public class SpawnerFactory {
    private final SpawnerTypeRegistry registry;

    public SpawnerFactory(SpawnerTypeRegistry registry){
        this.registry = registry;
    }

    public Spawner createSpawner(int id, String typeId, SpawnerTier tier, String ownerId) {
        SpawnerType type = registry.get(typeId);
        if (type == null) {
            throw new IllegalArgumentException("Unknown spawner type: " + typeId);
        }

        return new Spawner(id, ownerId, SpawnerState.INVENTORY, type, tier);
    }
}
