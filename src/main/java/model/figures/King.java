package model.figures;

import model.Board;
import model.Move;
import model.MoveResult;
import model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class King extends Piece {
    private static final int[][] positionRank = {
            // position values on board (adds or decrease value based on position)
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-20, -30, -30, -40, -40, -30, -30, -20},
            {-10, -20, -20, -20, -20, -20, -20, -10},
            { 20,  20,   0,   0,   0,   0,  20,  20},
            { 20,  30,  10,   0,   0,  10,  30,  20}
    };

    public King(boolean white, Position position) {
        super(white, position);
    }

    @Override
    public MoveResult isValidMove(Board board, Position targetSquare) {
        if (MoveResult.INVALID == MoveHelper.isWithinBounds(board, this, targetSquare)) {
            return MoveResult.INVALID;
        }

        Position start = this.getPosition();

        int rowDiff = start.xAbsPosDifference(targetSquare);
        int colDiff = start.yAbsPosDifference(targetSquare);

        if (colDiff == 2 && rowDiff == 0) {
            if (targetSquare.getPosY() == 6) {
                return MoveHelper.canCastleKingSide(board, this);
            }

            if (targetSquare.getPosY() == 2) {
                return MoveHelper.canCastleQueenSide(board, this);
            }
        }

        if (rowDiff > 1 || colDiff > 1) {
            return MoveResult.INVALID;
        }

        return MoveHelper.isValidTarget(board, this, targetSquare);
    }

    @Override
    public List<Move> getPossibleMoves(Board board) {
        List<Move> moves = new ArrayList<>();

        MoveHelper.getDiagonalMoves(board, this, moves, 1);
        MoveHelper.getStraightMoves(board, this, moves, 1);
        MoveHelper.getCastlingMoves(board, this, moves);
        return moves.stream()
                .filter(move -> {
                    boolean validMove = isValidMove(board, move.to) == MoveResult.VALID;
                    boolean validCastle = isValidMove(board, move.to) == MoveResult.CASTLE_KINGSIDE
                            || isValidMove(board, move.to) == MoveResult.CASTLE_QUEENSIDE;
                    return validMove || validCastle;
                })
                .filter(move -> !board.checkAfterMove(this.getPosition(), move.to))
                .collect(Collectors.toList());
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.KING;
    }

    @Override
    public int getValue() {
        int row = this.getPosition().getPosX();
        int col = this.getPosition().getPosY();

        if (this.isWhite()) {
            row = 7 - row;
        }
        return 9000 + positionRank[row][col];
    }
}

