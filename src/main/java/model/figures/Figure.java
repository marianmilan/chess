package model.figures;

import model.Position;

public abstract class Figure {

    private boolean white = false;
    private boolean dead = false;
    private boolean moved = false;

    private Position position;

    public Figure(boolean white, Position position){
        this.white = white;
        this.position = position;
    }

    public void killed(){
        this.dead = true;
    }

    public boolean isWhite() {
        return this.white;
    }

    public void moveTo(Position whereTo){
        this.position = whereTo;
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
}
