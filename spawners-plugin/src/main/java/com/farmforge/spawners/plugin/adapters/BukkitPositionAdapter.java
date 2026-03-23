package com.farmforge.spawners.plugin.adapters;

import com.farmforge.spawners.core.api.SpawnerPosition;
import org.bukkit.Location;

public class BukkitPositionAdapter {

    public static SpawnerPosition from(Location loc) {
        return new SpawnerPosition(
                loc.getWorld().getName(),
                loc.getBlockX(),
                loc.getBlockY(),
                loc.getBlockZ()
        );
    }
}
