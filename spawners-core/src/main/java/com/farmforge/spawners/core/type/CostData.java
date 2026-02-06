package com.farmforge.spawners.core.type;

public record CostData(
        long basePurchaseCost,
        long baseUpgradeCost
) {
    public CostData {
        if (basePurchaseCost < 0 || baseUpgradeCost < 0)
            throw new IllegalArgumentException("Costs cannot be negative");
    }
}

