package graphics;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import logic.GameManager;
import model.MoveResult;
import model.Position;
import model.figures.Piece;
import model.figures.PieceType;

public class WindowPanel extends JPanel {
    private final Square[][] squares = new Square[8][8];
    private Square selectedSquare = null;
    private final PieceIconManager iconManager;
    private final GameManager manager;

    public WindowPanel() {
        setBackground(new Color(152, 118, 84));
        setLayout(new GridBagLayout());
        this.manager = new GameManager();
        this.iconManager = new PieceIconManager();
        setupChessBoard();
        refreshBoard();
    }

    private void setupChessBoard() {
        GridBagConstraints grid = new GridBagConstraints();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
               Color color = (i + j) % 2 == 0 ? Color.WHITE : Color.DARK_GRAY;
               grid.gridx = i;
               grid.gridy = j;
               squares[i][j] = new Square(color, i, j);
               squares[i][j].addActionListener(event -> {
                   Square clicked = (Square) event.getSource();
                   requestMove(clicked);
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
                } else {
                    squares[i][j].setIcon(null);
                }
            }
        }
    }

    public void highlightMoves(Square square) {
        Piece currentPiece = manager.getBoard().getFigureOnSquare(new Position(square.posX, square.posY));

        if(currentPiece != null && currentPiece.isWhite() == manager.isWhiteTurn()) {
            List<Position> moves = currentPiece.getPossibleMoves(manager.getBoard());
            moves.forEach(position -> {
                /*
                 * display valid moves for a piece, if the square is empty (icon == null) add icon of a green circle
                 *  else if the square has a piece that can be captured highlight the background of the square
                 */
                if(squares[position.getPosX()][position.getPosY()].getIcon() == null){
                    squares[position.getPosX()][position.getPosY()].setIcon(new ImageIcon("src/main/resources/mark.png"));
                } else {
                    squares[position.getPosX()][position.getPosY()].setBackground(new Color(109, 153, 99, 100));
                }
            });
        }
    }

    public void requestPromotion(Square square){
        Object[] options = {
                PieceType.QUEEN,
                PieceType.KNIGHT,
                PieceType.BISHOP,
                PieceType.ROOK
        };
        int n = JOptionPane.showOptionDialog(this, " ",
                "Promotion", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        manager.makePromotion((PieceType) options[n], square.posX, square.posY);
        refreshBoard();
    }

    public void endingScreen(){
        String message = manager.isWhiteTurn() ? "Vyhral cierny" : "Vyhral biely";
        JOptionPane.showMessageDialog(this, message);
    }

    public void requestMove(Square square) {
        refreshBoard();

        // If the square was not selected before, select square and highlight possible moves
        if(selectedSquare == null) {
            highlightMoves(square);
            selectedSquare = square;
        } else {
            Piece target = manager.getBoard().getFigureOnSquare(new Position(square.posX, square.posY));

            // If the target square is same color display new piece highlight moves
            if (target != null && target.isWhite() == manager.isWhiteTurn()) {
                highlightMoves(square);
                selectedSquare = square;
            }

            // If the move is valid make move and refresh board
            MoveResult result = manager.makeMove(new Position(selectedSquare.posX, selectedSquare.posY), new Position(square.posX, square.posY));
            switch (result){
                case VALID -> {
                    selectedSquare = null;
                    refreshBoard();
                }
                case PROMOTION -> {
                    selectedSquare = null;
                    refreshBoard();
                    requestPromotion(square);
                }
                case CHECK_MATE -> {
                    refreshBoard();
                    endingScreen();
                }
            }
        }
    }

    // Helper class for chess square
    public static class Square extends JButton {
        private final int posX;
        private final int posY;

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
        public Dimension getPreferredSize() {
            return new Dimension(80,80);
        }

    }
}
