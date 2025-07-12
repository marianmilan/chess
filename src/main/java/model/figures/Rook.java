package model.figures;

import model.Board;
import model.MoveResult;
import model.Position;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {

    public Rook(boolean white, Position position){
        super(white, position);
    }

    @Override
    public MoveResult isValidMove(Board board, Position targetSquare) {

        if(MoveResult.INVALID == MoveHelper.isWithinBounds(board, this, targetSquare)){
            return MoveResult.INVALID;
        }

        return MoveHelper.isStraightValid(board, this, targetSquare);
    }

    @Override
    public List<Position> getPossibleMoves(Board board){
        List<Position> moves = new ArrayList<>();
        MoveHelper.getStraightMoves(board, this, moves, 8);
        return MoveHelper.filterMoves(board, this, moves);
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.ROOK;
    }
}