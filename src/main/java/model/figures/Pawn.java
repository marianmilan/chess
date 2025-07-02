package model.figures;

import model.*;

public class Pawn extends Figure {

    public Pawn(boolean white, Position position){
        super(white, position);
    }

    @Override
    public boolean isValidMove(Board board, Position whereTo){
        int direction = this.isWhite() ? -1 : 1;

        Position start = this.getPosition();
        Position middle = new Position(start.getPosX(), start.getPosY() + direction);

        int rowDiff = start.yAbsPosDifference(whereTo);
        int colDiff = start.xAbsPosDifference(whereTo);

        Figure middleSquare = board.getFigureOnSquare(middle);
        Figure endSquare = board.getFigureOnSquare(whereTo);

        // Check if one move forward is possible
        if(rowDiff == 1 && colDiff == 0 && endSquare == null){
            return true;
        }

        // Check if two-square move forward is possible
        if(!this.haveMoved() && rowDiff == 2 && colDiff == 0 && middleSquare == null
                && endSquare == null) {
            return true;
        }

        // Check if there is a piece that can be captured
        if(rowDiff == 1 && colDiff == 1 && endSquare != null && this.isWhite() != endSquare.isWhite()){
            return true;
        }

        return false;
    }
}
