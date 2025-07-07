package model.figures;

import model.Board;
import model.Position;

import java.util.List;

public abstract class Piece {

    private boolean white = false;
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

    public void setPosition(Position targetSquare){
        this.position = targetSquare;
    }

    public abstract boolean isValidMove(Board board, Position whereTo);

    public abstract List<Position> getPossibleMoves(Board board);

}
