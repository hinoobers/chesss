package org.hinoob.chess.engine.pieces;

import org.hinoob.chess.engine.ChessPiece;
import org.hinoob.chess.engine.Chessboard;
import org.hinoob.chess.engine.Move;

import java.util.List;

public class Pawn extends ChessPiece {

    public Pawn(Chessboard chessboard, boolean isWhite) {
        super(chessboard, isWhite);
    }

    public Pawn(Chessboard chessboard, int x, int y, boolean isWhite) {
        super(chessboard, x, y, isWhite);
    }

    @Override
    public void getPossibleMoves(List<Move> moves) {
        int direction = isWhite() ? 1 : -1; // White moves up, Black moves down

        addMoveIfValid(moves, this.x, this.y + direction, false);

        // first move gets 2 squares
        if (this.moves == 0) {
            addMoveIfValid(moves, this.x, this.y + (2 * direction), false);
        }

        getCaptures(moves);
    }

    @Override
    public void getCaptures(List<Move> captures) {
        int direction = isWhite() ? 1 : -1;
        addMoveIfValid(captures, this.x - 1, this.y + direction, true);
        addMoveIfValid(captures, this.x + 1, this.y + direction, true);
    }

    @Override
    public void getMoves(List<Move> moves) {
        int direction = isWhite() ? 1 : -1;
        addMovesToBoard(moves, this.x - 1, this.y + direction, true);
        addMovesToBoard(moves, this.x + 1, this.y + direction, true);

        addMovesToBoard(moves, this.x, this.y + direction, false);

        if(this.moves == 0) {
            addMovesToBoard(moves, this.x, this.y + (2 * direction), false);
        }
    }
}
