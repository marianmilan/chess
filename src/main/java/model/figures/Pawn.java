package model.figures;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private static final int[][] positionRank = {
            // position values on board (adds or decrease value based on position)
        {  0,   0,   0,   0,   0,   0,   0,   0 },
        {  5,  10,  10, -20, -20,  10,  10,   5 },
        {  5,  -5, -10,   0,   0, -10,  -5,   5 },
        {  0,   0,   0,  20,  20,   0,   0,   0 },
        {  5,   5,  10,  25,  25,  10,   5,   5 },
        { 10,  10,  20,  30,  30,  20,  10,  10 },
        { 50,  50,  50,  50,  50,  50,  50,  50 },
        {  0,   0,   0,   0,   0,   0,   0,   0 }
    };

    public Pawn(boolean white, Position position) {
        super(white, position);
    }

    @Override
    public MoveResult isValidMove(Board board, Position targetSquare) {
        if (MoveResult.INVALID == MoveHelper.isWithinBounds(board, this, targetSquare)) {
            return MoveResult.INVALID;
        }

        int direction = this.isWhite() ? -1 : 1;

        Position start = this.getPosition();
        Position middle = new Position(start.getPosX() + direction, start.getPosY());

        int rowDiff = start.xAbsPosDifference(targetSquare);
        int colDiff = start.yAbsPosDifference(targetSquare);

        Piece middleSquare = board.getFigureOnSquare(middle);
        Piece endSquare = board.getFigureOnSquare(targetSquare);

        // Check if one move forward is possible
        if (rowDiff == 1 && colDiff == 0 && endSquare == null) {
            return MoveResult.VALID;
        }

        // Check if two-square move forward is possible
        if (!this.haveMoved() && rowDiff == 2 && colDiff == 0 && middleSquare == null
                && endSquare == null) {
            return MoveResult.VALID;
        }

        // Check if there is a piece that can be captured
        if (rowDiff == 1 && colDiff == 1 && endSquare != null && (endSquare.isWhite() != this.isWhite())
            && endSquare.getPosition().getPosX() == this.getPosition().getPosX() + direction) {
            return MoveResult.VALID;
        }

        return MoveResult.INVALID;
    }

    // Return list of all possible moves by selected pawn
    @Override
    public List<Move> getPossibleMoves(Board board) {
        int direction = this.isWhite() ? -1 : 1;
        int promotionRow = this.isWhite() ? 0 : 7;
        int currentPosX = this.getPosition().getPosX();
        int currentPosY = this.getPosition().getPosY();

        List<Move> moves = new ArrayList<>();

        Position start = this.getPosition();
        Position oneStraight = new Position(currentPosX + direction, currentPosY);
        Position twoStraight = new Position(currentPosX + 2 * direction, currentPosY);

        if (!this.haveMoved()) {
            moves.add(new Move(start, twoStraight, this, null));
        }

        if (board.getFigureOnSquare(oneStraight) == null) {
            Move move = new Move(start, oneStraight, this, null);
            if(oneStraight.getPosX() == promotionRow) {
                move.promotingMove = true;
                move.promotingPawn = this;
            }
            moves.add(move);
        }

        for (int y : new int[]{-1, 1}) {
            Position diagonal = new Position(currentPosX + direction, currentPosY + y);
            if(diagonal.getPosY() <= 7 && diagonal.getPosY() >= 0){
                Piece target = board.getFigureOnSquare(diagonal);
                if(target != null && target.isWhite() != this.isWhite()){
                    Move move = new Move(start, diagonal, this, target);
                    if(diagonal.getPosX() == promotionRow){
                        move.promotingMove = true;
                        move.promotingPawn = this;
                    }
                    moves.add(move);
                }
            }
        }

        return MoveHelper.filterMoves(board, this, moves);
    }

    @Override
    public PieceType getPieceType() {
        return PieceType.PAWN;
    }

    @Override
    public int getValue() {
        int row = this.getPosition().getPosX();
        int col = this.getPosition().getPosY();

        if (this.isWhite()) {
            row = 7 - row;
        }
        return 100 + positionRank[row][col];
    }
}