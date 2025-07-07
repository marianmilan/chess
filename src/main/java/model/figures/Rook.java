package model.figures;

import model.Board;
import model.Position;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Position> getPossibleMoves(Board board){
        List<Position> moves = new ArrayList<>();
        MoveHelper.getStraightMoves(board, this, moves, 8);
        return moves;
    }
}
