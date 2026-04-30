package com.farmforge.api.types;

import com.farmforge.api.type.SpawnerType;

public class WoodSpawnerType implements SpawnerType {
    @Override
    public String getId() {
        return "wood_spawner";
    }

    @Override
    public String getSpawnerName() {
        return "Wood Spawner";
    }

    @Override
    public long getCost() {
        return 50;
    }

    @Override
    public long getUpgradeCost() {
        return 100;
    }

    @Override
    public String getBlockType() {
        return "OAK_LOG";
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public String getDropName() {
        return "Wood Pellet";
    }

    @Override
    public String getDropType() {
        return "OAK_BUTTON";
    }

    @Override
    public long getBaseDropValue() {
        return 5;
    }
}
