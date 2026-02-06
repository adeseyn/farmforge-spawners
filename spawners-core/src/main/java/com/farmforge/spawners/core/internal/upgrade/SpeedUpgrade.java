package com.farmforge.spawners.core.internal.upgrade;

public class SpeedUpgrade implements UpgradeDefinition{
    @Override
    public String getId() {
        return "";
    }

    @Override
    public int getMaxLevel() {
        return 0;
    }

    @Override
    public UpgradeTarget getTarget() {
        return null;
    }

    @Override
    public int evaluate(int baseValue, int level) {
        return 0;
    }
}
