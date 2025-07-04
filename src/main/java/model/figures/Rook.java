package model.figures;

import model.Board;
import model.Position;

public class Rook extends Piece {

    public Rook(boolean white, Position position){
        super(white, position);
    }

    @Override
    public boolean isValidMove(Board board, Position targetSquare) {

        if(!MoveHelper.isWithinBounds(board, this, targetSquare)){
            return false;
        }

        return MoveHelper.isStraightValid(board, this, targetSquare);
    }
}
