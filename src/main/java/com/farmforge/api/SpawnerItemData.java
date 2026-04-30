package com.farmforge.api;

import com.farmforge.api.tier.SpawnerTier;

public record SpawnerItemData(int id, String typeId, SpawnerTier tier) {}