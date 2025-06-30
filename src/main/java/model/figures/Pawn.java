package model.figures;

import model.*;

public class Pawn extends Figure {

    public Pawn(boolean white, Position position){
        super(white, position);
    }

    public boolean isValidMove(Position whereTo){
        return true;
    }
}
