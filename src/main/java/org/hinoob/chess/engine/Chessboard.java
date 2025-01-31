package org.hinoob.chess.engine;

import lombok.SneakyThrows;
import org.hinoob.chess.engine.pieces.*;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

public class Chessboard {

    private List<ChessPiece> pieces = new ArrayList<>();
    private boolean whiteTurn = true;


    public Chessboard() {

    }


    public void loadPieces() {
        pieces.add(new Pawn(this, 0, 1, true));
        pieces.add(new Pawn(this, 1, 1, true));
        pieces.add(new Pawn(this, 2, 1, true));
        pieces.add(new Pawn(this, 3, 1, true));
        pieces.add(new Pawn(this, 4, 1, true));
        pieces.add(new Pawn(this, 5, 1, true));
        pieces.add(new Pawn(this, 6, 1, true));
        pieces.add(new Pawn(this, 7, 1, true));

        pieces.add(new King(this, 3, 0, true));
        pieces.add(new Queen(this, 4, 0, false));
        pieces.add(new Horse(this, 1, 0, true));
        pieces.add(new Horse(this, 6, 0, true));
        pieces.add(new Rook(this, 0, 0, true));
        pieces.add(new Rook(this, 7, 0, true));
        pieces.add(new Bishop(this, 2, 0, true));
        pieces.add(new Bishop(this, 5, 0, true));

        pieces.add(new Pawn(this, 0, 6, false));
        pieces.add(new Pawn(this, 1, 6, false));
        pieces.add(new Pawn(this, 2, 6, false));
        pieces.add(new Pawn(this, 3, 6, false));
        pieces.add(new Pawn(this, 4, 6, false));
        pieces.add(new Pawn(this, 5, 6, false));
        pieces.add(new Pawn(this, 6, 6, false));
        pieces.add(new Pawn(this, 7, 6, false));

        pieces.add(new King(this, 3, 7, false));
        pieces.add(new Queen(this, 4, 7, false));
        pieces.add(new Horse(this, 1, 7, false));
        pieces.add(new Horse(this, 6, 7, false));
        pieces.add(new Rook(this, 0, 7, false));
        pieces.add(new Rook(this, 7, 7, false));
        pieces.add(new Bishop(this, 2, 7, false));
        pieces.add(new Bishop(this, 5, 7, false));
    }

    @SneakyThrows
    public void loadPiecesFrom(Chessboard board) {
        this.pieces = new ArrayList<>();
        for(ChessPiece piece : board.pieces) {
            ChessPiece pc = piece.clone();
            pc.board = this;
            this.pieces.add(pc);
        }
        this.whiteTurn = board.whiteTurn;
    }

    public boolean isWhiteKingInCheck() {
        King whiteKing = (King) pieces.stream().filter(p -> p.isWhite() && p instanceof King).findAny().orElse(null);
        if(whiteKing == null) return false;

        for (ChessPiece otherPiece : pieces) {
            if (!otherPiece.isWhite()) { // Only black pieces attack
                List<Move> captures = new ArrayList<>();
                otherPiece.getMoves(captures);

                for (Move move : captures) {
                    if (move.isCapture() && move.getNewX() == whiteKing.getX() && move.getNewY() == whiteKing.getY()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isBlackKingInCheck() {
        King blackKing = (King) pieces.stream().filter(p -> !p.isWhite() && p instanceof King).findAny().orElse(null);
        if(blackKing == null) return false;

        for (ChessPiece otherPiece : pieces) {
            if (otherPiece.isWhite()) { // Only white pieces attack
                List<Move> captures = new ArrayList<>();

                otherPiece.getMoves(captures);

                for (Move move : captures) {
                    if (move.isCapture() && move.getNewX() == blackKing.getX() && move.getNewY() == blackKing.getY()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }


    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void flip() {
        whiteTurn = !whiteTurn;
    }

    public void movePiece(ChessPiece piece, int newX, int newY) {
        ChessPiece targetPiece = getPiece(newX, newY);
        if (targetPiece != null) {
            pieces.remove(targetPiece);
        }
        piece.move(newX, newY);
        pieces.add(piece);
    }


    public List<ChessPiece> getPieces() {
        return pieces;
    }

    public ChessPiece getPiece(int x, int y) {
        return pieces.stream().filter(p -> p.getX() == x && p.getY() == y).findFirst().orElse(null);
    }
}
