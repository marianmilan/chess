package model.figures;

import model.*;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    private static final int[][] positionRank = {
        {  0,   0,   0,   0,   0,   0,   0,   0 },
        {  5,  10,  10, -20, -20,  10,  10,   5 },
        {  5,  -5, -10,   0,   0, -10,  -5,   5 },
        {  0,   0,   0,  20,  20,   0,   0,   0 },
        {  5,   5,  10,  25,  25,  10,   5,   5 },
        { 10,  10,  20,  30,  30,  20,  10,  10 },
        { 50,  50,  50,  50,  50,  50,  50,  50 },
        {  0,   0,   0,   0,   0,   0,   0,   0 }
    };

    public Pawn(boolean white, Position position){
        super(white, position);
    }

    @Override
    public MoveResult isValidMove(Board board, Position targetSquare){
        if(MoveResult.INVALID == MoveHelper.isWithinBounds(board, this, targetSquare)){
            return MoveResult.INVALID;
        }

        int direction = this.isWhite() ? -1 : 1;

        Position start = this.getPosition();
        Position middle = new Position(start.getPosX(), start.getPosY() + direction);

        int rowDiff = start.yAbsPosDifference(targetSquare);
        int colDiff = start.xAbsPosDifference(targetSquare);

        Piece middleSquare = board.getFigureOnSquare(middle);
        Piece endSquare = board.getFigureOnSquare(targetSquare);

        // Check if one move forward is possible
        if(rowDiff == 1 && colDiff == 0 && endSquare == null){
            return MoveResult.VALID;
        }

        // Check if two-square move forward is possible
        if(!this.haveMoved() && rowDiff == 2 && colDiff == 0 && middleSquare == null
                && endSquare == null) {
            return MoveResult.VALID;
        }

        // Check if there is a piece that can be captured
        if(colDiff == 1 && endSquare != null && (endSquare.isWhite() != this.isWhite())
            && endSquare.getPosition().getPosY() == this.getPosition().getPosY() + direction){
            return MoveResult.VALID;
        }

        return MoveResult.INVALID;
    }

    // Return list of all possible moves by selected pawn
    @Override
    public List<Move> getPossibleMoves(Board board){
        int direction = this.isWhite() ? -1 : 1;
        int promotionRow = this.isWhite() ? 0 : 7;
        int currentPosX = this.getPosition().getPosX();
        int currentPosY = this.getPosition().getPosY();

        List<Move> moves = new ArrayList<>();

        Position start = this.getPosition();
        Position oneStraight = new Position(currentPosX, currentPosY + direction);
        Position twoStraight = new Position(currentPosX, currentPosY + 2 * direction);

        if(!this.haveMoved()){
            moves.add(new Move(start, twoStraight, this, null, false));
        }
        if(board.getFigureOnSquare(oneStraight) == null) {
            Move move = new Move(start, oneStraight, this, null, false);
            if(oneStraight.getPosY() == promotionRow) {
                move.setPromotingTo();
            }
            moves.add(move);
        }

        for(int dx : new int[]{-1, 1}){
            Position diagonal = new Position(currentPosX + dx, currentPosY + direction);
            if(diagonal.getPosX() <= 7 && diagonal.getPosX() >= 0){
                Piece target = board.getFigureOnSquare(diagonal);
                if(target != null && target.isWhite() != this.isWhite()){
                    Move capture = new Move(start, diagonal, this, target, false);
                    if(diagonal.getPosY() == promotionRow){
                        capture.setPromotingTo();
                    }
                    moves.add(capture);
                }
            }
        }

        return MoveHelper.filterMoves(board, this, moves);
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.PAWN;
    }

    @Override
    public int getValue() {
        int col = this.getPosition().getPosX();
        int row = this.getPosition().getPosY();

        if(!this.isWhite()){
            row = 7 - row;
        }
        int value = 500 + positionRank[row][col];
        return this.isWhite() ? value : -value;
    }
}