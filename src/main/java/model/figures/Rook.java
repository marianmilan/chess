package model.figures;

import model.Board;
import model.Position;

public class Rook extends Figure {

    public Rook(boolean white, Position position){
        super(white, position);
    }

    @Override
    public boolean isValidMove(Board board, Position whereTo){
        Position start = this.getPosition();

        int rowDiff = start.yAbsPosDifference(whereTo);
        int colDiff = start.xAbsPosDifference(whereTo);

        int xDirection = start.getPosX() < whereTo.getPosX() ? 1 : -1;
        int yDirection = start.getPosY() < whereTo.getPosY() ? 1 : -1;

        // Check if the line is not straight
        if(rowDiff != 0 && colDiff != 0){
            return false;
        }



    }
}
