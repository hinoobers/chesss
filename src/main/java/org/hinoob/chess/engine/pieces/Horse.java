package org.hinoob.chess.engine.pieces;

import org.hinoob.chess.engine.ChessPiece;
import org.hinoob.chess.engine.Chessboard;
import org.hinoob.chess.engine.Move;

import java.util.List;

public class Horse extends ChessPiece {

    public Horse(Chessboard chessboard, boolean isWhite) {
        super(chessboard, isWhite);
    }

    public Horse(Chessboard chessboard, int x, int y, boolean isWhite) {
        super(chessboard, x, y, isWhite);
    }

    @Override
    public void getPossibleMoves(List<Move> moves) {
        addMoveIfValid(moves, this.x + 1, this.y + 2, false);
        addMoveIfValid(moves, this.x + 2, this.y + 1, false);
        addMoveIfValid(moves, this.x + 2, this.y - 1, false);
        addMoveIfValid(moves, this.x + 1, this.y - 2, false);
        addMoveIfValid(moves, this.x - 1, this.y + 2, false);
        addMoveIfValid(moves, this.x - 2, this.y + 1, false);
        addMoveIfValid(moves, this.x - 2, this.y - 1, false);
        addMoveIfValid(moves, this.x - 1, this.y - 2, false);
    }

    @Override
    public void getCaptures(List<Move> captures) {
        addMoveIfValid(captures, this.x + 1, this.y + 2, true);
        addMoveIfValid(captures, this.x + 2, this.y + 1, true);
        addMoveIfValid(captures, this.x + 2, this.y - 1, true);
        addMoveIfValid(captures, this.x + 1, this.y - 2, true);
        addMoveIfValid(captures, this.x - 1, this.y + 2, true);
        addMoveIfValid(captures, this.x - 2, this.y + 1, true);
        addMoveIfValid(captures, this.x - 2, this.y - 1, true);
        addMoveIfValid(captures, this.x - 1, this.y - 2, true);
    }
}
