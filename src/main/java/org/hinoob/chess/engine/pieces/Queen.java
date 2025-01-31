package org.hinoob.chess.engine.pieces;

import org.hinoob.chess.engine.ChessPiece;
import org.hinoob.chess.engine.Chessboard;
import org.hinoob.chess.engine.Move;

import java.util.List;

public class Queen extends ChessPiece {

    public Queen(Chessboard chessboard, boolean isWhite) {
        super(chessboard, isWhite);
    }

    public Queen(Chessboard chessboard, int x, int y, boolean isWhite) {
        super(chessboard, x, y, isWhite);
    }

    @Override
    public void getPossibleMoves(List<Move> moves) {
        for (int i = 1; i <= 7; i++) {
            addMoveIfValid(moves, this.x + i, this.y, false);
            addMoveIfValid(moves, this.x - i, this.y, false);
            addMoveIfValid(moves, this.x, this.y + i, false);
            addMoveIfValid(moves, this.x, this.y - i, false);
        }

        for (int i = 1; i <= 7; i++) {
            addMoveIfValid(moves, this.x + i, this.y + i, false);
            addMoveIfValid(moves, this.x - i, this.y + i, false);
            addMoveIfValid(moves, this.x - i, this.y - i, false);
            addMoveIfValid(moves, this.x + i, this.y - i, false);
        }
    }

    @Override
    public void getCaptures(List<Move> captures) {
        for (int i = 1; i <= 7; i++) {
            addMoveIfValid(captures, this.x + i, this.y, true);
            addMoveIfValid(captures, this.x - i, this.y, true);
            addMoveIfValid(captures, this.x, this.y + i, true);
            addMoveIfValid(captures, this.x, this.y - i, true);
        }

        for (int i = 1; i <= 7; i++) {
            addMoveIfValid(captures, this.x + i, this.y + i, true);
            addMoveIfValid(captures, this.x - i, this.y + i, true);
            addMoveIfValid(captures, this.x - i, this.y - i, true);
            addMoveIfValid(captures, this.x + i, this.y - i, true);
        }
    }

    @Override
    public void getMoves(List<Move> moves) {
        for (int i = 1; i <= 7; i++) {
            addMovesToBoard(moves, this.x + i, this.y, true);
            addMovesToBoard(moves, this.x - i, this.y, true);
            addMovesToBoard(moves, this.x, this.y + i, true);
            addMovesToBoard(moves, this.x, this.y - i, true);
        }

        for (int i = 1; i <= 7; i++) {
            addMovesToBoard(moves, this.x + i, this.y + i, true);
            addMovesToBoard(moves, this.x - i, this.y + i, true);
            addMovesToBoard(moves, this.x - i, this.y - i, true);
            addMovesToBoard(moves, this.x + i, this.y - i, true);
        }

        for (int i = 1; i <= 7; i++) {
            addMovesToBoard(moves, this.x + i, this.y, false);
            addMovesToBoard(moves, this.x - i, this.y, false);
            addMovesToBoard(moves, this.x, this.y + i, false);
            addMovesToBoard(moves, this.x, this.y - i, false);
        }

        for (int i = 1; i <= 7; i++) {
            addMovesToBoard(moves, this.x + i, this.y + i, false);
            addMovesToBoard(moves, this.x - i, this.y + i, false);
            addMovesToBoard(moves, this.x - i, this.y - i, false);
            addMovesToBoard(moves, this.x + i, this.y - i, false);
        }
    }
}
