package model;

/**
 * Class that stores row and column positions on board.
 */
public class Position {
    private int posX;
    private int posY;

    public Position(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }

    /**
     * Helper function to calculate row difference between piece square and target square.
     * It is calculated as absolute so it does not matter which direction the piece is moving.
     *
     * @param whereTo target square
     * @return absolute difference between rows
     */
    public int xAbsPosDifference(Position whereTo){
        return Math.abs(whereTo.posX - this.posX);
    }

    /**
     * Helper function to calculate column difference between piece square and target square.
     * It is calculated as absolute so it does not matter which direction the piece is moving.
     *
     * @param whereTo target square
     * @return absolute difference between columns
     */
    public int yAbsPosDifference(Position whereTo){
        return Math.abs(whereTo.posY - this.posY);
    }

    public int getPosX(){
        return posX;
    }

    public int getPosY(){
        return posY;
    }

    /**
     * Compares two positions for equality.
     * Positions are considered equal if they have same row and column values.
     *
     * @param obj object to compare this position with
     * @return true if equals, false otherwise
     */
    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || obj.getClass() != getClass()) return false;

        Position other = (Position) obj;

        return this.getPosX() == other.getPosX() && this.getPosY() == other.getPosY();
    }
}
