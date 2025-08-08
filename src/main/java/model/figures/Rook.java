package model.figures;

import model.Board;
import model.Move;
import model.MoveResult;
import model.Position;

import java.util.ArrayList;
import java.util.List;

public class Rook extends Piece {
    public static final int[][] positionRank = {
            {  0,   0,   0,   5,   5,   0,   0,   0 },
            { -5,   0,   0,   0,   0,   0,   0,  -5 },
            { -5,   0,   0,   0,   0,   0,   0,  -5 },
            { -5,   0,   0,   0,   0,   0,   0,  -5 },
            { -5,   0,   0,   0,   0,   0,   0,  -5 },
            { -5,   0,   0,   0,   0,   0,   0,  -5 },
            {  5,  10,  10,  10,  10,  10,  10,   5 },
            {  0,   0,   0,   0,   0,   0,   0,   0 }
    };

    public Rook(boolean white, Position position){
        super(white, position);
    }

    @Override
    public MoveResult isValidMove(Board board, Position targetSquare) {

        if(MoveResult.INVALID == MoveHelper.isWithinBounds(board, this, targetSquare)){
            return MoveResult.INVALID;
        }

        return MoveHelper.isStraightValid(board, this, targetSquare);
    }

    @Override
    public List<Move> getPossibleMoves(Board board){
        List<Move> moves = new ArrayList<>();
        MoveHelper.getStraightMoves(board, this, moves, 8);
        return MoveHelper.filterMoves(board, this, moves);
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.ROOK;
    }

    @Override
    public int getValue() {
        int row = this.getPosition().getPosX();
        int col = this.getPosition().getPosY();

        if(this.isWhite()){
            row = 7 - row;
        }
        return 500 + positionRank[row][col];
    }
}