package com.farmforge.spawners.core.tier;

/**
 * Represents the Tier of a Spawner.
 * Responsible for calculating a Spawner's drop value multiplier, resource generation speed, and capacity.
 */
public enum SpawnerTier {
    LOW(0.2, 5, 1),
    MID(0.5, 4, 3),
    HIGH(1.0, 3, 6);

    private final double multiIncrease;
    private final int baseSpeed;
    private final int capacityScalar;

    SpawnerTier(double multiIncrease, int speedIncrease, int capacityScalar){
        this.multiIncrease = multiIncrease;
        this.baseSpeed = speedIncrease;
        this.capacityScalar = capacityScalar;
    }

    /**
     * Retrieve the multiplier for the spawner drop value.
     * @return The amount to multiply by.
     */
    public double getMultiplier(int level) {
        return 1 + (level - 1) * multiIncrease;
    }

    /**
     * Retrieve the speed that a Spawner generates resources.
     * @return The Spawner's speed.
     */
    public int getSpeed(int level) {
        return baseSpeed - (level - 1);
    }

    /**
     * Retrieve the capacity size of a Spawner.
     * @return The Spawner's capacity.
     */
    public int getCapacity(int level) {
        return level * capacityScalar;
    }
}
