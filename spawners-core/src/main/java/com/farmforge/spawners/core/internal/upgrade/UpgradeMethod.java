package com.farmforge.spawners.core.internal.upgrade;

import com.farmforge.spawners.core.api.Spawner;
import com.farmforge.spawners.core.api.upgrade.UpgradeException;

public interface UpgradeMethod {
    int upgrade(Spawner spawner) throws UpgradeException;
}

