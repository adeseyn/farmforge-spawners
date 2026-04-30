package com.farmforge.internal.upgrade;

import com.farmforge.api.Spawner;
import com.farmforge.api.upgrade.UpgradeException;
import com.farmforge.api.stats.SpawnerStats;

public class ValueUpgrade implements UpgradeMethod{
    @Override
    public int upgrade(Spawner spawner) throws UpgradeException {
        SpawnerStats stats = spawner.getStats();
        return stats.incrementValue();
    }
}