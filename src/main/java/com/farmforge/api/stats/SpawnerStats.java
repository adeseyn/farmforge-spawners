package com.farmforge.api.stats;


import com.farmforge.api.upgrade.UpgradeException;
import com.farmforge.api.upgrade.UpgradeTarget;

public class SpawnerStats {
    private final int MAX_VALUE_LEVEL = 5;
    private final int MAX_SPEED_LEVEL = 3;
    private final int MAX_CAPACITY_LEVEL = 5;

    private int valueLevel = 1;
    private int capacityLevel = 1;
    private int speedLevel = 1;

    public int getUpgradeLevel(UpgradeTarget target){
        return switch (target) {
            case DROP_VALUE -> valueLevel;
            case CAPACITY -> capacityLevel;
            case SPEED -> speedLevel;
        };
    }

    public int getValueLevel() { return valueLevel; }
    public int getCapacityLevel() { return capacityLevel; }
    public int getSpeedLevel() { return speedLevel; }


    /**
     * Increments the value level for a Spawner. Used on drop value upgrade.
     * @return The resulting valueLevel, or throws if max level
     */
    public int incrementValue() throws UpgradeException {
        if(this.valueLevel >= MAX_VALUE_LEVEL){
            throw new UpgradeException("Value upgrade is already at max level.");
        }
        valueLevel++;
        return this.valueLevel;
    }

    /**
     * Increments the capacity level for a Spawner. Used on capacity upgrade.
     * @return The resulting capacity level, or throws if max level
     */
    public int incrementCapacity() throws UpgradeException {
        if(this.capacityLevel >= MAX_CAPACITY_LEVEL){
            throw new UpgradeException("Capacity upgrade is already at max level.");
        }
        capacityLevel++;
        return this.capacityLevel;
    }

    /**
     * Increments the speed level for a Spawner. Used on speed upgrade.
     * @return The resulting speed level, or throws if max level
     */
    public int incrementSpeed() throws UpgradeException {
        if(this.speedLevel >= MAX_SPEED_LEVEL){
            throw new UpgradeException("Speed upgrade is already at max level.");
        }
        speedLevel++;
        return this.speedLevel;
    }
}