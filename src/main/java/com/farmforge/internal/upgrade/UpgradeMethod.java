package com.farmforge.internal.upgrade;

import com.farmforge.api.Spawner;
import com.farmforge.api.upgrade.UpgradeException;

public interface UpgradeMethod {
    int upgrade(Spawner spawner) throws UpgradeException;
}

