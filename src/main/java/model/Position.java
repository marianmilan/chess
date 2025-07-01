package model;

public class Position {
    private int posX;
    private int posY;

    public Position(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public int xAbsPosDifference(Position whereTo){
        return Math.abs(whereTo.posX - this.posX);
    }

    public int yAbsPosDifference(Position whereTo){
        return Math.abs(whereTo.posY - this.posY);
    }

    public void setPosition(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }
}
