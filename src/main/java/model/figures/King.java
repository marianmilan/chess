package model.figures;

import model.Board;
import model.Position;

public class King extends Figure {

    public King(boolean white, Position position){
        super(white, position);
    }

    @Override
    public boolean isValidMove(Board board, Position targetSquare) {
        if (!MoveHelper.isWithinBounds(board, this, targetSquare)) {
            return false;
        }

        Position start = this.getPosition();

        int rowDiff = start.yAbsPosDifference(targetSquare);
        int colDiff = start.xAbsPosDifference(targetSquare);

        if(rowDiff > 1 || colDiff > 1 || (rowDiff == 0 && colDiff == 0)){
            return false;
        }

        return MoveHelper.isValidTarget(board, this, targetSquare);
    }
}
