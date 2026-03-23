package com.farmforge.spawners.plugin;

import com.farmforge.spawners.core.api.cost.EconomyAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SimpleEconomy implements EconomyAdapter {
    private final Map<UUID, Long> balances = new HashMap<>();

    @Override
    public long getBalance(UUID playerId) {
        return balances.getOrDefault(playerId, 0L);
    }

    @Override
    public boolean has(UUID playerId, long amount) {
        return getBalance(playerId) >= amount;
    }

    @Override
    public void deposit(UUID playerId, long amount) {
        balances.put(playerId, getBalance(playerId) + amount);
    }

    @Override
    public boolean withdraw(UUID playerId, long amount) {
        if (!has(playerId, amount)) {
            return false;
        }

        balances.put(playerId, getBalance(playerId) - amount);
        return true;
    }
}
