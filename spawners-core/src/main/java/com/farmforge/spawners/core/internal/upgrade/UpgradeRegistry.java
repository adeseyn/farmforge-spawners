package com.farmforge.spawners.core.internal.upgrade;

import java.util.Map;

public class UpgradeRegistry {
    private Map<String, UpgradeDefinition> upgrades;

    public UpgradeDefinition getUpgrade(String id){
        return upgrades.get(id);
    }
}
