package org.hinoob.chess.command;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.hinoob.chess.ChessPlugin;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.annotation.CommandPermission;

public class SetSpawnCommand {

    @Command("setspawn")
    @CommandPermission("chess.setspawn")
    public void setSpawn(Player player) {
        FileConfiguration f = ChessPlugin.getInstance().getConfig();
        f.set("spawn.x", player.getLocation().getX());
        f.set("spawn.y", player.getLocation().getY());
        f.set("spawn.z", player.getLocation().getZ());
        f.set("spawn.world", player.getLocation().getWorld().getName());
        f.set("spawn.yaw", player.getLocation().getYaw());
        f.set("spawn.pitch", player.getLocation().getPitch());
        ChessPlugin.getInstance().saveConfig();
        ChessPlugin.getInstance().getGameManager().setSpawn(player.getLocation());
        player.sendMessage(ChatColor.GREEN + "Spawn set!");
    }
}
