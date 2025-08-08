package model.figures;

import model.Board;
import model.Move;
import model.MoveResult;
import model.Position;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    private static final int[][] positionRank = {
            {-50, -40, -30, -30, -30, -30, -40, -50 },
            {-40, -20,   0,   5,   5,   0, -20, -40 },
            {-30,   5,  10,  15,  15,  10,   5, -30 },
            {-30,   0,  15,  20,  20,  15,   0, -30 },
            {-30,   5,  15,  20,  20,  15,   5, -30 },
            {-30,   0,  10,  15,  15,  10,   0, -30 },
            {-40, -20,   0,   0,   0,   0, -20, -40 },
            {-50, -40, -30, -30, -30, -30, -40, -50 }
    };

    public Knight(boolean white, Position position) {
        super(white, position);
    }

    public MoveResult isValidMove(Board board, Position targetSquare) {
        if(MoveResult.INVALID == MoveHelper.isWithinBounds(board, this, targetSquare)){
            return MoveResult.INVALID;
        }

        Position start = this.getPosition();

        int rowDiff = start.yAbsPosDifference(targetSquare);
        int colDiff = start.xAbsPosDifference(targetSquare);

        boolean isLShapedMove= (rowDiff == 1 && colDiff == 2) || (rowDiff == 2 && colDiff ==1);

        if(MoveResult.VALID == MoveHelper.isValidTarget(board, this, targetSquare) && isLShapedMove){
            return MoveResult.VALID;
        }
        return MoveResult.INVALID;
    }

    @Override
    public List<Move> getPossibleMoves(Board board){
        List<Move> moves = new ArrayList<>();

        int currentPosX = this.getPosition().getPosX();
        int currentPosY = this.getPosition().getPosY();

        Position one = new Position(currentPosX - 2,  currentPosY - 1);
        Position two = new Position(currentPosX -2, currentPosY  + 1);
        Position three = new Position(currentPosX -1, currentPosY - 2);
        Position four = new Position(currentPosX -1, currentPosY + 2);
        Position five = new Position(currentPosX + 1, currentPosY - 2);
        Position six = new Position(currentPosX + 1, currentPosY + 2);
        Position seven = new Position(currentPosX + 2, currentPosY - 1);
        Position eight = new Position(currentPosX + 2, currentPosY + 1);

        moves.add(new Move(this.getPosition(), one,this, board.getFigureOnSquare(one), false));
        moves.add(new Move(this.getPosition(), two,this, board.getFigureOnSquare(two), false));
        moves.add(new Move(this.getPosition(), three,this, board.getFigureOnSquare(three), false));
        moves.add(new Move(this.getPosition(), four,this, board.getFigureOnSquare(four), false));
        moves.add(new Move(this.getPosition(), five,this, board.getFigureOnSquare(five), false));
        moves.add(new Move(this.getPosition(), six,this, board.getFigureOnSquare(six), false));
        moves.add(new Move(this.getPosition(), seven,this, board.getFigureOnSquare(seven), false));
        moves.add(new Move(this.getPosition(), eight,this, board.getFigureOnSquare(eight), false));

        return MoveHelper.filterMoves(board, this, moves);
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.KNIGHT;
    }

    @Override
    public int getValue() {
        int row = this.getPosition().getPosX();
        int col = this.getPosition().getPosY();

        if(this.isWhite()){
            row = 7 - row;
        }
        return 300 + positionRank[row][col];
    }
}