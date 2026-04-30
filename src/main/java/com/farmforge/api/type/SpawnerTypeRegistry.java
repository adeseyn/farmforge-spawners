package com.farmforge.api.type;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpawnerTypeRegistry {
    private final Map<String, SpawnerType> types;

    public SpawnerTypeRegistry(){
        this.types = new HashMap<>();
    }

    public void register(SpawnerType type){
        types.put(type.getId(), type);
    }

    public List<SpawnerType> getSpawnerTypes(){
        return types.values().stream().sorted(Comparator.comparingInt(SpawnerType::getPriority)).toList();
    }

    public SpawnerType get(String id){
        return types.get(id);
    }
}
