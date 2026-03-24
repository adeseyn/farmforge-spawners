package com.farmforge.spawners.core.api;

public record CollectResult(
        int spawnerId,
        String dropMaterial,
        String dropName,
        int dropAmount,
        int unitValue
) {
}
