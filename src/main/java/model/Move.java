package model;

import model.figures.Piece;

/**
 * Represents a single move in game.
 * Stores the origin and target positions, moved piece, captured piece
 * and special move data.
 * This is used for move history tracking, for AI (so it can undo moves).
 */
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

    public Move(Position from, Position to, Piece movedPiece, Piece capturedPiece){
        this.from = from;
        this.to = to;
        this.movedPiece = movedPiece;
        this.capturedPiece = capturedPiece;
        this.wasCastling = false;
        promotingMove = false;
        firstMove = false;
    }
}
