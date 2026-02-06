package com.farmforge.spawners.core.type;

public record PresentationData(
        String displayName,
        String namePrefix,
        String itemMaterial
) {
    public PresentationData {
        if (displayName == null || displayName.isBlank())
            throw new IllegalArgumentException("Display name required");
    }
}
