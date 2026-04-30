package com.farmforge.api;

import com.farmforge.api.state.SpawnerState;
import com.farmforge.api.tier.SpawnerTier;
import com.farmforge.api.type.SpawnerType;
import com.farmforge.api.stats.SpawnerStats;

public class Spawner {

    private final int id;
    private final String ownerId;

    private SpawnerState state;
    private SpawnerPosition location;

    private final SpawnerType type;
    private final SpawnerTier tier;
    private final SpawnerStats stats;


    // Production system
    private int stored;
    private long nextProductionTimeMillis;

    public Spawner(int id,
                   String ownerId,
                   SpawnerState state,
                   SpawnerType type,
                   SpawnerTier tier) {

        this.id = id;
        this.ownerId = ownerId;
        this.state = state;
        this.type = type;
        this.tier = tier;

        this.stats = new SpawnerStats();

        this.location = null;
        this.stored = 0;
        this.nextProductionTimeMillis = 0;
    }

    public void place(SpawnerPosition location) {
        if (state != SpawnerState.INVENTORY) {
            throw new IllegalStateException("Spawner already placed");
        }

        this.location = location;
        this.state = SpawnerState.PLACED;
        this.stored = 0;
        this.nextProductionTimeMillis = System.currentTimeMillis() + getCollectionIntervalMillis();
    }

    public void pickup() {
        if (state != SpawnerState.PLACED) {
            throw new IllegalStateException("Spawner is not placed");
        }

        updateProduction();

        this.location = null;
        this.state = SpawnerState.INVENTORY;
    }

    public boolean isPlaced() {
        return state == SpawnerState.PLACED;
    }

    public SpawnerPosition getLocation() {
        return location;
    }

    public SpawnerState getState() {
        return state;
    }

    public long getCollectionIntervalMillis() {
        return getSpeed() * 1000L;
    }

    public long getMillisUntilCollectable(long currentTimeMillis) {
        updateProduction(currentTimeMillis);

        if (stored > 0) {
            return 0;
        }

        return Math.max(0, nextProductionTimeMillis - currentTimeMillis);
    }

    public double getSecondsUntilCollectable(long currentTimeMillis) {
        double timeLeft = getMillisUntilCollectable(currentTimeMillis) / 1000.0;
        return Math.round(timeLeft * 10.0) / 10.0;
    }

    public boolean isCollectable(long currentTimeMillis) {
        updateProduction(currentTimeMillis);
        return stored > 0;
    }

    private void updateProduction() {
        updateProduction(System.currentTimeMillis());
    }

    private void updateProduction(long now) {
        if (!isPlaced()) return;

        long interval = getCollectionIntervalMillis();
        if (interval <= 0) return;

        while (stored < getCapacity() && now >= nextProductionTimeMillis) {
            stored++;
            nextProductionTimeMillis += interval;
        }
    }

    public int collect() {
        if (!isPlaced()) {
            throw new IllegalStateException("Spawner must be placed");
        }

        long now = System.currentTimeMillis();
        updateProduction(now);

        if (stored <= 0) {
            return 0;
        }

        int amount = stored;
        stored = 0;
        nextProductionTimeMillis = now + getCollectionIntervalMillis();

        return amount;
    }

    public int getStored() {
        updateProduction();
        return stored;
    }

    public boolean isAtCapacity() {
        updateProduction();
        return stored >= getCapacity();
    }

    public int getDropValue() {
        int level = stats.getValueLevel();
        double multiplier = tier.getMultiplier(level);
        return (int) Math.round(type.getBaseDropValue() * multiplier);
    }

    public int getCapacity() {
        int level = stats.getCapacityLevel();
        return tier.getCapacity(level);
    }

    public int getSpeed() {
        int level = stats.getSpeedLevel();
        return tier.getSpeed(level);
    }

    public SpawnerStats getStats() {
        return stats;
    }

    public SpawnerPosition getPosition() {
        return this.location;
    }

    public SpawnerType getType() {
        return type;
    }

    public SpawnerTier getTier() {
        return tier;
    }

    public int getId() {
        return id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public long getNextProductionTimeMillis() {
        return nextProductionTimeMillis;
    }

    public int getStoredRaw() {
        return stored;
    }

    public void loadProductionState(int stored, long nextProductionTimeMillis) {
        this.stored = stored;
        this.nextProductionTimeMillis = nextProductionTimeMillis;
    }
}