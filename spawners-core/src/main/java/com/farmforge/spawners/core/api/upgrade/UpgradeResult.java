package com.farmforge.spawners.core.api.upgrade;

public record UpgradeResult(
        String spawnerName,
        UpgradeTarget target,
        int oldLevel,
        int newLevel
) {}