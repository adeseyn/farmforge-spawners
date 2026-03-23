package com.farmforge.spawners.plugin.listeners;

import com.farmforge.spawners.core.api.Spawner;
import com.farmforge.spawners.core.api.SpawnerFacade;
import com.farmforge.spawners.core.api.SpawnerPosition;
import com.farmforge.spawners.plugin.adapters.BukkitPositionAdapter;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SpawnerBreakListener implements Listener {

    private final SpawnerFacade facade;

    public SpawnerBreakListener(SpawnerFacade facade) {
        this.facade = facade;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        SpawnerPosition pos = BukkitPositionAdapter.from(e.getBlock().getLocation());

        try {
            Spawner spawner = facade.getSpawnerByPosition(pos);
            if (spawner == null) return;

            e.setCancelled(true);
            e.getPlayer().sendMessage("Use sneak + right click to pick up this spawner.");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }
}