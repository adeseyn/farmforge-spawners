package com.farmforge.spawners.core.api;

import com.farmforge.spawners.core.api.tier.SpawnerTier;

public record SpawnerItemRequest(String typeId, SpawnerTier tier) {}