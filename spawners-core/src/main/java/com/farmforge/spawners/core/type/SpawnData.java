package com.farmforge.spawners.core.type;

public record SpawnData(
        int minDrop,
        int maxDrop,
        int minCapacity,
        int maxCapacity,
        int minSpeed,
        int maxSpeed
) {
    public SpawnData {
        if (minDrop <= 0 || maxDrop < minDrop)
            throw new IllegalArgumentException("Invalid drop bounds");

        if (minCapacity <= 0 || maxCapacity < minCapacity)
            throw new IllegalArgumentException("Invalid capacity bounds");

        if (minSpeed <= 0 || maxSpeed > minSpeed)
            throw new IllegalArgumentException("Invalid speed bounds");
    }
}
