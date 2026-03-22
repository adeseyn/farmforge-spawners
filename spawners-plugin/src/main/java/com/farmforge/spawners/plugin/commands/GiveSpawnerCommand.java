package com.farmforge.spawners.plugin.commands;

import com.farmforge.spawners.core.tier.SpawnerTier;
import com.farmforge.spawners.core.type.SpawnerType;
import com.farmforge.spawners.core.type.SpawnerTypeRegistry;
import com.farmforge.spawners.core.type.SpawnerValidator;
import com.farmforge.spawners.plugin.SpawnerItemUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSpawnerCommand implements CommandExecutor {

    private final SpawnerItemUtil itemUtil;
    private final SpawnerValidator validator;

    public GiveSpawnerCommand(SpawnerItemUtil itemUtil, SpawnerValidator validator) {
        this.itemUtil = itemUtil;
        this.validator = validator;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (!(sender instanceof Player player)) return true;

        String typeId = args.length > 0 ? args[0] : "wood_spawner";
        if (!validator.isValidType(typeId)) {
            player.sendMessage("Invalid spawner type.");
            return true;
        }

        SpawnerTier tier = args.length > 1
                ? validator.parseTier(args[1])
                : SpawnerTier.LOW;

        if (tier == null) {
            player.sendMessage("Invalid tier.");
            return true;
        }

        player.getInventory().addItem(itemUtil.createNewItem(typeId, tier));
        player.sendMessage("Given " + typeId + " (" + tier + ")");
        return true;
    }
}