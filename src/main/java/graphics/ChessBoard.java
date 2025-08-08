package graphics;

import java.awt.*;
import java.util.List;
import javax.swing.*;

import logic.GameManager;
import model.Move;
import model.MoveResult;
import model.Position;
import model.figures.Piece;
import model.figures.PieceType;

public class ChessBoard extends JPanel {
    private final Square[][] squares = new Square[8][8];
    private Square selectedSquare = null;
    private final PieceIconManager iconManager;
    private final GameManager manager;
    Color light = new Color(214, 189, 157);
    Color dark = new Color(89, 52, 34);

    public ChessBoard() {
        setBackground(new Color(243, 243, 243, 255));
        setPreferredSize(new Dimension(642, 642));
        setBorder(BorderFactory.createLineBorder(dark, 1));
        setLayout(new GridBagLayout());
        this.manager = new GameManager();
        this.iconManager = new PieceIconManager();
        setupChessBoard();
        refreshBoard();
    }

    private void setupChessBoard() {
        GridBagConstraints grid = new GridBagConstraints();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
               Color color = (row + col) % 2 == 0 ? light : dark;
               grid.gridx = col;
               grid.gridy = row;
               squares[row][col] = new Square(color, row, col);
               squares[row][col].addActionListener(event -> {
                   Square clicked = (Square) event.getSource();
                   playerMove(clicked);
               });
               add(squares[row][col], grid);
            }
        }
    }

    public void refreshBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Color color = (i + j) % 2 == 0 ? light : dark;
                squares[i][j].setBackground(color);
                Piece piece = manager.getBoard().getFigureOnSquare(new Position(i, j));
                iconManager.displayIcon(squares[i][j], piece);
            }
        }
    }

    public void highlightMoves(Square square) {
        Piece currentPiece = manager.getBoard().getFigureOnSquare(new Position(square.posX, square.posY));

        if(currentPiece != null && currentPiece.isWhite() == manager.isWhiteTurn()) {
            List<Move> moves = currentPiece.getPossibleMoves(manager.getBoard());
            moves.forEach(move -> {
                if(squares[move.to.getPosX()][move.to.getPosY()].getIcon() == null) {
                    squares[move.to.getPosX()][move.to.getPosY()].setIcon(new ImageIcon("src/main/resources/mark.png"));
                } else {
                    squares[move.to.getPosX()][move.to.getPosY()].setBackground(new Color(176, 126, 80, 180));
                }
            });
        }
    }

    public PieceType requestPromotion(){
        Object[] options = {
                PieceType.QUEEN,
                PieceType.KNIGHT,
                PieceType.BISHOP,
                PieceType.ROOK
        };
        int n = JOptionPane.showOptionDialog(this, "Choose to which piece the pawn to be promoted.",
                "Promotion Window", JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        return (PieceType) options[n];
    }

    public void endingScreen(MoveResult result){
        String endingMessage;
        if(result == MoveResult.CHECK_MATE){
            endingMessage = manager.isWhiteTurn() ? "Black won" : "White won";
        } else {
            endingMessage = "Draw";
        }

        Object[] options = {
                "New Game",
                "Exit Game"
        };

        int n = JOptionPane.showOptionDialog(this, endingMessage, "Result window",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if(n == 0){
            manager.resetGame();
            refreshBoard();
        } else {
            Window window = SwingUtilities.getWindowAncestor(this);
            if(window != null){
                window.dispose();
            }
            System.exit(0);
        }
    }

    public void requestMove(Position from, Position to) {
        MoveResult result = manager.applyMove(from, to);
        switch (result){
            case INVALID -> {
                selectedSquare = null;
            }

            case VALID -> {
                selectedSquare = null;
                refreshBoard();
                nextTurn();
            }

            case PROMOTION -> {
                if(manager.isWhiteTurn()){
                    selectedSquare = null;
                    manager.applyPromotion(requestPromotion(), to.getPosX(), to.getPosY());
                }
                refreshBoard();
                nextTurn();
            }

            default -> {
                refreshBoard();
                endingScreen(result);
            }
        }
    }

    public void nextTurn(){
        if(!manager.isWhiteTurn()){
            manager.computerMove();
            refreshBoard();
        }
    }

    public void playerMove(Square square) {
        refreshBoard();
        Piece target = manager.getBoard().getFigureOnSquare(new Position(square.posX, square.posY));
        // If the square was not selected before, select square and highlight possible moves
        if(selectedSquare == null) {
            if(target != null && target.isWhite() == manager.isWhiteTurn()){
                highlightMoves(square);
                selectedSquare = square;
            }
        } else {
            // If the target square is same color display new piece highlight moves
            if (target != null && target.isWhite() == manager.isWhiteTurn()) {
                highlightMoves(square);
                selectedSquare = square;
            } else {
                requestMove(new Position(selectedSquare.posX, selectedSquare.posY), new Position(square.posX, square.posY));
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
