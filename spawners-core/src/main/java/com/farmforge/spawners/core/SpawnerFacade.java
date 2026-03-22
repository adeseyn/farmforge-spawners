package com.farmforge.spawners.core;

import com.farmforge.spawners.core.internal.OwnershipService;
import com.farmforge.spawners.core.internal.cost.CostService;
import com.farmforge.spawners.core.internal.upgrade.UpgradeException;
import com.farmforge.spawners.core.internal.upgrade.UpgradeResult;
import com.farmforge.spawners.core.internal.upgrade.UpgradeService;
import com.farmforge.spawners.core.internal.upgrade.UpgradeTarget;
import com.farmforge.spawners.core.tier.SpawnerTier;
import com.farmforge.spawners.core.type.SpawnerType;

import java.util.List;
import java.util.UUID;

public class SpawnerFacade {
    private SpawnerRepository repository;
    private SpawnerService spawnerService;
    private UpgradeService upgradeService;
    private CostService costService;

    public SpawnerFacade(SpawnerService spawnerService, UpgradeService upgradeService,
                         CostService costService, SpawnerRepository repository){
        this.spawnerService = spawnerService;
        this.upgradeService = upgradeService;
        this.costService = costService;
        this.repository = repository;
    }

    public Spawner placeNewSpawner(String typeId, SpawnerTier tier, UUID playerId, SpawnerPosition position) throws Exception {
        Spawner spawner = spawnerService.createSpawner(typeId, tier, playerId.toString());
        assertOwnership(spawner, playerId);
        spawnerService.placeSpawner(spawner.getId(), position);

        return spawner;
    }

    public Spawner placeExistingSpawner(int spawnerId, UUID playerId, SpawnerPosition position) throws Exception {
        Spawner spawner = spawnerService.getSpawner(spawnerId);
        assertOwnership(spawner, playerId);
        spawnerService.placeSpawner(spawnerId, position);

        return spawner;
    }

    public Spawner pickupSpawner(SpawnerPosition position, UUID playerId) throws Exception {
        Spawner spawner = spawnerService.getSpawnerByPosition(position);
        if(spawner == null) return null;
        assertOwnership(spawner, playerId);
        spawnerService.pickupSpawner(spawner.getId());

        return spawner;
    }

    public CollectResult collectSpawner(int spawnerId, UUID playerId){
        Spawner spawner = spawnerService.getSpawner(spawnerId);
        assertOwnership(spawner, playerId);
        return spawnerService.collectSpawner(spawnerId, playerId);
    }

    public Spawner purchaseSpawner(SpawnerType type, UUID playerId){
        return spawnerService.purchaseSpawner(type, playerId);
    }

    public UpgradeResult upgradeSpawner(int spawnerId, UUID playerId, UpgradeTarget target) throws UpgradeException {
        Spawner spawner = spawnerService.getSpawner(spawnerId);
        assertOwnership(spawner, playerId);
        return upgradeService.upgrade(spawnerId, target);
    }

    public Spawner getSpawner(int spawnerId){
        return repository.findById(spawnerId);
    }

    public Spawner getSpawnerByPosition(SpawnerPosition position){
        return spawnerService.getSpawnerByPosition(position);
    }

    public List<Spawner> getPlayerSpawners(UUID playerId){
        return null;
    }

    private void assertOwnership(Spawner spawner, UUID playerId) {
        if (!spawner.getOwnerId().equals(playerId.toString())) {
            throw new IllegalStateException("You do not own this spawner.");
        }
    }
}
