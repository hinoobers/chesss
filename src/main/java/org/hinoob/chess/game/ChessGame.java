package org.hinoob.chess.game;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.checkerframework.checker.units.qual.C;
import org.hinoob.chess.ChessPlugin;
import org.hinoob.chess.arena.ActiveArena;
import org.hinoob.chess.arena.Arena;
import org.hinoob.chess.engine.ChessPiece;
import org.hinoob.chess.engine.Chessboard;
import org.hinoob.chess.engine.Move;
import org.hinoob.chess.engine.pieces.*;
import org.hinoob.chess.engine.pieces.Horse;
import org.hinoob.chess.playerdata.PlayerData;
import org.hinoob.chess.playerdata.PlayerDataManager;
import org.hinoob.chess.util.LocationUtil;

import java.util.*;

public class ChessGame {

    private ChessPlayer player1;
    private ChessPlayer player2;
    private ActiveArena arena;

    private Chessboard board;
    private Map<ChessPiece, Entity> entities = new HashMap<>();
    private GameStatus status = GameStatus.ACTIVE;
    private int totalMoves = 0;

    public ChessGame(Arena arena, ChessPlayer player1, ChessPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.arena = new ActiveArena(arena);
        this.board = new Chessboard();

        // probably a better way to handle this
        if(player1.isHuman()) {
            PlayerData plr1 = ChessPlugin.getInstance().getPlayerDataManager().getPlayerData(player1.getUUID());
            plr1.player = player1;
            plr1.activeGame = this;
        }
        if(player2.isHuman()) {
            PlayerData plr2 = ChessPlugin.getInstance().getPlayerDataManager().getPlayerData(player2.getUUID());
            plr2.player = player2;
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
            // Remove it from here
            ChessPiece captured = board.getPiece(move.getNewX(), move.getNewY());
            entities.get(captured).remove();
            entities.remove(captured);
        }
        board.movePiece(piece, move.getNewX(), move.getNewY());
        checkWinCondition();
        ++totalMoves;
        refreshEntitiesOnBoard(false);
        board.flip();

        // bot support
        ChessPlayer other = (player.equals(player1) ? player2 : player1);
        if(!other.isHuman()) {
            Move next = other.getNextMove(board);
            handleMove(next.getBoard().getPiece(next.getOldX(), next.getOldY()), next, other);
        }
    }

    private boolean isKingSafeAfterMove(boolean white) {
        Chessboard tempBoard = new Chessboard();
        tempBoard.loadPiecesFrom(this.board);

        List<ChessPiece> pieces = new ArrayList<>(tempBoard.getPieces());

        boolean safe = false;

        for (ChessPiece piece : pieces) {
            if (piece.isWhite() == white) continue; // opponent

            List<Move> moves = new ArrayList<>();
            piece.getMoves(moves);

            for (Move move : moves) {
                tempBoard.movePiece(piece, move.getNewX(), move.getNewY());

                if(white && tempBoard.isWhiteKingInCheck()) {
                    safe = true;
                } else if(!white && !tempBoard.isBlackKingInCheck()) {
                    safe = true;
                }

                // Revert move
                tempBoard.movePiece(piece, move.getOldX(), move.getOldY());

                if (safe) break;
            }

            if (safe) break;
        }

        return safe;
    }

    private void checkWinCondition() {
        if (status == GameStatus.ENDED) {
            // Don't check winners twice
            return;
        }

        Chessboard tempBoard = new Chessboard();
        tempBoard.loadPiecesFrom(this.board);

        // Checkmate, find all possible moves that would get king out of check
        if (!isKingSafeAfterMove(true)) {
            status = GameStatus.ENDED;
            broadcast("White king is in checkmate!");
            cleanGame();
        } else {
            // potential stalemate ?

            if(!board.isWhiteKingInCheck()) {
                for(ChessPiece piece : board.getPieces()) {
                    if(!piece.isWhite()) continue;
                    List<Move> moves = new ArrayList<>();
                    piece.getPossibleMoves(moves);

                    if(moves.isEmpty()) {
                        // stalemate

                        status = GameStatus.ENDED;
                        broadcast("White king is in stalemate!");
                        cleanGame();
                        return;
                    }
                }
            }
        }


        if(!isKingSafeAfterMove(false)) {
            status = GameStatus.ENDED;
            broadcast("Black king is in checkmate!");
            cleanGame();
        } else {
            // potential stalemate ?

            if(!board.isBlackKingInCheck()) {
                for(ChessPiece piece : board.getPieces()) {
                    if(piece.isWhite()) continue;
                    List<Move> moves = new ArrayList<>();
                    piece.getPossibleMoves(moves);

                    if(moves.isEmpty()) {
                        // stalemate

                        status = GameStatus.ENDED;
                        broadcast("Black king is in stalemate!");
                        cleanGame();
                        return;
                    }
                }
            }
        }
    }


