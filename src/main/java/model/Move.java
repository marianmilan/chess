package model;

import model.figures.Piece;

public class Move {
    public Position from;
    public Position to;
    public final Piece movedPiece;
    public final Piece capturedPiece;
    public  Piece promotingPawn;
    public boolean wasCastling;
    public boolean promotingMove;
    public boolean firstMove;
    public Piece castleRook;
    public Position castleRookPos;

    public Move(Position from, Position to, Piece movedPiece, Piece capturedPiece, boolean wasCastling){
        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.wasCastling = wasCastling;
        promotingMove = false;
        firstMove = false;
    }
}
