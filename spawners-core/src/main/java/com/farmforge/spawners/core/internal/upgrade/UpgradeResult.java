package com.farmforge.spawners.core.internal.upgrade;

public record UpgradeResult(
        String spawnerName,
        UpgradeTarget target,
        int oldLevel,
        int newLevel
) {}