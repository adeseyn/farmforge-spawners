package com.farmforge.spawners.plugin.adapters;

import com.farmforge.spawners.core.CollectResult;
import com.farmforge.spawners.plugin.SpawnerItemUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class CollectResultAdapter {

    public static ItemStack toItemStack(CollectResult result, SpawnerItemUtil itemUtil) {
        Material material = Material.valueOf(result.dropMaterial());

        return itemUtil.createCollectedItem(
                material,
                result.dropName(),
                result.unitValue(),
                result.dropAmount()
        );
    }
}