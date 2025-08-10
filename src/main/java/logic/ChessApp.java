package logic;

import graphics.GameWindow;
import graphics.ChessBoard;

public class ChessApp {
    public static void main(String[] args){
        GameWindow window = new GameWindow(new ChessBoard());
    }
}
