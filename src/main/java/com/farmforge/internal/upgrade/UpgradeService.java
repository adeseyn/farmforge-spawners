package com.farmforge.internal.upgrade;

import com.farmforge.api.Spawner;
import com.farmforge.api.SpawnerRepository;
import com.farmforge.api.upgrade.UpgradeException;
import com.farmforge.api.upgrade.UpgradeResult;
import com.farmforge.api.upgrade.UpgradeTarget;

public class UpgradeService {
    private final SpawnerRepository repository;

    public UpgradeService(SpawnerRepository repository){
        this.repository = repository;
    }

    public UpgradeResult upgrade(int spawnerId, UpgradeTarget target) throws UpgradeException {
        UpgradeMethod method = switch (target) {
            case DROP_VALUE -> new ValueUpgrade();
            case CAPACITY -> new CapacityUpgrade();
            case SPEED -> new SpeedUpgrade();
        };
        Spawner spawner = repository.findById(spawnerId);
        int level = method.upgrade(spawner);
        UpgradeResult result = new UpgradeResult(
                spawner.getType().getSpawnerName(),
                target,
                level-1,
                level
        );
        repository.save(spawner);
        return result;
    }
}
