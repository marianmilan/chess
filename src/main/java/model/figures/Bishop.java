package model.figures;

import model.Board;
import model.Position;

public class Bishop extends Figure {

    public Bishop(boolean white, Position position){
        super(white, position);
    }

    @Override
    public boolean isValidMove(Board board, Position whereTo) {
        Position start = this.getPosition();

        int rowDiff = start.yAbsPosDifference(whereTo);
        int colDiff = start.xAbsPosDifference(whereTo);


        // Check if the move is diagonal
        if(rowDiff != colDiff) {
           return false;
        }

        // Calculate direction for diagonals
        int xDirection = start.getPosX() < whereTo.getPosX() ? 1 : -1;
        int yDirection = start.getPosY() < whereTo.getPosY() ? 1 : -1;

        // Get this piece x and y coordinates
        int thisX = start.getPosX();
        int thisY = start.getPosY();

        // Loop through the squares up to end square to check if it is blocked
        for(int i = 1; i < rowDiff; i++){
            int checkX = thisX + i * xDirection;
            int checkY = thisY +i * yDirection;
            if(board.getFigureOnSquare(new Position(checkX, checkY)) != null){
                return false;
            }
        }

        Figure endSquare = board.getFigureOnSquare(whereTo);

        // Check if the target square is null or piece that can be captured
        if(endSquare == null || endSquare.isWhite() != this.isWhite()){
            return true;
        }
        return false;
    }
}
