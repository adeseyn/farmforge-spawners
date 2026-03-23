package com.farmforge.spawners.plugin.listeners;

import com.farmforge.spawners.core.api.SpawnerPosition;
import com.farmforge.spawners.plugin.SpawnerItemUtil;
import com.farmforge.spawners.plugin.adapters.BukkitPositionAdapter;
import com.farmforge.spawners.core.api.SpawnerFacade;
import com.farmforge.spawners.core.api.tier.SpawnerTier;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class SpawnerPlaceListener implements Listener {

    private final SpawnerFacade facade;
    private final SpawnerItemUtil itemUtil;

    public SpawnerPlaceListener(SpawnerFacade facade, SpawnerItemUtil itemUtil) {
        this.facade = facade;
        this.itemUtil = itemUtil;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();

        // Not one of your items -> allow normal placement
        if (!itemUtil.hasSpawnerId(item) && !itemUtil.isNewSpawnerItem(item)) {
            return;
        }

        UUID playerId = e.getPlayer().getUniqueId();
        SpawnerPosition pos = BukkitPositionAdapter.from(e.getBlock().getLocation());

        try {
            if (itemUtil.hasSpawnerId(item)) {
                int id = itemUtil.getSpawnerId(item);
                facade.placeExistingSpawner(id, playerId, pos);
                System.out.println("Placing existing spawner");
                return;
            }

            String typeId = itemUtil.getType(item);
            SpawnerTier tier = itemUtil.getTier(item);
            facade.placeNewSpawner(typeId, tier, playerId, pos);
            System.out.println("Placing new spawner");


        } catch (Exception ex) {
            e.setCancelled(true);
            ex.printStackTrace();
            e.getPlayer().sendMessage(ex.getMessage());
        }
    }
}