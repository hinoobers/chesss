package org.hinoob.chess.command;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.hinoob.chess.ChessPlugin;
import org.hinoob.chess.playerdata.PlayerData;
import org.hinoob.chess.playerdata.PlayerDataManager;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Subcommand;

@Command("arena")
public class ArenaCommand {

    @Subcommand("create")
    public void create(Player player, String name) {
        PlayerData data = ChessPlugin.getInstance().getPlayerDataManager().getPlayerData(player.getUniqueId());
        if(data.pos1 == null || data.pos2 == null) {
            player.sendMessage(ChatColor.RED + "Position 1/2 not set!");
            return;
        }

        if(!data.pos1.getWorld().equals(data.pos2.getWorld())) {
            player.sendMessage(ChatColor.RED + "There can't be different worlds!");
            return;
        }

        // We prefer pos1 to be smaller
        if(data.pos1.getX() > data.pos2.getX()) {
            Location temp = data.pos1;
            data.pos1 = data.pos2;
            data.pos2 = temp;
        }

        if(data.pos1.getZ() > data.pos2.getZ()) {
            Location temp = data.pos1;
            data.pos1 = data.pos2;
            data.pos2 = temp;
        }

        ChessPlugin.getInstance().getArenaManager().createArena(name, data.pos1, data.pos2);
        player.sendMessage(ChatColor.GREEN + "Arena created!");
    }

    @Subcommand("pos1")
    public void pos1(Player player) {
        PlayerData data = ChessPlugin.getInstance().getPlayerDataManager().getPlayerData(player.getUniqueId());
        data.pos1 = player.getLocation();
        player.sendMessage(ChatColor.GREEN + "Position 1 set to " + data.pos1.toString());
    }

    @Subcommand("pos2")
    public void pos2(Player player) {
        PlayerData data = ChessPlugin.getInstance().getPlayerDataManager().getPlayerData(player.getUniqueId());
        data.pos2 = player.getLocation();
        player.sendMessage(ChatColor.GREEN + "Position 2 set to " + data.pos2.toString());
    }
}
