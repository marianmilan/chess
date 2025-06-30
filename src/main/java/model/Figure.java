package model;

public abstract class Figure {

    private boolean white = false;
    private boolean dead = false;

    private int posX;
    private int posY;

    public Figure(boolean white, int posX, int posY){
        this.white = white;
        this.posX = posX;
        this.posY = posY;
    }

    public void killed(){
        this.dead = true;
    }

    public boolean isWhite() {
        return this.white;
    }

    public void moveTo(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
}
