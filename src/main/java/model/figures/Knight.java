package model.figures;

import model.Board;
import model.Position;

public class Knight extends Figure {

    public Knight(boolean white, Position position){
        super(white, position);
    }

    public boolean isValidMove(Board board, Position targetSquare) {
        if(!MoveHelper.isWithinBounds(board, this, targetSquare)){
            return false;
        }

        Position start = this.getPosition();

        int rowDiff = start.yAbsPosDifference(targetSquare);
        int colDiff = start.xAbsPosDifference(targetSquare);

        boolean isLShapedMove= (rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff ==1);

        return MoveHelper.isValidTarget(board, this, targetSquare) && isLShapedMove;
    }
}
