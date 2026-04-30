package com.farmforge.internal;

import com.farmforge.api.CollectResult;
import com.farmforge.api.Spawner;
import com.farmforge.api.SpawnerPosition;
import com.farmforge.api.SpawnerRepository;
import com.farmforge.api.tier.SpawnerTier;
import com.farmforge.api.type.SpawnerType;
import com.farmforge.internal.factory.SpawnerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SpawnerService {

    private final SpawnerRepository repository;
    private final SpawnerFactory factory;
    private final Map<SpawnerPosition, Integer> positionToSpawnerId;

    public SpawnerService(SpawnerRepository repository, SpawnerFactory factory) {
        this.repository = repository;
        this.factory = factory;
        this.positionToSpawnerId = new HashMap<>();
    }

    public Spawner createSpawner(String typeId, SpawnerTier tier, String ownerId) {
        int id = repository.nextId();
        Spawner spawner = factory.createSpawner(id, typeId, tier, ownerId);
        repository.save(spawner);
        return spawner;
    }

    public Spawner getSpawner(int spawnerId) {
        return repository.findById(spawnerId);
    }

    public void deleteSpawner(int id) {
        Spawner spawner = repository.findById(id);
        if (spawner == null) {
            throw new IllegalArgumentException("Spawner not found: " + id);
        }

        if (spawner.getPosition() != null) {
            positionToSpawnerId.remove(spawner.getPosition());
        }

        repository.delete(id);
    }

    public void placeSpawner(int spawnerId, SpawnerPosition position) {
        Spawner spawner = repository.findById(spawnerId);
        if (spawner == null) {
            throw new IllegalArgumentException("Spawner not found: " + spawnerId);
        }

        if (positionToSpawnerId.containsKey(position)) {
            throw new IllegalStateException("A spawner is already placed at that position.");
        }

        if (spawner.getPosition() != null) {
            positionToSpawnerId.remove(spawner.getPosition());
        }

        spawner.place(position);
        repository.save(spawner);
        positionToSpawnerId.put(position, spawnerId);
    }

    public void pickupSpawner(int spawnerId) {
        Spawner spawner = repository.findById(spawnerId);
        if (spawner == null) {
            throw new IllegalArgumentException("Spawner not found: " + spawnerId);
        }

        SpawnerPosition position = spawner.getPosition();
        if (position == null) {
            throw new IllegalStateException("Spawner is not placed.");
        }

        spawner.pickup();
        repository.save(spawner);
        positionToSpawnerId.remove(position);
    }

    public Spawner getSpawnerByPosition(SpawnerPosition position) {
        Integer spawnerId = positionToSpawnerId.get(position);
        if (spawnerId == null) {
            return null;
        }

        return repository.findById(spawnerId);
    }

    public CollectResult collectSpawner(int spawnerId, UUID playerId) {
        Spawner spawner = repository.findById(spawnerId);

        if (spawner == null) {
            throw new IllegalArgumentException("Spawner not found: " + spawnerId);
        }
        SpawnerType type = spawner.getType();
        String dropType = type.getDropType();
        String dropName = type.getDropName();

        int amount = spawner.collect();
        repository.save(spawner);

        return new CollectResult(spawnerId, dropType, dropName, amount, spawner.getDropValue());
    }

    public Spawner purchaseSpawner(SpawnerType type, UUID playerId) {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}