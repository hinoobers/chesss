package org.hinoob.chess.engine;

import org.hinoob.chess.engine.pieces.Horse;
import org.hinoob.chess.engine.pieces.King;
import org.hinoob.chess.engine.pieces.Pawn;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

public class Chessboard {

    private List<ChessPiece> pieces = new ArrayList<>();
    private boolean whiteTurn = true;

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
        pieces.add(new Horse(this, 1, 0, true));
        pieces.add(new Horse(this, 6, 0, true));

        pieces.add(new Pawn(this, 0, 6, false));
        pieces.add(new Pawn(this, 1, 6, false));
        pieces.add(new Pawn(this, 2, 6, false));
        pieces.add(new Pawn(this, 3, 6, false));
        pieces.add(new Pawn(this, 4, 6, false));
        pieces.add(new Pawn(this, 5, 6, false));
        pieces.add(new Pawn(this, 6, 6, false));
        pieces.add(new Pawn(this, 7, 6, false));

        pieces.add(new King(this, 3, 7, false));
        pieces.add(new Horse(this, 1, 7, false));
        pieces.add(new Horse(this, 6, 7, false));
    }

    public void loadPiecesFrom(Chessboard board) {
        this.pieces = new ArrayList<>(board.pieces);
        this.whiteTurn = board.whiteTurn;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void flip() {
        whiteTurn = !whiteTurn;
    }

    public boolean checkBounds(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    public void movePiece(ChessPiece piece, int newX, int newY) {
        ChessPiece targetPiece = getPiece(newX, newY);
        if (targetPiece != null) {
            removePiece(targetPiece);
        }
        piece.move(newX, newY);
        pieces.add(piece);
    }


    public boolean isWhiteWin() {
        // a lot of calculation
        ChessPiece blackKing = pieces.stream().filter(p -> !p.isWhite() && p instanceof King).findFirst().orElse(null);

        boolean canTake = false;
        for(ChessPiece whitePiece : pieces.stream().filter(ChessPiece::isWhite).toList()) {
            List<Move> captures = new ArrayList<>();
            whitePiece.getCaptures(captures);

            for(Move capture : captures) {
                if(capture.getNewX() == blackKing.getX() && capture.getNewY() == blackKing.getY()) {
                    canTake = true;
                }
            }
        }

        List<Move> blackMoves = new ArrayList<>();
        blackKing.getPossibleMoves(blackMoves);

        return blackMoves.isEmpty() && canTake;
    }

    public boolean isBlackWin() {
        return false;
    }

    public List<ChessPiece> getPieces() {
        return pieces;
    }

    public ChessPiece getPiece(int x, int y) {
        return pieces.stream().filter(p -> p.getX() == x && p.getY() == y).findFirst().orElse(null);
    }

    public void removePiece(ChessPiece piece) {
        pieces.remove(piece);
    }
}
