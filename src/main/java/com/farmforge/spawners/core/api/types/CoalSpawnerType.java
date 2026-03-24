package com.farmforge.spawners.core.api.types;

import com.farmforge.spawners.core.api.type.SpawnerType;

public class CoalSpawnerType implements SpawnerType {
    @Override
    public String getId() {
        return "coal_spawner";
    }

    @Override
    public String getSpawnerName() {
        return "Coal Spawner";
    }

    @Override
    public long getCost() {
        return 10000;
    }

    @Override
    public long getUpgradeCost() {
        return 5000;
    }

    @Override
    public String getBlockType() {
        return "FURNACE";
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public String getDropName() {
        return "Chunk of Coal";
    }

    @Override
    public String getDropType() {
        return "COAL";
    }

    @Override
    public long getBaseDropValue() {
        return 20;
    }
}
