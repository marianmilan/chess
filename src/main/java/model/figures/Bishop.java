package model.figures;

import model.Board;
import model.Position;

public class Bishop extends Figure {

    public Bishop(boolean white, Position position){
        super(white, position);
    }

    @Override
    public boolean isValidMove(Board board, Position whereTo) {
        return SlidingMove.isDiagonalValid(board, this, whereTo);
    }
}
