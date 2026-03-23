package com.farmforge.spawners.core.api;

import com.farmforge.spawners.core.api.tier.SpawnerTier;

public record SpawnerItemData(int id, String typeId, SpawnerTier tier) {}