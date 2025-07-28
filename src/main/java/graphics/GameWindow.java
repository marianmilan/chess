package graphics;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    public GameWindow(ChessBoard chessBoard){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setTitle("Chess");
        add(chessBoard);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
