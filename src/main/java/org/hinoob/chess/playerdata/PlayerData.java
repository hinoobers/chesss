package org.hinoob.chess.playerdata;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.hinoob.chess.ChessPlugin;
import org.hinoob.chess.engine.ChessPiece;
import org.hinoob.chess.game.ChessGame;
import org.hinoob.chess.game.ChessPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerData {

    private final UUID uuid;

    // arena creation data
    public Location pos1, pos2;

    public ChessGame activeGame;
    public ChessPiece selectedPiece;
    public ChessPlayer player;

    public List<UUID> invites = new ArrayList<>();

    public PlayerData(UUID uuid) {
        this.uuid = uuid;
    }


}
