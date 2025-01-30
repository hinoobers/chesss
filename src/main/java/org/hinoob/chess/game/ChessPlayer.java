package org.hinoob.chess.game;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.hinoob.chess.ChessPlugin;
import org.hinoob.chess.engine.Move;

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

    public abstract Move getNextMove();
    public abstract void sendMessage(String message);
    public abstract void teleport(Location loc);

    public static ChessPlayer from(Player player) {
        ChessPlayer pl = new ChessPlayer(player.getUniqueId()) {
            @Override
            public Move getNextMove() {
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
            public boolean isHuman() {
                return true;
            }
        };
        return pl;
    }
}
