package com.farmforge.spawners.plugin.adapters;

import com.farmforge.spawners.core.api.type.SpawnerType;
import org.bukkit.Material;

public class BukkitSpawnerTypeAdapter {
    public static Material toBlockMaterial(SpawnerType type) {
        return Material.valueOf(type.getBlockType());
    }

    public static Material toDropMaterial(SpawnerType type) {
        return Material.valueOf(type.getDropType());
    }
}
