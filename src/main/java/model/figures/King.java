package model.figures;

import model.Board;
import model.Position;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(boolean white, Position position){
        super(white, position);
    }

    @Override
    public boolean isValidMove(Board board, Position targetSquare) {
        if (!MoveHelper.isWithinBounds(board, this, targetSquare)) {
            return false;
        }

        Position start = this.getPosition();

        int rowDiff = start.yAbsPosDifference(targetSquare);
        int colDiff = start.xAbsPosDifference(targetSquare);

        if(rowDiff > 1 || colDiff > 1 || (rowDiff == 0 && colDiff == 0)){
            return false;
        }

        return MoveHelper.isValidTarget(board, this, targetSquare);
    }

    @Override
    public List<Position> getPossibleMoves(Board board){
        List<Position> moves = new ArrayList<>();
        MoveHelper.getDiagonalMoves(board, this, moves, 1);
        MoveHelper.getStraightMoves(board, this, moves, 1);
        return moves;
    }
}
