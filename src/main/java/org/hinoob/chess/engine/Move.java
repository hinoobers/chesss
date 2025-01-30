package org.hinoob.chess.engine;

public class Move {

    private Chessboard board;
    private int oldX, oldY;
    private int newX, newY;
    private boolean isCapture;

    public Move(Chessboard board, int oldX, int oldY, int newX, int newY, boolean capture) {
        this.board = board;
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
        this.isCapture = capture;
    }

    public Move(Chessboard board, ChessPiece piece, int newX, int newY, boolean capture) {
        this.board = board;
        this.oldX = piece.getX();
        this.oldY = piece.getY();
        this.newX = newX;
        this.newY = newY;
        this.isCapture = capture;
    }

    public boolean isCapture() {
        return isCapture;
    }

    public Chessboard getBoard() {
        return board;
    }

    public int getOldX() {
        return oldX;
    }

    public int getOldY() {
        return oldY;
    }

    public int getNewX() {
        return newX;
    }

    public int getNewY() {
        return newY;
    }
}
