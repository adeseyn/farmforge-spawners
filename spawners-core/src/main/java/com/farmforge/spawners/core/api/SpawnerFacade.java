package com.farmforge.spawners.core.api;

import com.farmforge.spawners.core.api.cost.EconomyAdapter;
import com.farmforge.spawners.core.api.upgrade.UpgradeException;
import com.farmforge.spawners.core.api.upgrade.UpgradeResult;
import com.farmforge.spawners.core.internal.InMemorySpawnerRepository;
import com.farmforge.spawners.core.internal.SpawnerService;
import com.farmforge.spawners.core.internal.cost.CostService;
import com.farmforge.spawners.core.internal.factory.SpawnerFactory;
import com.farmforge.spawners.core.internal.upgrade.UpgradeService;
import com.farmforge.spawners.core.api.upgrade.UpgradeTarget;
import com.farmforge.spawners.core.api.tier.SpawnerTier;
import com.farmforge.spawners.core.api.type.SpawnerType;
import com.farmforge.spawners.core.api.type.SpawnerTypeRegistry;
import com.farmforge.spawners.core.api.type.SpawnerValidator;

import java.util.List;
import java.util.UUID;

public class SpawnerFacade {
    private SpawnerTypeRegistry registry;
    private SpawnerRepository repository;
    private SpawnerService spawnerService;
    private UpgradeService upgradeService;
    private CostService costService;
    private EconomyAdapter economy;
    private SpawnerValidator validator;
    private final SpawnerFactory spawnerFactory;

    public SpawnerFacade(
            SpawnerTypeRegistry registry,
            SpawnerRepository repository,
            EconomyAdapter economy
    ){
        this.registry = registry;
        this.repository = repository;
        this.economy = economy;

        this.validator = new SpawnerValidator(registry);
        this.costService = new CostService();
        this.upgradeService = new UpgradeService(repository);
        this.spawnerFactory = new SpawnerFactory(registry);
        this.spawnerService = new SpawnerService(repository, spawnerFactory);
    }

    public static SpawnerFacade createInMemory(
            SpawnerTypeRegistry registry,
            EconomyAdapter economy
    ) {
        return new SpawnerFacade(registry,
                new InMemorySpawnerRepository(),
                economy
        );
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

    public SpawnerItemRequest resolveSpawnerItemRequest(String rawTypeId, String rawTier) {
        String typeId = (rawTypeId == null || rawTypeId.isBlank())
                ? "wood_spawner"
                : rawTypeId.toLowerCase();

        if (!validator.isValidType(typeId)) {
            throw new IllegalArgumentException("Invalid spawner type.");
        }

        SpawnerTier tier = (rawTier == null || rawTier.isBlank())
                ? SpawnerTier.LOW
                : validator.parseTier(rawTier);

        if (tier == null) {
            throw new IllegalArgumentException("Invalid tier.");
        }

        return new SpawnerItemRequest(typeId, tier);
    }

    public void canOpenUpgradeMenu(Spawner spawner, UUID playerId){
        assertOwnership(spawner, playerId);
    }

    public UpgradeResult upgradeSpawner(int spawnerId, UUID playerId, UpgradeTarget target) throws UpgradeException {
        Spawner spawner = spawnerService.getSpawner(spawnerId);
        assertOwnership(spawner, playerId);

        long cost = costService.getUpgradeCost(spawner, target);
        if (!economy.has(playerId, cost)) {
            throw new IllegalStateException("You need $" + cost + " to upgrade " + target.getName() + ".");
        }

        boolean success = economy.withdraw(playerId, cost);
        if (!success) {
            throw new IllegalStateException("Not enough money.");
        }
        return upgradeService.upgrade(spawnerId, target);
    }

    public Spawner getSpawner(int spawnerId){
        Spawner spawner = repository.findById(spawnerId);
        if (spawner == null) {
            throw new IllegalArgumentException("Unknown spawner id: " + spawnerId);
        }
        return spawner;
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
