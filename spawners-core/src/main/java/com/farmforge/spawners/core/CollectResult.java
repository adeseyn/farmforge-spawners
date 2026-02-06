package com.farmforge.spawners.core;

public record CollectResult(
        int spawnerId,
        String dropMaterial,
        int dropAmount
) {
}
