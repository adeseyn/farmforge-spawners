package com.farmforge.spawners.plugin.listeners;

import com.farmforge.spawners.core.api.CollectResult;
import com.farmforge.spawners.core.api.Spawner;
import com.farmforge.spawners.core.api.SpawnerFacade;
import com.farmforge.spawners.core.api.SpawnerPosition;
import com.farmforge.spawners.plugin.SpawnerItemUtil;
import com.farmforge.spawners.plugin.adapters.BukkitPositionAdapter;
import com.farmforge.spawners.plugin.SpawnerItemFactory;
import com.farmforge.spawners.plugin.adapters.CollectResultAdapter;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class SpawnerInteractListener implements Listener {

    private final SpawnerFacade facade;
    private final SpawnerItemFactory itemFactory;
    private final SpawnerItemUtil itemUtil;

    public SpawnerInteractListener(SpawnerFacade facade, SpawnerItemFactory itemFactory, SpawnerItemUtil itemUtil) {
        this.facade = facade;
        this.itemFactory = itemFactory;
        this.itemUtil = itemUtil;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        if (e.getHand() != EquipmentSlot.HAND) return;

        Action action = e.getAction();
        if (action != Action.RIGHT_CLICK_BLOCK && action != Action.LEFT_CLICK_BLOCK) return;

        SpawnerPosition pos = BukkitPositionAdapter.from(e.getClickedBlock().getLocation());

        Spawner spawner;
        try {
            spawner = facade.getSpawnerByPosition(pos);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return;
        }

        if (spawner == null) return;

        e.setCancelled(true);

        Player player = e.getPlayer();

        UUID playerId = player.getUniqueId();

        try {
            // pickup = crouch + right click
            if (action == Action.RIGHT_CLICK_BLOCK && player.isSneaking()) {
                Spawner pickedUp = facade.pickupSpawner(pos, playerId);

                e.getClickedBlock().setType(Material.AIR);

                player.getInventory().addItem(
                        itemFactory.createExistingItem(pickedUp.getId())
                );



                player.sendMessage("Spawner picked up.");
                return;
            }

            // upgrade = regular right click
            if (action == Action.RIGHT_CLICK_BLOCK) {
                facade.canOpenUpgradeMenu(spawner, playerId);

                int spawnerId = spawner.getId();

                e.getPlayer().sendMessage(Component.text("Choose an upgrade:"));

                e.getPlayer().sendMessage(
                        Component.text("[Speed]")
                                .clickEvent(ClickEvent.runCommand("/spawnerupgrade " + spawnerId + " speed"))
                                .hoverEvent(HoverEvent.showText(Component.text("Upgrade speed")))
                );

                e.getPlayer().sendMessage(
                        Component.text("[Capacity]")
                                .clickEvent(ClickEvent.runCommand("/spawnerupgrade " + spawnerId + " capacity"))
                                .hoverEvent(HoverEvent.showText(Component.text("Upgrade capacity")))
                );

                e.getPlayer().sendMessage(
                        Component.text("[Drop Value]")
                                .clickEvent(ClickEvent.runCommand("/spawnerupgrade " + spawnerId + " drop"))
                                .hoverEvent(HoverEvent.showText(Component.text("Upgrade drop value")))
                );

                return;
            }

            // collect = regular left click
            if (action == Action.LEFT_CLICK_BLOCK) {
                CollectResult result = facade.collectSpawner(spawner.getId(), playerId);

                if (result.dropAmount() <= 0) {
                    Spawner updated = facade.getSpawnerByPosition(pos);
                    double timeLeft = updated.getSecondsUntilCollectable(System.currentTimeMillis());
                    player.sendMessage("Ready in " + timeLeft + "s");
                    return;
                }

                ItemStack dropItem = CollectResultAdapter.toItemStack(result, itemUtil);
                player.getInventory().addItem(dropItem);

                player.sendMessage("Collected " + result.dropAmount() + ".");
                return;
            }

        } catch (Exception ex) {
            player.sendMessage(ex.getMessage());

        }
    }
}