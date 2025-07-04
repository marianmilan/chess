package GUI;

import javax.swing.*;
import java.awt.*;

public class WindowPanel extends JPanel {
    private final Square[][] squares = new Square[8][8];

    public WindowPanel(){
        setLayout(new GridBagLayout());
        setupChessBoard();
        setupPieces();
    }

    private void setupChessBoard(){
        int index = 0;
        GridBagConstraints grid = new GridBagConstraints();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
               Color color = index % 2 == 0 ? Color.WHITE : Color.DARK_GRAY;
               grid.gridx = i;
               grid.gridy = j;
               squares[i][j] = new Square(color);
               add(squares[i][j], grid);
               index++;
            }
            index++;
        }
    }

    public void setupPieces(){
        ImageIcon whitePawn = new ImageIcon("src/main/resources/white_pawn.png");
        ImageIcon blackPawn = new ImageIcon("src/main/resources/black_pawn.png");

        ImageIcon whiteKing = new ImageIcon("src/main/resources/white_king.png");
        ImageIcon blackKing = new ImageIcon("src/main/resources/black_king.png");

        ImageIcon whiteQueen = new ImageIcon("src/main/resources/white_queen.png");
        ImageIcon blackQueen = new ImageIcon("src/main/resources/black_queen.png");

        ImageIcon whiteBishop = new ImageIcon("src/main/resources/white_bishop.png");
        ImageIcon blackBishop = new ImageIcon("src/main/resources/black_bishop.png");

        ImageIcon whiteKnight = new ImageIcon("src/main/resources/white_knight.png");
        ImageIcon blackKnight = new ImageIcon("src/main/resources/black_knight.png");

        ImageIcon whiteRook = new ImageIcon("src/main/resources/white_rook.png");
        ImageIcon blackRook = new ImageIcon("src/main/resources/black_rook.png");

        for(int i = 0; i < 8; i++){
            squares[i][6].setIcon(whitePawn);
            squares[i][1].setIcon(blackPawn);
        }

        squares[0][7].setIcon(whiteRook);
        squares[7][7].setIcon(whiteRook);
        squares[1][7].setIcon(whiteKnight);
        squares[6][7].setIcon(whiteKnight);
        squares[2][7].setIcon(whiteBishop);
        squares[5][7].setIcon(whiteBishop);
        squares[3][7].setIcon(whiteQueen);
        squares[4][7].setIcon(whiteKing);

        squares[0][0].setIcon(blackRook);
        squares[7][0].setIcon(blackRook);
        squares[1][0].setIcon(blackKnight);
        squares[6][0].setIcon(blackKnight);
        squares[2][0].setIcon(blackBishop);
        squares[5][0].setIcon(blackBishop);
        squares[3][0].setIcon(blackQueen);
        squares[4][0].setIcon(blackKing);
    }

    // Helper class for chess square
    private class Square extends JButton{

        public Square(Color color) {
            setContentAreaFilled(false);
            setBorderPainted(false);
            // change this after
            setFocusPainted(true);
            setBackground(color);
            setOpaque(true);
        }


        @Override
        public Dimension getPreferredSize(){
            return new Dimension(80,80);
        }
    }
}
