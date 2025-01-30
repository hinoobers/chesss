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
        super(chessboard, isWhite);

        moveWithoutMoves(x, y);
    }

    @Override
    public void getPossibleMoves(List<Move> moves) {
        if(this.moves == 0) {
            if(isWhite()) {
                if(board.getPiece(this.x, this.y + 1) == null) {
                    moves.add(new Move(this.board, this, this.x, this.y + 1, false));
                }
                if(board.getPiece(this.x, this.y + 2) == null) {
                    moves.add(new Move(this.board, this, this.x, this.y + 2, false));
                }
            } else {
                if(board.getPiece(this.x, this.y - 1) == null) {
                    moves.add(new Move(this.board, this, this.x, this.y - 1, false));
                }
                if(board.getPiece(this.x, this.y - 2) == null) {
                    moves.add(new Move(this.board, this, this.x, this.y - 2, false));
                }
            }
        } else {
            if(isWhite()) {
                if(board.getPiece(this.x, this.y + 1) == null) {
                    moves.add(new Move(this.board, this, this.x, this.y + 1, false));
                }

                if(board.getPiece(this.x - 1, this.y + 1) != null && board.getPiece(this.x - 1, this.y + 1).isWhite() != this.isWhite()) {
                    moves.add(new Move(this.board, this, this.x - 1, this.y + 1, true));
                }

                if(board.getPiece(this.x + 1, this.y + 1) != null && board.getPiece(this.x + 1, this.y + 1).isWhite() != this.isWhite()) {
                    moves.add(new Move(this.board, this, this.x + 1, this.y + 1, true));
                }
            } else {
                if(board.getPiece(this.x, this.y - 1) == null) {
                    moves.add(new Move(this.board, this, this.x, this.y - 1, false));
                }

                if(board.getPiece(this.x - 1, this.y - 1) != null && board.getPiece(this.x - 1, this.y - 1).isWhite() != this.isWhite()) {
                    moves.add(new Move(this.board, this, this.x - 1, this.y - 1, true));
                }

                if(board.getPiece(this.x + 1, this.y - 1) != null && board.getPiece(this.x + 1, this.y - 1).isWhite() != this.isWhite()) {
                    moves.add(new Move(this.board, this, this.x + 1, this.y - 1, true));
                }
            }
        }
    }

}
