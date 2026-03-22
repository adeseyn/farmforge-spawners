package com.farmforge.spawners.plugin;

import com.farmforge.spawners.core.type.SpawnerTypeRegistry;
import com.farmforge.spawners.core.type.types.CoalSpawnerType;
import com.farmforge.spawners.core.type.types.WoodSpawnerType;

public class SpawnerTypeRegistrar {
    public static void registerAll(SpawnerTypeRegistry registry) {
        registry.register(new WoodSpawnerType());
        registry.register(new CoalSpawnerType());
    }
}
