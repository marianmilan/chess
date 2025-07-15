package logic;

import model.Board;
import model.MoveResult;
import model.Position;
import model.figures.PieceType;

public class GameManager {
    private Board board;
    private boolean whiteTurn;

    public GameManager(){
        board = new Board();
        whiteTurn = true;
    }


    public MoveResult makeMove(Position start, Position targetSquare){
        MoveResult result = board.movePiece(start, targetSquare, whiteTurn);
        if(result == MoveResult.VALID){
            whiteTurn = !whiteTurn;

            if(board.staleMate(whiteTurn)){
                return MoveResult.STALE_MATE;
            }

            if(board.checkMate(whiteTurn)){
                return MoveResult.CHECK_MATE;
            }
        }
        return result;
    }

    public void makePromotion(PieceType type, int posX, int posY){
        board.promotion(type, whiteTurn, posX, posY);
        whiteTurn = !whiteTurn;
    }

    public void resetGame(){
        this.board = new Board();
        this.whiteTurn = true;
    }

    public boolean isWhiteTurn(){
        return  whiteTurn;
    }

    public Board getBoard(){
        return this.board;
    }
}
