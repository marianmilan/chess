package model.figures;

import model.Board;
import model.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Queen extends Piece {

    public Queen(boolean white, Position position){
        super(white, position);
    }

    @Override
    public boolean isValidMove(Board board, Position targetSquare) {
        if(!MoveHelper.isWithinBounds(board, this, targetSquare)){
            return false;
        }
        return MoveHelper.isStraightValid(board, this, targetSquare)
            || MoveHelper.isDiagonalValid(board, this, targetSquare);
    }

    @Override
    public List<Position> getPossibleMoves(Board board){
        List<Position> moves = new ArrayList<>();
        MoveHelper.getDiagonalMoves(board, this, moves, 8);
        MoveHelper.getStraightMoves(board, this, moves, 8);
        return MoveHelper.filterMoves(board, this, moves);
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.QUEEN;
    }
}