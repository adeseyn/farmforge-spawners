package com.farmforge.spawners.core.api.cost;

import java.util.UUID;

public interface EconomyAdapter {
    long getBalance(UUID playerId);
    boolean has(UUID playerId, long amount);
    boolean withdraw(UUID playerId, long amount);
    void deposit(UUID playerId, long amount);
}
