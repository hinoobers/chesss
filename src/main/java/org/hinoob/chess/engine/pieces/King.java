package org.hinoob.chess.engine.pieces;

import org.bukkit.Bukkit;
import org.hinoob.chess.engine.ChessPiece;
import org.hinoob.chess.engine.Chessboard;
import org.hinoob.chess.engine.Move;

import java.util.ArrayList;
import java.util.List;

public class King extends ChessPiece {
    public King(Chessboard board, boolean isWhite) {
        super(board, isWhite);
    }

    public King(Chessboard chessboard, int x, int y, boolean isWhite) {
        super(chessboard, x, y, isWhite);
    }

    @Override
    public void getPossibleMoves(List<Move> moves) {
        Chessboard tempBoard = new Chessboard();
        tempBoard.loadPiecesFrom(this.board);

        ChessPiece tempPiece = tempBoard.getPiece(this.x, this.y);
        if (tempPiece == null) return; // Just in case

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                if (xOffset == 0 && yOffset == 0) continue;

                int newX = this.x + xOffset;
                int newY = this.y + yOffset;

                if (isOutOfBounds(newX, newY)) continue;
                if (tempBoard.getPiece(newX, newY) != null) continue;

                if (!isMoveCausingCheck(tempBoard, newX, newY)) {
                    moves.add(new Move(this.board, this, newX, newY, false));
                }
            }
        }

        getCaptures(moves); // Captures are handled separately
    }

    @Override
    public void getCaptures(List<Move> captures) {
        Chessboard tempBoard = new Chessboard();
        tempBoard.loadPiecesFrom(this.board);

        ChessPiece tempPiece = tempBoard.getPiece(this.x, this.y);
        if (tempPiece == null) return; // Just in case

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                if (xOffset == 0 && yOffset == 0) continue;

                int newX = this.x + xOffset;
                int newY = this.y + yOffset;

                if (isOutOfBounds(newX, newY)) continue;

                ChessPiece targetPiece = tempBoard.getPiece(newX, newY);
                if (targetPiece == null || targetPiece.isWhite() == this.isWhite()) continue;

                if (!isMoveCausingCheck(tempBoard, newX, newY)) {
                    captures.add(new Move(this.board, this, newX, newY, true));
                }
            }
        }
    }

    @Override
    public void getMoves(List<Move> moves) {

    }

    // Helper method to check if a square is out of bounds
    private boolean isOutOfBounds(int x, int y) {
        return x < 0 || y < 0 || x > 7 || y > 7;
    }

    // Helper method to check if a move puts the king in check
    private boolean isMoveCausingCheck(Chessboard tempBoard, int newX, int newY) {
        ChessPiece tempPiece = tempBoard.getPiece(this.x, this.y);
        int originalX = this.x;
        int originalY = this.y;

        // Move the piece temporarily to the new position
        tempBoard.movePiece(tempPiece, newX, newY);

        boolean inCheck = false;
        for (ChessPiece other : tempBoard.getPieces()) {
            if (other.isWhite() == this.isWhite()) continue;

            List<Move> tempMoves = new ArrayList<>();
            other.getCaptures(tempMoves);

            if (tempMoves.stream().anyMatch(m -> m.getNewX() == newX && m.getNewY() == newY)) {
                inCheck = true;
                break;
            }
        }

        // Restore the original position
        tempBoard.movePiece(tempPiece, originalX, originalY);

        return inCheck;
    }
}
