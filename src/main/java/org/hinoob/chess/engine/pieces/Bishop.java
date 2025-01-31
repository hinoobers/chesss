package org.hinoob.chess.engine.pieces;

import org.hinoob.chess.engine.ChessPiece;
import org.hinoob.chess.engine.Chessboard;
import org.hinoob.chess.engine.Move;

import java.util.List;

public class Bishop extends ChessPiece {

    public Bishop(Chessboard chessboard, boolean isWhite) {
        super(chessboard, isWhite);
    }

    public Bishop(Chessboard chessboard, int x, int y, boolean isWhite) {
        super(chessboard, x, y, isWhite);
    }

    @Override
    public void getPossibleMoves(List<Move> moves) {
        for (int i = 1; i <= 7; i++) {
            int newX = this.x + i;
            int newY = this.y + i;
            addMoveIfValid(moves, newX, newY, false);
            if (board.getPiece(newX, newY) != null) {
                break;
            }
        }

        for (int i = 1; i <= 7; i++) {
            int newX = this.x - i;
            int newY = this.y + i;
            addMoveIfValid(moves, newX, newY, false);
            if (board.getPiece(newX, newY) != null) {
                break; // Stop if there's a piece in the way
            }
        }

        for (int i = 1; i <= 7; i++) {
            int newX = this.x - i;
            int newY = this.y - i;
            addMoveIfValid(moves, newX, newY, false);
            if (board.getPiece(newX, newY) != null) {
                break; // Stop if there's a piece in the way
            }
        }

        for (int i = 1; i <= 7; i++) {
            int newX = this.x + i;
            int newY = this.y - i;
            addMoveIfValid(moves, newX, newY, false);
            if (board.getPiece(newX, newY) != null) {
                break; // Stop if there's a piece in the way
            }
        }
    }

    @Override
    public void getCaptures(List<Move> captures) {
        for (int i = 1; i <= 7; i++) {
            int newX = this.x + i;
            int newY = this.y + i;
            addMoveIfValid(captures, newX, newY, true);
            if (board.getPiece(newX, newY) != null) {
                break;
            }
        }

        for (int i = 1; i <= 7; i++) {
            int newX = this.x - i;
            int newY = this.y + i;
            addMoveIfValid(captures, newX, newY, true);
            if (board.getPiece(newX, newY) != null) {
                break; // Stop if there's a piece in the way
            }
        }

        for (int i = 1; i <= 7; i++) {
            int newX = this.x - i;
            int newY = this.y - i;
            addMoveIfValid(captures, newX, newY, true);
            if (board.getPiece(newX, newY) != null) {
                break; // Stop if there's a piece in the way
            }
        }

        for (int i = 1; i <= 7; i++) {
            int newX = this.x + i;
            int newY = this.y - i;
            addMoveIfValid(captures, newX, newY, true);
            if (board.getPiece(newX, newY) != null) {
                break; // Stop if there's a piece in the way
            }
        }
    }

    @Override
    public void getMoves(List<Move> moves) {
        for (int i = 1; i <= 7; i++) {
            int newX = this.x + i;
            int newY = this.y + i;
            addMovesToBoard(moves, newX, newY, true);
            if (board.getPiece(newX, newY) != null) {
                break; // Stop if there's a piece in the way
            }
        }

        // Top-left diagonal
        for (int i = 1; i <= 7; i++) {
            int newX = this.x - i;
            int newY = this.y + i;
            addMovesToBoard(moves, newX, newY, true);
            if (board.getPiece(newX, newY) != null) {
                break; // Stop if there's a piece in the way
            }
        }

        // Bottom-left diagonal
        for (int i = 1; i <= 7; i++) {
            int newX = this.x - i;
            int newY = this.y - i;
            addMovesToBoard(moves, newX, newY, true);
            if (board.getPiece(newX, newY) != null) {
                break; // Stop if there's a piece in the way
            }
        }

        // Bottom-right diagonal
        for (int i = 1; i <= 7; i++) {
            int newX = this.x + i;
            int newY = this.y - i;
            addMovesToBoard(moves, newX, newY, true);
            if (board.getPiece(newX, newY) != null) {
                break; // Stop if there's a piece in the way
            }
        }

        for (int i = 1; i <= 7; i++) {
            int newX = this.x + i;
            int newY = this.y + i;
            addMovesToBoard(moves, newX, newY, false);
            if (board.getPiece(newX, newY) != null) {
                break; // Stop if there's a piece in the way
            }
        }

        // Top-left diagonal
        for (int i = 1; i <= 7; i++) {
            int newX = this.x - i;
            int newY = this.y + i;
            addMovesToBoard(moves, newX, newY, false);
            if (board.getPiece(newX, newY) != null) {
                break; // Stop if there's a piece in the way
            }
        }

        // Bottom-left diagonal
        for (int i = 1; i <= 7; i++) {
            int newX = this.x - i;
            int newY = this.y - i;
            addMovesToBoard(moves, newX, newY, false);
            if (board.getPiece(newX, newY) != null) {
                break; // Stop if there's a piece in the way
            }
        }

        // Bottom-right diagonal
        for (int i = 1; i <= 7; i++) {
            int newX = this.x + i;
            int newY = this.y - i;
            addMovesToBoard(moves, newX, newY, false);
            if (board.getPiece(newX, newY) != null) {
                break; // Stop if there's a piece in the way
            }
        }
    }
}
