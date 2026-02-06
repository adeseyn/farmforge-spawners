package com.farmforge.spawners.core.internal.upgrade;

public interface UpgradeDefinition {
    String getId();
    int getMaxLevel();
    UpgradeTarget getTarget();
    int evaluate(int baseValue, int level);
}
