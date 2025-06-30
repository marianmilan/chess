package model;

public class Position {
    private int posX;
    private int posY;

    public Position(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    public int xPosDifference(Position whereTo){
        return whereTo.posX - this.posX;
    }

    public int yPosDifference(Position whereTo){
        return whereTo.posY - this.posY;
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
