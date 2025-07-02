package model.figures;

import model.Board;
import model.Position;

public class King extends Figure {

    public King(boolean white, Position position){
        super(white, position);
    }


    @Override
    public boolean isValidMove(Board board, Position whereTo) {

    }
}
