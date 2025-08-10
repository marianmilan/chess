package model.figures;

import model.Board;
import model.Move;
import model.MoveResult;
import model.Position;
import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    public static final int[][] positionRank = {
            // position values on board (adds or decrease value based on position)
            {-20, -10, -10,  -5,  -5, -10, -10, -20 },
            {-10,   0,   0,   0,   0,   0,   0, -10 },
            {-10,   0,   5,   5,   5,   5,   0, -10 },
            { -5,   0,   5,   5,   5,   5,   0,  -5 },
            {  0,   0,   5,   5,   5,   5,   0,  -5 },
            {-10,   5,   5,   5,   5,   5,   0, -10 },
            {-10,   0,   5,   0,   0,   0,   0, -10 },
            {-20, -10, -10,  -5,  -5, -10, -10, -20 }
    };

    public Queen(boolean white, Position position) {
        super(white, position);
    }

    @Override
    public MoveResult isValidMove(Board board, Position targetSquare) {
        if (MoveResult.INVALID == MoveHelper.isWithinBounds(board, this, targetSquare)) {
            return MoveResult.INVALID;
        }

        if (MoveResult.VALID == MoveHelper.isDiagonalValid(board, this, targetSquare)
                || MoveResult.VALID == MoveHelper.isStraightValid(board, this, targetSquare)) {
            return MoveResult.VALID;
        }
        return MoveResult.INVALID;
    }

    @Override
    public List<Move> getPossibleMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        MoveHelper.getDiagonalMoves(board, this, moves, 8);
        MoveHelper.getStraightMoves(board, this, moves, 8);
        return MoveHelper.filterMoves(board, this, moves);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.QUEEN;
    }

    @Override
    public int getValue() {
        int row = this.getPosition().getPosX();
        int col = this.getPosition().getPosY();

        if (this.isWhite()) {
            row = 7 - row;
        }
        return 900 + positionRank[row][col];
    }
}