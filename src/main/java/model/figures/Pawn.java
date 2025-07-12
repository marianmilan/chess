package model.figures;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Pawn extends Piece {

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
    public List<Position> getPossibleMoves(Board board){
        int direction = this.isWhite() ? -1 : 1;

        int currentPosX = this.getPosition().getPosX();
        int currentPosY = this.getPosition().getPosY();

        List<Position> moves = new ArrayList<>();

        // Add all moves pawn can make
        moves.add(new Position(currentPosX, currentPosY + direction));
        moves.add(new Position(currentPosX + 1, currentPosY + direction));
        moves.add(new Position(currentPosX - 1, currentPosY + direction));
        if(!this.haveMoved()){
            moves.add(new Position(currentPosX, currentPosY + 2 * direction));
        }
        // return list of possible moves selected pawn can make
        return MoveHelper.filterMoves(board, this, moves);
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.PAWN;
    }
}