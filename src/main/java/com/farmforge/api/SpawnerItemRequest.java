package com.farmforge.api;

import com.farmforge.api.tier.SpawnerTier;

public record SpawnerItemRequest(String typeId, SpawnerTier tier) {}