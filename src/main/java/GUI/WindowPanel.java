package GUI;

import Logic.GameManager;
import model.Position;
import model.figures.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class WindowPanel extends JPanel {
    private final Square[][] squares = new Square[8][8];
    private final PieceIconManager iconManager;
    private final GameManager manager;

    public WindowPanel(){
        setLayout(new GridBagLayout());
        this.manager = new GameManager();
        this.iconManager = new PieceIconManager();
        setupChessBoard();
        refreshBoard();
    }

    private void setupChessBoard(){
        GridBagConstraints grid = new GridBagConstraints();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
               Color color = (i + j) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY;
               grid.gridx = i;
               grid.gridy = j;
               squares[i][j] = new Square(color, i, j);
               squares[i][j].addActionListener(event -> {
                   Square clicked = (Square) event.getSource();
                   highlightSquares(clicked);
               });
               add(squares[i][j], grid);
            }
        }
    }

    public void refreshBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color color = (i + j) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY;
                squares[i][j].setBackground(color);
                Piece piece = manager.getBoard().getFigureOnSquare(new Position(i, j));
                if (piece != null) {
                    iconManager.displayIcon(squares[i][j], piece);
                }
            }
        }
    }

    public void highlightSquares(Square square){
        refreshBoard();
        Piece currentPiece = manager.getBoard().getFigureOnSquare(new Position(square.posX, square.posY));

        if(currentPiece != null){
            List<Position> moves = currentPiece.getPossibleMoves(manager.getBoard());
            moves.stream()
                    .forEach(position -> {
                        squares[position.getPosX()][position.getPosY()].setBackground(new Color(255, 165, 0, 150));
                    });
        }
    }

    // Helper class for chess square
    public static class Square extends JButton {
        private int posX;
        private int posY;

        public Square(Color color, int posX, int posY) {
            this.posX = posX;
            this.posY = posY;

            setContentAreaFilled(false);
            setBorderPainted(false);
            setFocusPainted(false);
            setBackground(color);
            setOpaque(true);
            setRolloverEnabled(false);
        }

        @Override
        public Dimension getPreferredSize(){
            return new Dimension(80,80);
        }

    }
}
