package org.hinoob.chess.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;
import org.hinoob.chess.ChessPlugin;
import org.hinoob.chess.arena.Arena;
import org.hinoob.chess.engine.Move;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class GameManager {

    private List<ChessGame> games = new ArrayList<>();

    public ChessGame createDummyGame(Player player) {
        ChessPlayer dummyBot = new ChessPlayer(UUID.randomUUID()) {
            @Override
            public Move getNextMove() {
                return null;
            }

            @Override
            public void sendMessage(String message) {

            }

            @Override
            public void teleport(Location loc) {

            }

            @Override
            public boolean isHuman() {
                return false;
            }
        };
        Arena arena = ChessPlugin.getInstance().getArenaManager().getRandomArena();
        return new ChessGame(arena, ChessPlayer.from(player), dummyBot);
    }
}
