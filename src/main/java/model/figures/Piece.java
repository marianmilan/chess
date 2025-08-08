package model.figures;

import model.Board;
import model.Move;
import model.MoveResult;
import model.Position;

import java.util.List;

public abstract class Piece {

    private final boolean white;
    private boolean moved = false;
    private Position position;

    public Piece(boolean white, Position position){
        this.white = white;
        this.position = position;
    }

    public boolean isWhite() {
        return this.white;
    }

    public Position getPosition(){
        return this.position;
    }

    public boolean haveMoved(){
        return this.moved;
    }

    public void setMoved(){
        this.moved = true;
    }

    public void undoMoved() {
        this.moved = false;
    }

    public void setPosition(Position targetSquare){
        this.position = targetSquare;
    }

    public abstract MoveResult isValidMove(Board board, Position whereTo);

    public abstract List<Move> getPossibleMoves(Board board);

    public abstract PieceType getPieceType();

    public abstract int getValue();
}
