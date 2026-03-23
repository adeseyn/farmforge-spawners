package com.farmforge.spawners.plugin.commands;

import com.farmforge.spawners.core.api.SpawnerFacade;
import com.farmforge.spawners.core.api.SpawnerItemRequest;
import com.farmforge.spawners.plugin.SpawnerItemUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSpawnerCommand implements CommandExecutor {

    private final SpawnerFacade facade;
    private final SpawnerItemUtil itemUtil;

    public GiveSpawnerCommand(SpawnerFacade facade, SpawnerItemUtil itemUtil) {
        this.facade = facade;
        this.itemUtil = itemUtil;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player player)) return true;

        String rawType = args.length > 0 ? args[0] : null;
        String rawTier = args.length > 1 ? args[1] : null;

        try {
            SpawnerItemRequest request = facade.resolveSpawnerItemRequest(rawType, rawTier);

            player.getInventory().addItem(
                    itemUtil.createNewItem(request.typeId(), request.tier())
            );

            player.sendMessage("Given " + request.typeId() + " (" + request.tier() + ")");
        } catch (IllegalArgumentException e) {
            player.sendMessage(e.getMessage());
        }

        return true;
    }
}