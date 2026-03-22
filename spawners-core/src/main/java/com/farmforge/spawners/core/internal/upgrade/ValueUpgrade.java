package com.farmforge.spawners.core.internal.upgrade;

import com.farmforge.spawners.core.Spawner;
import com.farmforge.spawners.core.stats.SpawnerStats;

public class ValueUpgrade implements UpgradeMethod{
    @Override
    public int upgrade(Spawner spawner) throws UpgradeException {
        SpawnerStats stats = spawner.getStats();
        return stats.incrementValue();
    }
}