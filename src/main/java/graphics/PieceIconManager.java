package graphics;

import model.figures.Piece;
import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class PieceIconManager {
    private final Map<String, ImageIcon> icons;

    public PieceIconManager(){
       icons = new HashMap<>();

       icons.put("white_pawn", new ImageIcon("src/main/resources/white_pawn.png"));
       icons.put("black_pawn", new ImageIcon("src/main/resources/black_pawn.png"));
       icons.put("white_rook", new ImageIcon("src/main/resources/white_rook.png"));
       icons.put("black_rook", new ImageIcon("src/main/resources/black_rook.png"));
       icons.put("white_bishop", new ImageIcon("src/main/resources/white_bishop.png"));
       icons.put("black_bishop", new ImageIcon("src/main/resources/black_bishop.png"));
       icons.put("white_knight", new ImageIcon("src/main/resources/white_knight.png"));
       icons.put("black_knight", new ImageIcon("src/main/resources/black_knight.png"));
       icons.put("white_queen", new ImageIcon("src/main/resources/white_queen.png"));
       icons.put("black_queen", new ImageIcon("src/main/resources/black_queen.png"));
       icons.put("white_king", new ImageIcon("src/main/resources/white_king.png"));
       icons.put("black_king", new ImageIcon("src/main/resources/black_king.png"));
    }

    public void displayIcon(ChessBoard.Square button, Piece piece){
        if(piece == null){
            button.setIcon(null);
            return;
        }
        String color = piece.isWhite() ? "white" : "black";
        String iconName = color + "_" + piece.getPieceType().toString().toLowerCase();
        button.setIcon(icons.get(iconName));
    }
}
