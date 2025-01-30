package org.hinoob.chess.engine.pieces;

import org.hinoob.chess.engine.ChessPiece;
import org.hinoob.chess.engine.Chessboard;
import org.hinoob.chess.engine.Move;

import java.util.List;

public class Pawn extends ChessPiece {

    public Pawn(Chessboard chessboard, boolean isWhite) {
        super(chessboard, isWhite);
    }

    @Override
    public void getPossibleMoves(List<Move> moves) {
        if(this.moves == 0) {
            moves.add(new Move(this.board, this, this.x, this.y + 1));
            moves.add(new Move(this.board, this, this.x, this.y + 2));
        } else {
            moves.add(new Move(this.board, this, this.x, this.y + 1));
        }
    }

}
