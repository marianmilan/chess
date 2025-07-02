package model.figures;

import model.Board;
import model.Position;

public class Rook extends Figure {

    public Rook(boolean white, Position position){
        super(white, position);
    }

    @Override
    public boolean isValidMove(Board board, Position whereTo) {
        return SlidingMove.isStraightValid(board, this, whereTo);
    }
}
