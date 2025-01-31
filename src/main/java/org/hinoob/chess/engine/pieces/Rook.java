package org.hinoob.chess.engine.pieces;

import org.hinoob.chess.engine.ChessPiece;
import org.hinoob.chess.engine.Chessboard;
import org.hinoob.chess.engine.Move;

import java.util.List;

public class Rook extends ChessPiece {

    public Rook(Chessboard chessboard, boolean isWhite) {
        super(chessboard, isWhite);
    }

    public Rook(Chessboard chessboard, int x, int y, boolean isWhite) {
        super(chessboard, x, y, isWhite);
    }

    @Override
    public void getPossibleMoves(List<Move> moves) {
        for(int x = this.x + 1; x <= 7; x++) {
            addMoveIfValid(moves, x, this.y, false);
            if(board.getPiece(x, this.y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }

        for(int x = this.x - 1; x >= 0; x--) {
            addMoveIfValid(moves, x, this.y, false);
            if(board.getPiece(x, this.y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }

        for(int y = this.y + 1; y <= 7; y++) {
            addMoveIfValid(moves, this.x, y, false);
            if(board.getPiece(this.x, y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }

        for(int y = this.y - 1; y >= 0; y--) {
            addMoveIfValid(moves, this.x, y, false);
            if(board.getPiece(this.x, y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }
    }

    @Override
    public void getCaptures(List<Move> captures) {
        for(int x = this.x + 1; x <= 7; x++) {
            addMoveIfValid(captures, x, this.y, true);
            if(board.getPiece(x, this.y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }

        for(int x = this.x - 1; x >= 0; x--) {
            addMoveIfValid(captures, x, this.y, true);
            if(board.getPiece(x, this.y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }

        for(int y = this.y + 1; y <= 7; y++) {
            addMoveIfValid(captures, this.x, y, true);
            if(board.getPiece(this.x, y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }

        for(int y = this.y - 1; y >= 0; y--) {
            addMoveIfValid(captures, this.x, y, true);
            if(board.getPiece(this.x, y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }
    }

    @Override
    public void getMoves(List<Move> moves) {
        for(int x = this.x + 1; x <= 7; x++) {
            addMovesToBoard(moves, x, this.y, true);
            if(board.getPiece(x, this.y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }

        for(int x = this.x - 1; x >= 0; x--) {
            addMovesToBoard(moves, x, this.y, true);
            if(board.getPiece(x, this.y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }

        for(int y = this.y + 1; y <= 7; y++) {
            addMovesToBoard(moves, this.x, y, true);
            if(board.getPiece(this.x, y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }

        for(int y = this.y - 1; y >= 0; y--) {
            addMovesToBoard(moves, this.x, y, true);
            if(board.getPiece(this.x, y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }

        for(int x = this.x + 1; x <= 7; x++) {
            addMovesToBoard(moves, x, this.y, false);
            if(board.getPiece(x, this.y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }

        for(int x = this.x - 1; x >= 0; x--) {
            addMovesToBoard(moves, x, this.y, false);
            if(board.getPiece(x, this.y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }

        for(int y = this.y + 1; y <= 7; y++) {
            addMovesToBoard(moves, this.x, y, false);
            if(board.getPiece(this.x, y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }

        for(int y = this.y - 1; y >= 0; y--) {
            addMovesToBoard(moves, this.x, y, false);
            if(board.getPiece(this.x, y) != null) {
                // piece above gets removed if invalid
                break;
            }
        }
    }
}
