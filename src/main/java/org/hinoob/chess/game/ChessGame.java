package org.hinoob.chess.game;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
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
import org.hinoob.chess.engine.pieces.Horse;
import org.hinoob.chess.engine.pieces.King;
import org.hinoob.chess.engine.pieces.Pawn;
import org.hinoob.chess.playerdata.PlayerData;
import org.hinoob.chess.playerdata.PlayerDataManager;
import org.hinoob.chess.util.LocationUtil;

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

    public void handleMove(ChessPiece piece, Move move, ChessPlayer player, ChessCallback callback) {
        boolean invalidMove = false;
        Chessboard tempBoard = new Chessboard();
        tempBoard.loadPiecesFrom(board);

        tempBoard.movePiece(piece, move.getNewX(), move.getNewY());
        King whiteKing = (King) tempBoard.getPieces().stream().filter(p -> p.isWhite() && p instanceof King).findAny().orElse(null);
        King blackKing = (King) tempBoard.getPieces().stream().filter(p -> !p.isWhite() && p instanceof King).findAny().orElse(null);

        if (player.isWhite()) {
            for (ChessPiece otherPiece : tempBoard.getPieces()) {
                if (otherPiece.isWhite()) continue;

                List<Move> captures = new ArrayList<>();
                otherPiece.getCaptures(captures);

                if (captures.stream().anyMatch(s -> s.getNewX() == whiteKing.getX() && s.getNewY() == whiteKing.getY())) {
                    invalidMove = true;
                    break;
                }
            }
        } else {
            for (ChessPiece otherPiece : tempBoard.getPieces()) {
                if (!otherPiece.isWhite()) continue;

                List<Move> captures = new ArrayList<>();
                otherPiece.getCaptures(captures);

                if (captures.stream().anyMatch(s -> s.getNewX() == blackKing.getX() && s.getNewY() == blackKing.getY())) {
                    invalidMove = true;
                    break;
                }
            }
        }
        if (invalidMove) {
            if (callback != null) {
                callback.kingInCheck();
            }
            return;
        }

        board.movePiece(piece, move.getNewX(), move.getNewY());
        ++totalMoves;
        refreshEntitiesOnBoard(false);
        board.flip();

        // bot support
        ChessPlayer other = (player.equals(player1) ? player2 : player1);
        if(!other.isHuman()) {
            Move next = other.getNextMove(board);
            handleMove(next.getBoard().getPiece(next.getOldX(), next.getOldY()), next, other, null);
        }
    }

    public interface ChessCallback {
        void success();
        void kingInCheck();
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

    private void refreshEntitiesOnBoard(boolean force) {
        List<ChessPiece> currentPieces = new ArrayList<>(board.getPieces());

        for(ChessPiece piece : currentPieces) {
            Location x = arena.getOnePos().add(piece.getX() + .5, 1, piece.getY() + .5);


            if(entities.containsKey(piece)) {
                entities.get(piece).teleport(x);
            } else if(force){
                if(piece instanceof Pawn) {
                    Villager villager = (Villager) x.getWorld().spawnEntity(x, EntityType.VILLAGER);
                    villager.setAI(false);
                    villager.setSilent(true);
                    villager.setCustomName(piece.isWhite() ? "(W) Pawn" : "(B) Pawn");
                    villager.setCustomNameVisible(true);
                    entities.put(piece, villager);
                } else if(piece instanceof King) {
                    Villager villager = (Villager) x.getWorld().spawnEntity(x, EntityType.VILLAGER);
                    villager.setAI(false);
                    villager.setSilent(true);
                    villager.setCustomName(piece.isWhite() ? "(W) King" : "(B) King");
                    villager.setCustomNameVisible(true);
                    entities.put(piece, villager);
                } else if(piece instanceof Horse) {
                    org.bukkit.entity.Horse horse = (org.bukkit.entity.Horse) x.getWorld().spawnEntity(x, EntityType.HORSE);
                    horse.setCustomName(piece.isWhite() ? "(W) Horse" : "(B) Horse");
                    horse.setCustomNameVisible(true);
                    horse.setAI(false);
                    horse.setSilent(true);
                    entities.put(piece, horse);
                }
            }
        }

        // cleanup
        Iterator<Map.Entry<ChessPiece, Entity>> iterator = entities.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<ChessPiece, Entity> entry = iterator.next();
            if (!currentPieces.contains(entry.getKey())) {
                entry.getValue().remove();
                iterator.remove(); //
            }
        }

        // make blacks look at whites for the best
        for(ChessPiece piece : currentPieces) {
            if(piece.isWhite()) continue;

            Location loc = entities.get(piece).getLocation();
            loc.setYaw(loc.getYaw() + 180);
            entities.get(piece).teleport(loc);
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

        Location one = new Location(world, arena.getOnePos().getBlockX(), world.getHighestBlockYAt(arena.getOnePos().getBlockX(), arena.getOnePos().getBlockZ()) + 1, arena.getOnePos().getBlockZ());
        Location two = new Location(world, arena.getTwoPos().getBlockX(), world.getHighestBlockYAt(arena.getTwoPos().getBlockX(), arena.getTwoPos().getBlockZ()) + 1, arena.getTwoPos().getBlockZ());

        LocationUtil.ensureSafeTeleport(one);
        LocationUtil.ensureSafeTeleport(two);

        player1.setFlying(true);
        player2.setFlying(true);
        if(player1.isWhite()) {
            player1.sendMessage("You are white!");
            player2.sendMessage("You are black!");

            player1.teleport(one);
            player2.teleport(two);
        } else {
            player2.sendMessage("You are white!");
            player1.sendMessage("You are black!");

            player1.teleport(two);
            player2.teleport(one);
        }

        refreshEntitiesOnBoard(true);
    }


}
