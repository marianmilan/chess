package model.figures;

import model.Board;
import model.Position;

public class Bishop extends Figure {

    public Bishop(boolean white, Position position){
        super(white, position);
    }

    @Override
    public boolean isValidMove(Board board, Position targetSquare) {

        if(!MoveHelper.isWithinBounds(board, this, targetSquare)){
            return false;
        }

        return MoveHelper.isDiagonalValid(board, this, targetSquare);
    }
}
