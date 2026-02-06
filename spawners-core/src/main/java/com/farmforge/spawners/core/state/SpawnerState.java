package com.farmforge.spawners.core.state;

import com.farmforge.spawners.core.Spawner;

public interface SpawnerState {
    void onCollected(Spawner spawner);
    void onPlaced(Spawner spawner);
}
