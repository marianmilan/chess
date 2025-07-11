import model.Board;

public class GameManager {
    private Board board;
    private boolean whiteTurn;

    public GameManager(){
        board = new Board();
        whiteTurn = true;
    }


   

    public boolean isWhiteTurn(){
        return  whiteTurn;
    }

    public Board getBoard(){
        return this.board;
    }


}
