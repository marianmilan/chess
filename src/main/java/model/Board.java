package model;

import model.figures.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Board class that represents chess board.
 * Maintains the position of all pieces on the board, manages game logic,
 * including move validation, tracking moves and determining end conditions.
 * It stores moves in stack so that ai can make and undo moves.
 *
 */
public class Board {
    private final Piece[][] board = new Piece[8][8];
    private final Stack<Move> moves = new Stack<>();

    /**
     * Constructor that set up board.
     * Place pieces on their starting positions.
     */
    public Board() {
        setupBoard();
    }

    private void setupBoard() {
        placePawns();
        placeRooks();
        placeKnights();
        placeBishops();
        placeQueens();
        placeKings();
    }

    private void placePawns() {
        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn(true, new Position(6, i));
            board[1][i] = new Pawn(false, new Position(1, i));
        }
    }

    private void placeRooks() {
        // Place black rooks
        board[0][0] = new Rook(false, new Position(0,0));
        board[0][7] = new Rook(false, new Position(0, 7));

        // Place white rooks
        board[7][0] = new Rook(true, new Position(7, 0));
        board[7][7] = new Rook(true, new Position(7, 7));
    }

    private void placeKnights() {
        // Place black knights
        board[0][1] = new Knight(false, new Position(0,1));
        board[0][6] = new Knight(false, new Position(0, 6));

        // Place white knights
        board[7][1] = new Knight(true, new Position(7, 1));
        board[7][6] = new Knight(true, new Position(7, 6));
    }

    private void placeBishops() {
        // Place black bishops
        board[0][2] = new Bishop(false, new Position(0, 2));
        board[0][5] = new Bishop(false, new Position(0, 5));

        // Place white bishops
        board[7][2] = new Bishop(true, new Position(7, 2));
        board[7][5] = new Bishop(true, new Position(7, 5));
    }

    private void placeQueens() {
        // Place black queen
        board[0][3] = new Queen(false, new Position(0, 3));

        // Place white queen
        board[7][3] = new Queen(true, new Position(7, 3));
    }

    private void placeKings() {
        // Place black king
        board[0][4] = new King(false, new Position(0, 4));

        // Place white king
        board[7][4] = new King(true, new Position(7, 4));
    }

    public Piece[][] getBoard() {
        return board;
    }

    /**
     * Return the piece located on the specified position on board.
     *
     * @param position position on board
     * @return Piece or null if the square is empty
     */
    public Piece getFigureOnSquare(Position position) {
        if (position.getPosX() > 7 || position.getPosX() < 0 || position.getPosY() > 7 || position.getPosY() < 0) {
            return null;
        } else {
            return board[position.getPosX()][position.getPosY()];
        }
    }

    /**
     * Moves a piece on the board from start position to target position,
     * updating piece information and handling move conditions such as castling and pawn promotion.
     *
     * @param start starting square
     * @param targetSquare ending square
     * @param whiteTurn white to move
     * @return MoveResult based on conditions
     */
    public MoveResult movePiece(Position start, Position targetSquare, boolean whiteTurn) {
        Piece piece = getFigureOnSquare(start);

        if(piece == null || piece.isWhite() != whiteTurn || MoveResult.INVALID == piece.isValidMove(this, targetSquare)) {
            return MoveResult.INVALID;
        }

        if(!checkAfterMove(start, targetSquare)){
            if (PieceType.KING == piece.getPieceType() && MoveResult.CASTLE_KINGSIDE == piece.isValidMove(this, targetSquare)) {
                int row = piece.getPosition().getPosX();
                Piece rook = getFigureOnSquare(new Position(row, 7));

                rook.setPosition(new Position(row, 5));
                rook.setMoved();

                piece.setPosition(new Position(row, 7));
                piece.setMoved();
                board[row][start.getPosY()] = null;
                board[row][7] = null;
                board[row][5] = rook;
                board[row][6] = piece;
                return MoveResult.VALID;
            }

            if (PieceType.KING == piece.getPieceType() && MoveResult.CASTLE_QUEENSIDE == piece.isValidMove(this, targetSquare)) {
                int row = piece.getPosition().getPosX();
                Piece rook = getFigureOnSquare(new Position(row, 0));

                rook.setPosition(new Position(row, 3));
                rook.setMoved();

                piece.setPosition(new Position(row, 2));
                piece.setMoved();

                board[row][start.getPosX()] = null;
                board[row][0] = null;
                board[row][3] = rook;
                board[row][2] = piece;
                return MoveResult.VALID;
            }

            board[start.getPosX()][start.getPosY()] = null;
            board[targetSquare.getPosX()][targetSquare.getPosY()] = piece;

            piece.setPosition(targetSquare);
            piece.setMoved();

            if(piece.getPieceType() == PieceType.PAWN) {
                boolean needPromotion = piece.isWhite() ? piece.getPosition().getPosX() == 0 : piece.getPosition().getPosX() == 7;
                if(needPromotion){
                    return MoveResult.PROMOTION;
                }
            }
            return MoveResult.VALID;
        }
        return MoveResult.INVALID;
    }

    /**
     * Check whether the king of the moving piece's color would be in check
     * after moving piece from the start to the target position.
     * This method temporarily makes the move, checks for check,
     * reverts the move and return boolean value.
     *
     * @param start starting square
     * @param targetSquare ending square
     * @return true if king is in check after move, false otherwise
     */
    public boolean checkAfterMove(Position start, Position targetSquare) {
        Piece piece = getFigureOnSquare(start);
        Piece capturedPiece = getFigureOnSquare(targetSquare);

        board[start.getPosX()][start.getPosY()] = null;
        board[targetSquare.getPosX()][targetSquare.getPosY()] = piece;
        piece.setPosition(targetSquare);

        boolean isInCheck = kingInCheck(piece.isWhite());

        board[start.getPosX()][start.getPosY()] = piece;
        board[targetSquare.getPosX()][targetSquare.getPosY()] = capturedPiece;
        piece.setPosition(start);

        return isInCheck;
    }

    /**
     * Loop through the board to find king position of provided color.
     * Throws RuntimeException if the king is not found.
     *
     * @param isWhite color of king, true = white, false = black
     * @return Position of king
     */
    public Position findKingPosition(boolean isWhite) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = getFigureOnSquare(new Position(i, j));
                if (piece != null && piece.getPieceType() == PieceType.KING && piece.isWhite() == isWhite) {
                    return new Position(i, j);
                }
            }
        }
        throw new RuntimeException("King not found which should never happen.");
    }

    /**
     * Checks if the king of the specified color is in check.
     * Loops through all pieces on the board of the opposite color and returns true
     * if any can legally move to the king's position.
     *
     * @param isWhite color of piece
     * @return true if king is in check, false otherwise
     */
    public boolean kingInCheck(boolean isWhite) {
        Position kingPosition = findKingPosition(isWhite);

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = getFigureOnSquare(new Position(i, j));
                if (piece != null && piece.isWhite() != isWhite
                        && MoveResult.VALID == piece.isValidMove(this, kingPosition)) {
                    return true;
                }
            }
        }
       return false;
    }

    /**
     * If the king of provided color is in check loop over all the pieces
     * and check if there is possible moves that can block the check, or if king can escape.
     * If not returns false.
     *
     * @param isWhite color of piece
     * @return true if checkmate occurs, false otherwise
     */
    public boolean checkMate(boolean isWhite) {
        if (!kingInCheck(isWhite)) {
            return false;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = getFigureOnSquare(new Position(i, j));
                if (piece != null && piece.isWhite() == isWhite && !piece.getPossibleMoves(this).isEmpty()) {
                    return false;
                }
            }
        }
       return true;
    }

    /**
     * If the king of provided color is not in check, loop through all the pieces
     * of this color and if there are no possible moves return true, otherwise false.
     *
     * @param isWhite color of piece
     * @return true if stalemate occurs, false otherwise
     */
    public boolean staleMate(boolean isWhite){
        if(kingInCheck(isWhite)){
           return false;
        }

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = getFigureOnSquare(new Position(i, j));
                if(piece != null && piece.isWhite() == isWhite && !piece.getPossibleMoves(this).isEmpty()){
                    return false;
                }
            }
        }
        return true;

    }

    /**
     * Check if checkmate or stalemate occurs,
     * this is used as a condition for ai minimax.
     *
     * @param isWhite color of piece
     * @return true if checkmate or stalemate occurs, otherwise false
     */
    public boolean gameOver(boolean isWhite) {
        return checkMate(isWhite) || staleMate(isWhite);
    }

    /**
     * Loop through all the pieces of specified colors
     * and add them together. Returns value of all pieces of this color.
     *
     * @param isWhite color of piece
     * @return value of pieces
     */
    public int evalPieces(boolean isWhite){
        int eval = 0;
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++) {
                Piece piece = getFigureOnSquare(new Position(i, j));
                if(piece != null && piece.isWhite() == isWhite) {
                    eval += piece.getValue();
                }
            }
        }
        return eval;
    }

    /**
     * Evaluates the board position by calculating the total material value
     * then returning the difference from black's perspective.
     * Calculated as (black total value) - (white total value)
     *
     * @return value of board
     */
    public int getBoardEval() {
        int whitePieces = evalPieces(true);
        int blackPieces = evalPieces(false);

        return blackPieces - whitePieces;
    }


    /**
     * Executes the move on board, updating piece positions, handling special moves,
     * and adding the move to the stack.
     * Prevents moves that would leave the moving side's king in check.
     * Prevents moves directly on king's square.
     * Updates the piece's has moved status.
     * Handles pawn promotion
     * Handles castling moves
     * Update the board state.
     *
     * @param move move to be performed
     */
    public void makeMove(Move move){
        if(checkAfterMove(move.from, move.to)) return;
        if(move.to.equals(findKingPosition(!move.movedPiece.isWhite()))) return;

        int fromX = move.from.getPosX();
        int fromY = move.from.getPosY();
        int toX = move.to.getPosX();
        int toY = move.to.getPosY();

        if(!move.movedPiece.haveMoved()){
            move.firstMove = true;
            move.movedPiece.setMoved();
        }

        if(move.promotingMove){
            board[fromX][fromY] = null;
            promotion(PieceType.QUEEN, move.movedPiece.isWhite(), toX, toY);
            moves.push(move);
            return;
        }

        if(move.wasCastling){
            int rookX = move.castleRook.getPosition().getPosX();
            int rookY = move.castleRook.getPosition().getPosY();
            int newRookY = rookY == 0 ? 3 : 5;

            board[fromX][fromY] = null;
            board[rookX][rookY] = null;
            board[toX][toY] = move.movedPiece;
            board[rookX][newRookY] = move.castleRook;

            move.movedPiece.setPosition(move.to);
            move.castleRook.setPosition(new Position(rookX, newRookY));
            move.castleRook.setMoved();

            moves.push(move);
            return;
        }

        board[fromX][fromY] = null;
        board[toX][toY] = move.movedPiece;
        move.movedPiece.setPosition(move.to);
        moves.push(move);
    }

    /**
     * Get the last performed move and restore the board according to it.
     */
    public void undoMove() {
        Move move = moves.pop();
        int fromX = move.from.getPosX();
        int fromY = move.from.getPosY();
        int toX = move.to.getPosX();
        int toY = move.to.getPosY();

        if(move.firstMove) {
            move.movedPiece.undoMoved();
            move.firstMove = false;
        }

        if(move.wasCastling) {
            int oldRookX = move.castleRook.getPosition().getPosX();
            int oldRookY = move.castleRook.getPosition().getPosY();

            int rookX = move.castleRookPos.getPosX();
            int rookY = move.castleRookPos.getPosY();


            board[fromX][fromY] = move.movedPiece;
            board[rookX][rookY] = move.castleRook;
            board[toX][toY] = null;
            board[oldRookX][oldRookY] = null;

            move.castleRook.undoMoved();
            move.movedPiece.setPosition(move.from);
            move.castleRook.setPosition(move.castleRookPos);
            return;
        }

        if(move.promotingMove) {
            board[toX][toY] = move.capturedPiece;
            board[fromX][fromY] = move.promotingPawn;
            return;
        }

        board[fromX][fromY] = move.movedPiece;
        board[toX][toY] = move.capturedPiece;
        move.movedPiece.setPosition(move.from);
    }

    /**
     * Return all possible moves by all pieces of provided color.
     *
     * @param isWhite piece color
     * @return List of moves
     */
    public List<Move> getAllPossibleMoves(boolean isWhite){
        List<Move> moves = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Piece piece = getFigureOnSquare(new Position(i, j));
                if(piece != null && piece.isWhite() == isWhite){
                    moves.addAll(piece.getPossibleMoves(this));
                }
            }
        }
        return moves;
    }

    /**
     * Handles promotion and updates board accordingly.
     * @param type piece type to be promoted to
     * @param white color of piece
     * @param posX row position
     * @param posY col position
     */
    public void promotion(PieceType type, boolean white, int posX, int posY){
        Piece newPiece;
        Position position = new Position(posX, posY);
        switch (type) {
            case QUEEN -> newPiece = new Queen(white, position);
            case BISHOP -> newPiece = new Bishop(white, position);
            case KNIGHT -> newPiece = new Knight(white, position);
            case ROOK -> newPiece = new Rook(white, position);
            default -> newPiece = null;
        }
        board[posX][posY] = newPiece;
    }
}