package com.farmforge.spawners.core;

public interface SpawnerState {
    void setSpawner(Spawner spawner);
    void onCollected();
    void onPlaced();
}
