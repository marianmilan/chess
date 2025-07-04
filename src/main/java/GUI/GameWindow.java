package GUI;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    public GameWindow(JPanel windowPanel){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Chess");
        setLayout(new BorderLayout());
        add(windowPanel, BorderLayout.CENTER);
        setSize(1024, 820);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
