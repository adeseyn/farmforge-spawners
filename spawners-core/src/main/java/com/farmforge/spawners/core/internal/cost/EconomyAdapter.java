package com.farmforge.spawners.core.internal.cost;

import java.util.UUID;

public interface EconomyAdapter {
    boolean has(UUID playerId, long amount);
    void withdraw(UUID playerId, long amount);
}
