package org.hinoob.chess.engine;

import org.hinoob.chess.engine.pieces.Pawn;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.ArrayList;
import java.util.List;

public class Chessboard {

    private List<ChessPiece> pieces = new ArrayList<>();

    public void loadPieces() {
        pieces.add(new Pawn(this, false));
    }

    public boolean isWhiteWin() {
        // a lot of calculation
        return false;
    }

    public boolean isBlackWin() {
        return false;
    }

    public ChessPiece getPiece(int x, int y) {
        return pieces.stream().filter(p -> p.getX() == x && p.getY() == y).findFirst().orElse(null);
    }
}
