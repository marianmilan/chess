package model.figures;

import model.Board;
import model.MoveResult;
import model.Position;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {

    public Queen(boolean white, Position position){
        super(white, position);
    }

    @Override
    public MoveResult isValidMove(Board board, Position targetSquare) {
        if(MoveResult.INVALID == MoveHelper.isWithinBounds(board, this, targetSquare)){
            return MoveResult.INVALID;
        }

        if(MoveResult.VALID == MoveHelper.isDiagonalValid(board, this, targetSquare) || MoveResult.VALID == MoveHelper.isStraightValid(board, this, targetSquare)){
            return MoveResult.VALID;
        }
        return MoveResult.INVALID;
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