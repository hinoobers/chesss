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


    public abstract void getPossibleMoves(List<Move> moves);

}
