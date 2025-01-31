package org.hinoob.chess.engine;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

public abstract class ChessPiece {

    protected Chessboard board;
    protected int x, y;
    protected int moves;
    private boolean isWhite;

    public ChessPiece(Chessboard board, boolean isWhite) {
        this.board = board;
        this.isWhite = isWhite;
    }

    public ChessPiece(Chessboard chessboard, int x, int y, boolean isWhite) {
        this.board = chessboard;
        this.isWhite = isWhite;

        moveWithoutMoves(x, y);
    }

    @Override
    @SneakyThrows
    public ChessPiece clone() {
        // cba to make clone class for every piece, this is enough
        ChessPiece clonedPiece = this.getClass().getConstructor(Chessboard.class, int.class, int.class, boolean.class).newInstance(null, this.x, this.y, this.isWhite);
        return clonedPiece;
    }

    public void move(int x, int y) {
        moveWithoutMoves(x, y);
        moves++;
    }

    public void moveWithoutMoves(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWhite() {
        return isWhite;
    }

    protected void addMovesToBoard(List<Move> moves, int newX, int newY, boolean captureOnly) {
        if(newX < 0 || newY < 0 || newX > 7 || newY > 7) return;

        ChessPiece piece = board.getPiece(newX, newY);

        if (piece == null && !captureOnly) { // Normal move
            moves.add(new Move(this.board, this, newX, newY, false));
        } else if (piece != null && piece.isWhite() != this.isWhite()) { // Capture move
            moves.add(new Move(this.board, this, newX, newY, true));
        }
    }

    // helper function
    protected void addMoveIfValid(List<Move> moves, int newX, int newY, boolean captureOnly) {
        if(newX < 0 || newY < 0 || newX > 7 || newY > 7) return;

        // Simulate the move to check if it gets the king out of check
        Chessboard tempBoard = new Chessboard();
        tempBoard.loadPiecesFrom(this.board);

        // Get the piece from the tempBoard
        ChessPiece tempPiece = tempBoard.getPiece(this.x, this.y);

        // Move the piece temporarily
        tempBoard.movePiece(tempPiece, newX, newY);

        // Use the flag to prevent recursion during check validation
        boolean check = isWhite ? tempBoard.isWhiteKingInCheck() : tempBoard.isBlackKingInCheck();
        if(check) {
            return; // If in check, discard this move
        }

        ChessPiece piece = board.getPiece(newX, newY);

        if(piece != null && piece.isWhite == this.isWhite) {
            // Under no circumstance we can hop to our own square
            return;
        }

        if (piece == null && !captureOnly) { // Normal move
            moves.add(new Move(this.board, this, newX, newY, false));
        } else if (piece != null && piece.isWhite() != this.isWhite()) { // Capture move
            moves.add(new Move(this.board, this, newX, newY, true));
        }
    }




    public abstract void getPossibleMoves(List<Move> moves);

    public abstract void getCaptures(List<Move> captures);

    public abstract void getMoves(List<Move> moves);

}
