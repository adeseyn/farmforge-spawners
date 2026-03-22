package com.farmforge.spawners.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class InMemorySpawnerRepository implements SpawnerRepository {

    private final Map<Integer, Spawner> storage = new HashMap<>();
    private int idCounter = 0;

    @Override
    public int nextId() {
        return ++idCounter;
    }

    @Override
    public Spawner findById(int id) {
        return storage.get(id);
    }

    @Override
    public List<Spawner> findByOwner(UUID ownerId) {
        return storage.values().stream()
                .filter(s -> s.getOwnerId().equals(ownerId.toString()))
                .toList();
    }

    @Override
    public void save(Spawner spawner) {
        storage.put(spawner.getId(), spawner);
    }

    @Override
    public void delete(int id) {
        storage.remove(id);
    }

}