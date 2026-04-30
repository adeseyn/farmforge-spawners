package com.farmforge.api.upgrade;

public record UpgradeResult(
        String spawnerName,
        UpgradeTarget target,
        int oldLevel,
        int newLevel
) {}