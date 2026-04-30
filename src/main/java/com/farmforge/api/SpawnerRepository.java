package com.farmforge.api;

import java.util.List;
import java.util.UUID;

public interface SpawnerRepository {
    int nextId();
    Spawner findById(int id);
    List<Spawner> findByOwner(UUID ownerId);
    void save(Spawner spawner);
    void delete(int id);
}