    private void assignTeams() {
        if(new Random().nextBoolean()) {
            player1.setWhite();
            player2.setBlack();
        } else {
            player1.setBlack();
            player2.setWhite();;
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
                // preserve rotation
                float yaw = entities.get(piece).getLocation().getYaw();
                float pitch = entities.get(piece).getLocation().getPitch();
                x.setYaw(yaw);
                x.setPitch(pitch);

                entities.get(piece).teleport(x);
            } else if(force){
                if(piece instanceof Pawn) {
                    Villager villager = (Villager) x.getWorld().spawnEntity(x, EntityType.VILLAGER);
                    villager.setAI(false);
                    villager.setSilent(true);
                    villager.setCustomName(piece.isWhite() ? "(W) " + piece.getClass().getSimpleName() : "(B) " + piece.getClass().getSimpleName());
                    villager.setCustomNameVisible(true);
                    villager.setBaby();
                    entities.put(piece, villager);
                } else if(piece instanceof Horse) {
                    org.bukkit.entity.Horse horse = (org.bukkit.entity.Horse) x.getWorld().spawnEntity(x, EntityType.HORSE);
                    horse.setCustomName(piece.isWhite() ? "(W) Horse" : "(B) Horse");
                    horse.setCustomNameVisible(true);
                    horse.setAI(false);
                    horse.setSilent(true);
                    entities.put(piece, horse);
                } else if(piece instanceof Rook) {
                    Blaze blaze = (Blaze) x.getWorld().spawnEntity(x, EntityType.BLAZE);
                    blaze.setCustomName(piece.isWhite() ? "(W) Rook" : "(B) Rook");
                    blaze.setCustomNameVisible(true);
                    blaze.setAI(false);
                    blaze.setSilent(true);
                    blaze.setFireTicks(0);
                    entities.put(piece, blaze);
                } else if(piece instanceof Bishop) {
                    Vex vex = (Vex) x.getWorld().spawnEntity(x, EntityType.VEX);
                    vex.setCustomName(piece.isWhite() ? "(W) Bishop" : "(B) Bishop");
                    vex.setCustomNameVisible(true);
                    vex.setAI(false);
                    vex.setSilent(true);
                    vex.setFireTicks(0);
                    entities.put(piece, vex);
                } else if(piece instanceof Queen) {
                    WitherSkeleton skeleton = (WitherSkeleton) x.getWorld().spawnEntity(x, EntityType.WITHER_SKELETON);
                    skeleton.setCustomName(piece.isWhite() ? "(W) Queen" : "(B) Queen");
                    skeleton.setCustomNameVisible(true);
                    skeleton.setAI(false);
                    skeleton.setSilent(true);
                    skeleton.setFireTicks(0);
                    entities.put(piece, skeleton);
                } else if(piece instanceof King) {
                    Golem golem = (Golem) x.getWorld().spawnEntity(x, EntityType.SNOW_GOLEM);
                    golem.setCustomName(piece.isWhite() ? "(W) King" : "(B) King");
                    golem.setCustomNameVisible(true);
                    golem.setAI(false);
                    golem.setSilent(true);
                    golem.setFireTicks(0);
                    entities.put(piece, golem);
                }
            }
        }

        // make blacks look at whites for the best
        for(ChessPiece piece : currentPieces) {
            if(piece.isWhite()) continue;
            if(!entities.containsKey(piece)) {
                // TODO: Might wanna come back to this
                continue;
            }

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

        // after teleport
        player1.setFlying(true);
        player2.setFlying(true);

        refreshEntitiesOnBoard(true);

        if(!getWhite().isHuman()) {
            Move next = getWhite().getNextMove(board);
            handleMove(next.getBoard().getPiece(next.getOldX(), next.getOldY()), next, getWhite());
        }
    }

    // Does not announce anything, simply cleanups the game
    public void cleanGame() {
        Location spawnLoc = ChessPlugin.getInstance().getGameManager().getSpawnLocation();
        World aWorld = this.arena.getOnePos().getWorld();

        for(Player player : aWorld.getPlayers()) {
            player.teleport(spawnLoc);
            player.setFlying(false);
        }

        this.arena.cleanup();
        ChessPlugin.getInstance().getGameManager().unregisterGame(this);
    }


}
