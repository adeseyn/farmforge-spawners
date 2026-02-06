package com.farmforge.spawners.core;

public interface SpawnerRepository {
    Spawner findById(int id);
    void save(Spawner spawner);
    void delete(int id);
}
