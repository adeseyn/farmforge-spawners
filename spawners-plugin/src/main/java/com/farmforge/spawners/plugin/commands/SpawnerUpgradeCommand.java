package com.farmforge.spawners.plugin.commands;

import com.farmforge.spawners.core.api.SpawnerFacade;
import com.farmforge.spawners.core.api.upgrade.UpgradeResult;
import com.farmforge.spawners.core.api.upgrade.UpgradeTarget;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnerUpgradeCommand implements CommandExecutor {

    private final SpawnerFacade facade;

    public SpawnerUpgradeCommand(SpawnerFacade facade) {
        this.facade = facade;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return true;
        }

        if (args.length != 2) {
            player.sendMessage("Usage: /spawnerupgrade <spawnerId> <speed|capacity|drop>");
            return true;
        }

        int spawnerId;
        try {
            spawnerId = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            player.sendMessage("Invalid spawner id.");
            return true;
        }

        UpgradeTarget target;
        try {
            target = parseTarget(args[1]);
        } catch (IllegalArgumentException e) {
            player.sendMessage("Invalid upgrade target.");
            return true;
        }

        try {
            UpgradeResult result = facade.upgradeSpawner(spawnerId, player.getUniqueId(), target);
            player.sendMessage("Upgraded " + result.target().getName() + " from " + result.oldLevel() + " to " + result.newLevel() + ".");
        } catch (Exception e) {
            player.sendMessage(e.getMessage());
        }

        return true;
    }

    private UpgradeTarget parseTarget(String input) {
        return switch (input.toLowerCase()) {
            case "speed" -> UpgradeTarget.SPEED;
            case "capacity", "cap" -> UpgradeTarget.CAPACITY;
            case "drop", "value", "drop_value" -> UpgradeTarget.DROP_VALUE;
            default -> throw new IllegalArgumentException("Unknown target");
        };
    }
}