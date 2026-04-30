package com.farmforge.internal.factory;

import com.farmforge.api.Spawner;
import com.farmforge.api.state.SpawnerState;
import com.farmforge.api.tier.SpawnerTier;
import com.farmforge.api.type.SpawnerType;
import com.farmforge.api.type.SpawnerTypeRegistry;

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
