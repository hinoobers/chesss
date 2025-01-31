package org.hinoob.chess.game;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.units.qual.C;
import org.hinoob.chess.ChessPlugin;
import org.hinoob.chess.arena.Arena;
import org.hinoob.chess.engine.ChessPiece;
import org.hinoob.chess.engine.Chessboard;
import org.hinoob.chess.engine.Move;

import java.util.*;

public class GameManager {

    private List<ChessGame> games = new ArrayList<>();

    public ChessGame createDummyGame(Player player) {
        ChessPlayer dummyBot = new ChessPlayer(UUID.randomUUID()) {
            @Override
            public Move getNextMove(Chessboard board) {
                List<ChessPiece> whitePieces = board.getPieces().stream().filter(d -> d.isWhite() == this.isWhite()).toList();
                List<Move> moves = new ArrayList<>();
                Random rand = new Random();

                for(ChessPiece piece : whitePieces) {
                    piece.getPossibleMoves(moves);
                }

                return moves.get(rand.nextInt(moves.size()));
            }

            @Override
            public void sendMessage(String message) {

            }

            @Override
            public void teleport(Location loc) {

            }

            @Override
            public void setFlying(boolean flying) {

            }

            @Override
            public boolean isHuman() {
                return false;
            }
        };
        Arena arena = ChessPlugin.getInstance().getArenaManager().getRandomArena();
        return new ChessGame(arena, ChessPlayer.from(player), dummyBot);
    }
    public ChessGame createGame(Player player1, Player player2) {
        ChessPlayer pl1 = ChessPlayer.from(player1);
        ChessPlayer pl2 = ChessPlayer.from(player2);
        Arena arena = ChessPlugin.getInstance().getArenaManager().getRandomArena();
        return new ChessGame(arena, pl1, pl2);
    }

    public Collection<ChessGame> getGames() {
        return Collections.unmodifiableCollection(games);
    }
}
