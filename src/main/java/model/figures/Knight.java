package model.figures;

import model.Board;
import model.Position;

public class Knight extends Figure {

    public Knight(boolean white, Position position){
        super(white, position);
    }

    public boolean isValidMove(Board board, Position whereTo) {
        Position start = this.getPosition();

        int rowDiff = start.yAbsPosDifference(whereTo);
        int colDiff = start.xAbsPosDifference(whereTo);

        int xPos = whereTo.getPosX();
        int yPos = whereTo.getPosY();

        Figure endSquare = board.getFigureOnSquare(whereTo);

        // Check if the move is within board range
        if (xPos <= 7 && xPos >= 0 && yPos <= 7 && yPos >= 0) {

            // Check 2 forward 1 to the side move
            if ((endSquare == null || endSquare.isWhite() != this.isWhite())
                    && rowDiff == 2 && colDiff == 1) {
                return true;
            }

            // Check 1 forward 2 to the side move
            if ((endSquare == null || endSquare.isWhite() != this.isWhite())
                    && rowDiff == 1 && colDiff == 2) {
                return true;
            }
        }
        return false;
    }
}
