package com.farmforge.spawners.core;

import com.farmforge.spawners.core.internal.CooldownService;
import com.farmforge.spawners.core.internal.OwnershipService;
import com.farmforge.spawners.core.internal.cost.CostService;
import com.farmforge.spawners.core.internal.upgrade.UpgradeService;
import com.farmforge.spawners.core.internal.upgrade.UpgradeTarget;
import com.farmforge.spawners.core.type.SpawnerType;

import java.util.UUID;

public class SpawnerService {
    private UpgradeService upgradeService;
    private OwnershipService ownershipService;
    private CooldownService cooldownService;
    private CostService costService;

    public void createSpawner(String typeId, String ownerId){

    }

    public void placeSpawner(String spawnerId){

    }

    public CollectResult collectSpawner(String spawnerId){
        return null;
    }

    public Spawner purchaseSpawner(SpawnerType type, UUID playerId){
        return null;
    }

    public void upgradeSpawner(int spawnerId, UUID playerId, UpgradeTarget target){

    }

}
