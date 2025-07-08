package logic;

import model.Board;
import model.Position;
import model.figures.Piece;

public class GameManager {
    private final Board board;
    private boolean whiteTurn;

    public GameManager(){
        board = new Board();
        whiteTurn = true;
    }


    public boolean makeMove(Position start, Position targetSquare){
        Piece piece = board.getFigureOnSquare(start);

        if(piece != null && whiteTurn == piece.isWhite() && piece.isValidMove(board, targetSquare)){
            board.movePiece(piece, targetSquare);
            whiteTurn = !whiteTurn;
            return true;
        }
        return false;
    }


    public boolean isWhiteTurn(){
        return  whiteTurn;
    }

    public Board getBoard(){
        return this.board;
    }
}
