package com.farmforge.spawners.core.internal.upgrade;

import com.farmforge.spawners.core.Spawner;

public interface UpgradeMethod {
    int upgrade(Spawner spawner) throws UpgradeException;
}

