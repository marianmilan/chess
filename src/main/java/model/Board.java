package model;

import model.figures.*;

public class Board {
    private final Piece[][] board = new Piece[8][8];

    public Board(){
        setupBoard();
    }

    private void setupBoard(){
        placePawns();
        placeRooks();
        placeKnights();
        placeBishops();
        placeQueens();
        placeKings();
    }

    private void placePawns(){
        for(int i = 0; i < 8; i++){
            board[i][6] = new Pawn(true, new Position(i, 6));
            board[i][1] = new Pawn(false, new Position(i, 1));
        }
    }

    private void placeRooks(){
        // Place black rooks
        board[0][0] = new Rook(false, new Position(0,0));
        board[7][0] = new Rook(false, new Position(7, 0));

        // Place white rooks
        board[0][7] = new Rook(true, new Position(0, 7));
        board[7][7] = new Rook(true, new Position(7, 7));
    }

    private void placeKnights(){
        // Place black knights
        board[1][0] = new Knight(false, new Position(1,0));
        board[6][0] = new Knight(false, new Position(6, 0));

        // Place white knights
        board[1][7] = new Knight(true, new Position(1, 7));
        board[6][7] = new Knight(true, new Position(6, 7));
    }

    private void placeBishops(){
        // Place black bishops
        board[2][0] = new Bishop(false, new Position(2, 0));
        board[5][0] = new Bishop(false, new Position(5, 0));

        // Place white bishops
        board[2][7] = new Bishop(true, new Position(2, 7));
        board[5][7] = new Bishop(true, new Position(5, 7));
    }

    private void placeQueens(){
        // Place black queen
        board[3][0] = new Queen(false, new Position(3, 0));

        // Place white queen
        board[3][7] = new Queen(true, new Position(3, 7));
    }

    private void placeKings(){
        // Place black king
        board[4][0] = new King(false, new Position(4, 0));

        // Place white king
        board[4][7] = new King(true, new Position(4, 7));
    }

    public Piece[][] getBoard(){
        return board;
    }

    public Piece getFigureOnSquare(Position position) {
        return board[position.getPosX()][position.getPosY()];
    }

    public MoveResult movePiece(Position start, Position targetSquare, boolean whiteTurn){
        Piece piece = getFigureOnSquare(start);

        if(piece == null || piece.isWhite() != whiteTurn || MoveResult.INVALID == piece.isValidMove(this, targetSquare)){
            return MoveResult.INVALID;
        }

        if(!checkAfterMove(start, targetSquare)){
            if (PieceType.KING == piece.getPieceType() && MoveResult.CASTLE_KINGSIDE == piece.isValidMove(this, targetSquare)) {
                int posY = piece.getPosition().getPosY();
                Piece rook = getFigureOnSquare(new Position(7, posY));

                rook.setPosition(new Position(5, posY));
                rook.setMoved();

                piece.setPosition(new Position(6, posY));
                piece.setMoved();
                board[start.getPosX()][posY] = null;
                board[7][posY] = null;
                board[5][posY] = rook;
                board[6][posY] = piece;

                return MoveResult.VALID;
            }

            if (PieceType.KING == piece.getPieceType() && MoveResult.CASTLE_QUEENSIDE == piece.isValidMove(this, targetSquare)) {
                int posY = piece.getPosition().getPosY();
                Piece rook = getFigureOnSquare(new Position(0, posY));

                rook.setPosition(new Position(3, posY));
                rook.setMoved();

                piece.setPosition(new Position(2, posY));
                piece.setMoved();

                board[start.getPosX()][posY] = null;
                board[0][posY] = null;
                board[3][posY] = rook;
                board[2][posY] = piece;
                return MoveResult.VALID;
            }

            board[start.getPosX()][start.getPosY()] = null;
            board[targetSquare.getPosX()][targetSquare.getPosY()] = piece;

            piece.setPosition(targetSquare);
            piece.setMoved();

            if(piece.getPieceType() == PieceType.PAWN){
                boolean needPromotion = piece.isWhite() ? piece.getPosition().getPosY() == 0 : piece.getPosition().getPosY() == 7;
                if(needPromotion){
                    return MoveResult.PROMOTION;
                }
            }

            return MoveResult.VALID;
        }
        return MoveResult.INVALID;
    }

    public boolean checkAfterMove(Position start, Position targetSquare){
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

    public Position findKingPosition(boolean isWhite){
        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++) {
                Piece piece = getFigureOnSquare(new Position(i, j));
                if(piece != null && piece.getPieceType() == PieceType.KING && piece.isWhite() == isWhite){
                    return new Position(i, j);
                }
            }
        }
        throw new RuntimeException("King not found which should never happen.");
    }

    public boolean kingInCheck(boolean isWhite){
        Position kingPosition = findKingPosition(isWhite);

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                Piece piece = getFigureOnSquare(new Position(i, j));
                if(piece != null && piece.isWhite() != isWhite && MoveResult.VALID == piece.isValidMove(this, kingPosition)) {
                    return true;
                }
            }
        }
       return false;
    }

    public boolean checkMate(boolean isWhite){
        if(!kingInCheck(isWhite)){
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

    public void promotion(PieceType type, boolean white, int posX, int posY){
        Piece newPiece;
        Position position = new Position(posX, posY);
        switch (type){
            case QUEEN -> newPiece = new Queen(white, position);
            case BISHOP -> newPiece = new Bishop(white, position);
            case KNIGHT -> newPiece = new Knight(white, position);
            case ROOK -> newPiece = new Rook(white, position);
            default -> newPiece = null;
        }
        board[posX][posY] = newPiece;
    }
}