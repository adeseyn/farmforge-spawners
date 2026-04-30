package com.farmforge.api.type;

public interface SpawnerType {
    String getId();
    String getSpawnerName();
    long getCost();
    long getUpgradeCost();
    String getBlockType();
    int getPriority();

    String getDropName();
    String getDropType();
    long getBaseDropValue();

}
