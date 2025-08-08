package logic;

import model.Board;
import model.Move;
import model.MoveResult;
import model.Position;
import model.figures.PieceType;

public class GameManager {
    private Board board;
    private boolean whiteTurn;
    private final ComputerPlayer computerPlayer;

    public GameManager(){
        board = new Board();
        whiteTurn = true;
        computerPlayer = new ComputerPlayer();
    }

    public MoveResult applyMove(Position start, Position targetSquare){
        MoveResult result = board.movePiece(start, targetSquare, whiteTurn);
        if(!isWhiteTurn() && result == MoveResult.PROMOTION){
            applyPromotion(PieceType.QUEEN, targetSquare.getPosX(), targetSquare.getPosY());
        }
        if(result == MoveResult.VALID){
            board.changeTurns();
            whiteTurn = !whiteTurn;

            if(board.staleMate(whiteTurn)){
                return MoveResult.STALE_MATE;
            }

            if(board.checkMate(whiteTurn)){
                return MoveResult.CHECK_MATE;
            }
        }
        board.changeTurns();
        return result;
    }

    public void applyPromotion(PieceType type, int posX, int posY){
        board.promotion(type, whiteTurn, posX, posY);
        board.changeTurns();
        whiteTurn = !whiteTurn;
    }

    public void resetGame(){
        this.board = new Board();
        this.whiteTurn = true;
    }

    public boolean isWhiteTurn(){
        return  whiteTurn;
    }

    public void computerMove(){
        Move move = computerPlayer.findBestMove(board, 3);
        applyMove(move.from, move.to);
    }

    public Board getBoard(){
        return this.board;
    }
}
