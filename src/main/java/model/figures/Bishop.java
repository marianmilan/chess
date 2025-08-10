package model.figures;

import model.Board;
import model.Move;
import model.MoveResult;
import model.Position;
import java.util.ArrayList;
import java.util.List;

/**
 * Represent bishop piece.
 * Extends Piece class and provides move validation and evaluation specific for bishop.
 */
public class Bishop extends Piece {

    private static final int[][] positionRank = {
            // position values on board (adds or decrease value based on position)
            {-20, -10, -10, -10, -10, -10, -10, -20 },
            {-10,   0,   0,   0,   0,   0,   0, -10 },
            {-10,   0,   5,  10,  10,   5,   0, -10 },
            {-10,   5,   5,  10,  10,   5,   5, -10 },
            {-10,   0,  10,  10,  10,  10,   0, -10 },
            {-10,  10,  10,  10,  10,  10,  10, -10 },
            {-10,   5,   0,   0,   0,   0,   5, -10 },
            {-20, -10, -10, -10, -10, -10, -10, -20 }
    };

    public Bishop(boolean white, Position position) {
        super(white, position);
    }

    @Override
    public MoveResult isValidMove(Board board, Position targetSquare) {
        if (MoveResult.INVALID == MoveHelper.isWithinBounds(board, this, targetSquare)) {
            return MoveResult.INVALID;
        }
        return MoveHelper.isDiagonalValid(board, this, targetSquare);
    }

    @Override
    public List<Move> getPossibleMoves(Board board) {
        List<Move> moves = new ArrayList<>();
        MoveHelper.getDiagonalMoves(board, this, moves, 8);
        return MoveHelper.filterMoves(board, this, moves);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.BISHOP;
    }

    @Override
    public int getValue() {
        int row = this.getPosition().getPosX();
        int col = this.getPosition().getPosY();

        if(this.isWhite()) {
            row = 7 - row;
        }
        return 300 + positionRank[row][col];
    }
}
