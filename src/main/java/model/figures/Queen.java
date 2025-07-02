package model.figures;

import model.Board;
import model.Position;

public class Queen extends Figure {

    public Queen(boolean white, Position position){
        super(white, position);
    }

    @Override
    public boolean isValidMove(Board board, Position whereTo) {

    }
}
