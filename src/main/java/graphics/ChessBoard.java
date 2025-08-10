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

/**
 * Represents a graphical chessboard panel for chess game.
 * Manages squares, player interaction, move highlighting and ending screen.
 */

public class ChessBoard extends JPanel {
    private final Square[][] squares = new Square[8][8];
    // square selected by a player, null if no such square
    private Square selectedSquare = null;
    private final PieceIconManager iconManager;
    private final GameManager manager;
    private final Color light = new Color(214, 189, 157);
    private final Color dark = new Color(89, 52, 34);

    /**
     * Constructor for ChessBoard panel initialize game manager,
     * setup visual board through setupChessBoard.
     */
    public ChessBoard() {
        setBackground(new Color(108, 148, 86, 255));
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

    /**
     * Highlights possible moves for selected piece on square.
     * Empty squares where piece can move are displayed with a mark
     * while squares with enemy piece are highlighted with a background tint.
     * @param square Square to highlight moves on.
     */
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

    /**
     * Displays window with options to choose the promotion piece.
     * @return PieceType type for a promotion.
     */
    public PieceType requestPromotion() {
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
        ImageIcon icon = new ImageIcon("src/main/resources/mark.png");
        ImageIcon white = new ImageIcon("src/main/resources/white_king.png");
        ImageIcon black = new ImageIcon("src/main/resources/black_king.png");
        boolean whiteWon;
        if(result == MoveResult.CHECK_MATE){
            whiteWon = manager.isWhiteTurn();
            endingMessage = whiteWon ? "White won!" : "Black won!";
            icon = whiteWon ? white : black;
        } else {
            endingMessage = "Draw";
        }

        Object[] options = {
                "New Game",
                "Exit Game"
        };



        int n = JOptionPane.showOptionDialog(this, endingMessage, "Result window",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon, options, options[0]);

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

    /**
     * Request GameManager to process the move for the selected piece.
     * Handles move results.
     *
     * @param from Position where the piece is moving from.
     * @param to Position where the piece is moving to.
     */
    public void requestMove(Position from, Position to) {
        MoveResult result = manager.applyMove(from, to);
        switch (result) {
            case INVALID -> selectedSquare = null;

            case VALID -> {
                selectedSquare = null;
                refreshBoard();
                nextTurn();
            }

            case PROMOTION -> {
                if(manager.isWhiteTurn()){
                    manager.applyPromotion(requestPromotion(), to.getPosX(), to.getPosY());
                }
                selectedSquare = null;
                refreshBoard();
                nextTurn();
            }

            default -> {
                refreshBoard();
                endingScreen(result);
            }
        }
    }

    /**
     * Now only changes to black (ai can play only for black atm)
     * check if move performed by ai leads to game ending and calls endingScreen method.
     */
    public void nextTurn(){
        if(!manager.isWhiteTurn()){
            MoveResult result = manager.computerMove();
            refreshBoard();

            if(result == MoveResult.CHECK_MATE || result == MoveResult.STALE_MATE){
                endingScreen(result);
            }
        }
    }

    /**
     * Handles player clicks on chessboard square.
     * Depending on state, this will either:
     * Select the clicked square (if no square is currently selected)
     * Switch selection to a different square of the same color
     * Request a move to the clicked square.
     * @param square Square clicked by player.
     */
    public void playerMove(Square square) {
        refreshBoard();
        Piece target = manager.getBoard().getFigureOnSquare(new Position(square.posX, square.posY));
        // If the square was not selected before, select square and highlight possible moves
        if(selectedSquare == null) {
            if(target != null && target.isWhite() == manager.isWhiteTurn()) {
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

    /**
     * Represents a clickable square of a chessboard with its coordinates.
     * Extends JButton for gui interactions.
     */
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
