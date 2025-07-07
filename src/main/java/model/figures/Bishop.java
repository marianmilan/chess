package model.figures;

import model.Board;
import model.Position;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {

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

    @Override
    public List<Position> getPossibleMoves(Board board){
        List<Position> moves = new ArrayList<>();
        MoveHelper.getDiagonalMoves(board, this, moves, 8);
        return moves;
    }
}
