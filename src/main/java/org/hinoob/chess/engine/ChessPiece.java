package org.hinoob.chess.engine;

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

    // helper function
    protected void addMoveIfValid(List<Move> moves, int newX, int newY, boolean captureOnly) {
        if (!board.checkBounds(newX, newY)) return;

        ChessPiece piece = board.getPiece(newX, newY);

        if (piece == null && !captureOnly) { // Normal move
            moves.add(new Move(this.board, this, newX, newY, false));
        } else if (piece != null && piece.isWhite() != this.isWhite()) { // Capture move
            moves.add(new Move(this.board, this, newX, newY, false));
        }
    }


    public abstract void getPossibleMoves(List<Move> moves);

    public abstract void getCaptures(List<Move> captures);

}
