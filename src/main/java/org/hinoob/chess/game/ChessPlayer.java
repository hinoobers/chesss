package org.hinoob.chess.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.hinoob.chess.ChessPlugin;
import org.hinoob.chess.engine.Chessboard;
import org.hinoob.chess.engine.Move;

import java.util.List;
import java.util.UUID;

public abstract class ChessPlayer {

    private UUID uuid;
    private boolean isWhite;

    public ChessPlayer(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setWhite(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isWhite() {
        return isWhite;
    }

    public abstract boolean isHuman();

    public abstract Move getNextMove(Chessboard board);
    public abstract void sendMessage(String message);
    public abstract void teleport(Location loc);
    public abstract void setFlying(boolean flying);

    public static ChessPlayer from(Player player) {
        ChessPlayer pl = new ChessPlayer(player.getUniqueId()) {
            @Override
            public Move getNextMove(Chessboard board) {
                // Humans make their move themselves
                return null;
            }

            @Override
            public void sendMessage(String message) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }

            @Override
            public void teleport(Location loc) {
                player.teleport(loc);
            }

            @Override
            public void setFlying(boolean flying) {
                player.setAllowFlight(true);
                player.setFlying(flying);
            }

            @Override
            public boolean isHuman() {
                return true;
            }
        };
        return pl;
    }
}
