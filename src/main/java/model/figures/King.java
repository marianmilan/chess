package model.figures;

import model.Board;
import model.MoveResult;
import model.Position;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public King(boolean white, Position position){
        super(white, position);
    }

    @Override
    public MoveResult isValidMove(Board board, Position targetSquare) {
        if (MoveResult.INVALID == MoveHelper.isWithinBounds(board, this, targetSquare)) {
            return MoveResult.INVALID;
        }

        Position start = this.getPosition();

        int rowDiff = start.yAbsPosDifference(targetSquare);
        int colDiff = start.xAbsPosDifference(targetSquare);

        if(colDiff == 2 && rowDiff == 0){
            int row = start.getPosY();

            if(targetSquare.getPosX() == 6){
                return MoveHelper.canCastleKingSide(board, this);
            }

            if(targetSquare.getPosX() == 2){
                return MoveHelper.canCastleQueenSide(board, this);
            }
        }

        if(rowDiff > 1 || colDiff > 1){
            return MoveResult.INVALID;
        }

        return MoveHelper.isValidTarget(board, this, targetSquare);
    }

    @Override
    public List<Position> getPossibleMoves(Board board){
        List<Position> moves = new ArrayList<>();

        MoveHelper.getDiagonalMoves(board, this, moves, 1);
        MoveHelper.getStraightMoves(board, this, moves, 1);
        MoveHelper.getCastlingMoves(board, this, moves);
        return moves.stream()
                .filter(position -> {
                    boolean validMove = isValidMove(board, position) == MoveResult.VALID;
                    boolean validCastle = isValidMove(board, position) == MoveResult.CASTLE_KINGSIDE
                            || isValidMove(board, position) == MoveResult.CASTLE_QUEENSIDE;
                    return validMove || validCastle;
                })
                .filter(position -> !board.checkAfterMove(this.getPosition(), position))
                .collect(Collectors.toList());
    }
    @Override
    public PieceType getPieceType(){
        return PieceType.KING;
    }
}

