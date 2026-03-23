package com.farmforge.spawners.core.internal.cost;

import com.farmforge.spawners.core.api.Spawner;
import com.farmforge.spawners.core.api.upgrade.UpgradeTarget;

public class CostService {

    public long getUpgradeCost(Spawner spawner, UpgradeTarget target) {
        int currentLevel = switch (target) {
            case SPEED -> spawner.getStats().getSpeedLevel();
            case CAPACITY -> spawner.getStats().getCapacityLevel();
            case DROP_VALUE -> spawner.getStats().getValueLevel();
        };

        return spawner.getType().getUpgradeCost() * (currentLevel + 1);
    }
}