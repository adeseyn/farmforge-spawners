package com.farmforge.spawners.core.internal.upgrade;

import com.farmforge.spawners.core.api.Spawner;
import com.farmforge.spawners.core.api.upgrade.UpgradeException;
import com.farmforge.spawners.core.api.stats.SpawnerStats;

public class SpeedUpgrade implements UpgradeMethod{
    @Override
    public int upgrade(Spawner spawner) throws UpgradeException {
        SpawnerStats stats = spawner.getStats();
        return stats.incrementSpeed();
    }
}
