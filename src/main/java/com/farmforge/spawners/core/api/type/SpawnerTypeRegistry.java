package com.farmforge.spawners.core.api.type;

import java.util.HashMap;
import java.util.Map;

public class SpawnerTypeRegistry {
    private final Map<String, SpawnerType> types;

    public SpawnerTypeRegistry(){
        this.types = new HashMap<>();
    }

    public void register(SpawnerType type){
        types.put(type.getId(), type);
    }

    public SpawnerType get(String id){
        return types.get(id);
    }
}
