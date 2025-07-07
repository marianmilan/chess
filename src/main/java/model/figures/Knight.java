package model.figures;

import model.Board;
import model.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Knight extends Piece {

    public Knight(boolean white, Position position){
        super(white, position);
    }

    public boolean isValidMove(Board board, Position targetSquare) {
        if(!MoveHelper.isWithinBounds(board, this, targetSquare)){
            return false;
        }

        Position start = this.getPosition();

        int rowDiff = start.yAbsPosDifference(targetSquare);
        int colDiff = start.xAbsPosDifference(targetSquare);

        boolean isLShapedMove= (rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff ==1);

        return MoveHelper.isValidTarget(board, this, targetSquare) && isLShapedMove;
    }

    @Override
    public List<Position> getPossibleMoves(Board board){
        List<Position> moves = new ArrayList<>();

        int currentPosX = this.getPosition().getPosX();
        int currentPosY = this.getPosition().getPosY();

        moves.add(new Position(currentPosX - 2,  currentPosY - 1));
        moves.add(new Position(currentPosX -2, currentPosY  + 1));
        moves.add(new Position(currentPosX -1, currentPosY - 2));
        moves.add(new Position(currentPosX -1, currentPosY + 2));
        moves.add(new Position(currentPosX + 1, currentPosY - 2));
        moves.add(new Position(currentPosX + 1, currentPosY + 2));
        moves.add(new Position(currentPosX + 2, currentPosY - 1));
        moves.add(new Position(currentPosX + 2, currentPosY + 1));

        return moves.stream()
                .filter(position -> this.isValidMove(board, position))
                .collect(Collectors.toList());
    }
}
