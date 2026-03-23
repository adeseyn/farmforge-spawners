package com.farmforge.spawners.plugin;

import com.farmforge.spawners.core.api.tier.SpawnerTier;
import com.farmforge.spawners.core.api.type.SpawnerType;
import com.farmforge.spawners.core.api.type.SpawnerTypeRegistry;
import com.farmforge.spawners.plugin.adapters.BukkitSpawnerTypeAdapter;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class SpawnerItemUtil {

    private final NamespacedKey idKey;
    private final NamespacedKey typeKey;
    private final NamespacedKey tierKey;
    private final NamespacedKey collectedValueKey;
    private final SpawnerTypeRegistry typeRegistry;

    public SpawnerItemUtil(Plugin plugin, SpawnerTypeRegistry typeRegistry) {
        this.idKey = new NamespacedKey(plugin, "spawner_id");
        this.typeKey = new NamespacedKey(plugin, "spawner_type");
        this.tierKey = new NamespacedKey(plugin, "spawner_tier");
        this.collectedValueKey = new NamespacedKey(plugin, "drop_value");
        this.typeRegistry = typeRegistry;
    }

    public ItemStack createNewItem(String typeId, SpawnerTier tier) {
        SpawnerType type = typeRegistry.get(typeId);
        if (type == null) {
            throw new IllegalArgumentException("Unknown spawner type: " + typeId);
        }

        ItemStack item = new ItemStack(BukkitSpawnerTypeAdapter.toBlockMaterial(type));
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(typeKey, PersistentDataType.STRING, type.getId());
        meta.getPersistentDataContainer().set(tierKey, PersistentDataType.STRING, tier.name());
        meta.setDisplayName(type.getSpawnerName() + " [" + formatTier(tier) + "]");

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack createExistingItem(int spawnerId, String typeId, SpawnerTier tier) {
        SpawnerType type = typeRegistry.get(typeId);
        if (type == null) {
            throw new IllegalArgumentException("Unknown spawner type: " + typeId);
        }

        ItemStack item = new ItemStack(BukkitSpawnerTypeAdapter.toBlockMaterial(type));
        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(idKey, PersistentDataType.INTEGER, spawnerId);
        meta.getPersistentDataContainer().set(typeKey, PersistentDataType.STRING, type.getId());
        meta.getPersistentDataContainer().set(tierKey, PersistentDataType.STRING, tier.name());
        meta.setDisplayName(type.getSpawnerName() + " [" + formatTier(tier) + "]");

        item.setItemMeta(meta);
        return item;
    }

    public ItemStack createCollectedItem(Material material, String dropName, int value, int amount) {
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(dropName);
        meta.setLore(List.of("Unit Value: $" + value));

        meta.getPersistentDataContainer().set(
                collectedValueKey,
                PersistentDataType.INTEGER,
                value
        );

        item.setItemMeta(meta);
        return item;
    }

    public boolean isNewSpawnerItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;

        return item.getItemMeta().getPersistentDataContainer()
                .has(typeKey, PersistentDataType.STRING)
                && item.getItemMeta().getPersistentDataContainer()
                .has(tierKey, PersistentDataType.STRING);
    }

    public boolean hasSpawnerId(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        return item.getItemMeta().getPersistentDataContainer()
                .has(idKey, PersistentDataType.INTEGER);
    }

    public int getSpawnerId(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer()
                .get(idKey, PersistentDataType.INTEGER);
    }

    public String getType(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer()
                .get(typeKey, PersistentDataType.STRING);
    }

    public SpawnerTier getTier(ItemStack item) {
        String tier = item.getItemMeta().getPersistentDataContainer()
                .get(tierKey, PersistentDataType.STRING);
        return SpawnerTier.valueOf(tier);
    }

    public boolean isCollectedItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;

        return item.getItemMeta().getPersistentDataContainer()
                .has(collectedValueKey, PersistentDataType.INTEGER);
    }

    public int getCollectedItemValue(ItemStack item) {
        return item.getItemMeta().getPersistentDataContainer()
                .get(collectedValueKey, PersistentDataType.INTEGER);
    }

    private String formatTier(SpawnerTier tier) {
        String lower = tier.name().toLowerCase();
        return Character.toUpperCase(lower.charAt(0)) + lower.substring(1);
    }
}