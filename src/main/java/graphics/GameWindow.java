package graphics;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {

    public GameWindow(JPanel windowPanel){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Chess");
        setLayout(new BorderLayout());
        add(addGameMenu(), BorderLayout.NORTH);
        add(windowPanel, BorderLayout.CENTER);
        setSize(1024, 820);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JMenuBar addGameMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem menuItem = new JMenuItem("Reset");
        gameMenu.add(menuItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);
        return menuBar;
    }
}
