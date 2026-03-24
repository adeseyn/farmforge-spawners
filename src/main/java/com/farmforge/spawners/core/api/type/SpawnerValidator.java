package com.farmforge.spawners.core.api.type;

import com.farmforge.spawners.core.api.tier.SpawnerTier;

public class SpawnerValidator {
    private final SpawnerTypeRegistry typeRegistry;

    public SpawnerValidator(SpawnerTypeRegistry typeRegistry) {
        this.typeRegistry = typeRegistry;
    }

    public boolean isValidType(String typeId) {
        if (typeId == null || typeId.isBlank()) return false;
        return typeRegistry.get(typeId) != null;
    }

    public SpawnerTier parseTier(String input) {
        if (input == null || input.isBlank()) return null;

        try {
            return SpawnerTier.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
