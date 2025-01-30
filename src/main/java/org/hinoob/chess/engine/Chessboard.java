package org.hinoob.chess.engine;

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

        pieces.add(new Pawn(this, 0, 6, false));
        pieces.add(new Pawn(this, 1, 6, false));
        pieces.add(new Pawn(this, 2, 6, false));
        pieces.add(new Pawn(this, 3, 6, false));
        pieces.add(new Pawn(this, 4, 6, false));
        pieces.add(new Pawn(this, 5, 6, false));
        pieces.add(new Pawn(this, 6, 6, false));
        pieces.add(new Pawn(this, 7, 6, false));
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public void flip() {
        whiteTurn = !whiteTurn;
    }

    public boolean isWhiteWin() {
        // a lot of calculation
        return false;
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
