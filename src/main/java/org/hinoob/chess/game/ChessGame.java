package org.hinoob.chess.game;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.hinoob.chess.ChessPlugin;
import org.hinoob.chess.arena.ActiveArena;
import org.hinoob.chess.arena.Arena;
import org.hinoob.chess.engine.ChessPiece;
import org.hinoob.chess.engine.Chessboard;
import org.hinoob.chess.engine.Move;
import org.hinoob.chess.engine.pieces.Pawn;
import org.hinoob.chess.playerdata.PlayerData;
import org.hinoob.chess.playerdata.PlayerDataManager;

import java.util.*;

public class ChessGame {

    private ChessPlayer player1;
    private ChessPlayer player2;
    private ActiveArena arena;

    private Chessboard board = new Chessboard();
    private Map<ChessPiece, Entity> entities = new HashMap<>();
    private int totalMoves = 0;

    public ChessGame(Arena arena, ChessPlayer player1, ChessPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.arena = new ActiveArena(arena);

        // probably a better way to handle this
        if(player1.isHuman()) {
            PlayerData plr1 = ChessPlugin.getInstance().getPlayerDataManager().getPlayerData(player1.getUUID());
            plr1.player = player1;
            plr1.activeGame = this;
        }
        if(player2.isHuman()) {
            PlayerData plr2 = ChessPlugin.getInstance().getPlayerDataManager().getPlayerData(player2.getUUID());
            plr2.player = player1;
            plr2.activeGame = this;
        }
        this.arena.createWorld();
        this.board.loadPieces();
    }

    public ChessPlayer getWhite() {
        return player1.isWhite() ? player1 : player2;
    }

    public ChessPlayer getBlack() {
        return player1.isWhite() ? player2 : player1;
    }

    public void handleMove(ChessPiece piece, Move move, ChessPlayer player) {
        if(move.isCapture()) {
            ChessPiece targetPiece = board.getPiece(move.getNewX(), move.getNewY());
            board.removePiece(targetPiece);
            entities.get(targetPiece).remove();
            entities.remove(targetPiece);
        }
        piece.move(move.getNewX(), move.getNewY());
        ++totalMoves;
        refreshEntitiesOnBoard();
        board.flip();
    }

    private void assignTeams() {
        if(new Random().nextBoolean()) {
            player1.setWhite(true);
        } else {
            player2.setWhite(true);
        }
    }

    private void broadcast(String message) {
        player1.sendMessage(ChatColor.RED + "[CHESS] " + ChatColor.GRAY + message);
        player2.sendMessage(ChatColor.RED + "[CHESS] " + ChatColor.GRAY + message);
    }

    public Chessboard getBoard() {
        return board;
    }

    public ActiveArena getArena() {
        return arena;
    }

    private void refreshEntitiesOnBoard() {
        for(ChessPiece piece : board.getPieces()) {
            Location x = arena.getOnePos().add(piece.getX() + .5, 1, piece.getY() + .5);

            if(entities.containsKey(piece)) {
                entities.get(piece).teleport(x);
            } else if(totalMoves == 0){
                if(piece instanceof Pawn) {
                    Villager villager = (Villager) x.getWorld().spawnEntity(x, EntityType.VILLAGER);
                    villager.setAI(false);
                    villager.setSilent(true);
                    villager.setCustomName(piece.isWhite() ? "(W) Pawn" : "(B) Pawn");
                    villager.setCustomNameVisible(true);
                    entities.put(piece, villager);
                }
            }
        }
    }

    public ChessPiece entityToPiece(Entity entity) {
        for(ChessPiece piece : board.getPieces()) {
            if(entities.get(piece).equals(entity)) {
                return piece;
            }
        }

        return null;
    }

    public void start() {
        assignTeams();
        arena.refreshBoard();

        World world = arena.getOnePos().getWorld();

        if(player1.isWhite()) {
            player1.sendMessage("You are white!");
            player2.sendMessage("You are black!");

            player1.teleport(new Location(world, arena.getOnePos().getBlockX(), world.getHighestBlockYAt(arena.getOnePos().getBlockX(), arena.getOnePos().getBlockZ()) + 1, arena.getOnePos().getBlockZ()));
            player2.teleport(new Location(world, arena.getTwoPos().getBlockX(), world.getHighestBlockYAt(arena.getTwoPos().getBlockX(), arena.getTwoPos().getBlockZ()) + 1, arena.getTwoPos().getBlockZ()));
        } else {
            player2.sendMessage("You are white!");
            player1.sendMessage("You are black!");

            player2.teleport(new Location(world, arena.getOnePos().getBlockX(), world.getHighestBlockYAt(arena.getOnePos().getBlockX(), arena.getOnePos().getBlockZ()) + 1, arena.getOnePos().getBlockZ()));
            player1.teleport(new Location(world, arena.getTwoPos().getBlockX(), world.getHighestBlockYAt(arena.getTwoPos().getBlockX(), arena.getTwoPos().getBlockZ()) + 1, arena.getTwoPos().getBlockZ()));
        }

        refreshEntitiesOnBoard();
    }


}
