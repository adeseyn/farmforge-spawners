package com.farmforge.spawners.core;

public record CollectResult(
        int spawnerId,
        String dropMaterial,
        String dropName,
        int dropAmount,
        int unitValue
) {
}
