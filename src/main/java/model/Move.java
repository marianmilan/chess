package model;

import model.figures.Piece;
import model.figures.PieceType;

public class Move {
    public final Position from;
    public final Position to;
    public final Piece movedPiece;
    public final Piece capturedPiece;
    public PieceType promotingTo;
    public boolean wasCastling;

    public Move(Position from, Position to, Piece movedPiece, Piece capturedPiece, boolean wasCastling){
        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.wasCastling = wasCastling;
        this.promotingTo = null;
    }

    public void setPromotingTo(){
        this.promotingTo = PieceType.QUEEN;
    }

    public boolean isPromoting(){
        return promotingTo != null;
    }
}
