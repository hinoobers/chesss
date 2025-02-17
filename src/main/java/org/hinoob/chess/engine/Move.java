package org.hinoob.chess.engine;

import lombok.Getter;

@Getter
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



    public String toString() {
        return "move[newx=" + newX + ",newy=" + newY + ",oldx=" + oldX + ",oldy=" + oldY + "]";
    }
}
