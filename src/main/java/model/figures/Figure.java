package model.figures;

import model.Board;
import model.Position;

public abstract class Figure {

    private boolean white = false;
    private boolean moved = false;
    private Position position;

    public Figure(boolean white, Position position){
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

    public abstract boolean isValidMove(Board board, Position whereTo);

}
