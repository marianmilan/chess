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

    public void movePiece(Piece piece, Position targetSquare){
        int targetPosX = targetSquare.getPosX();
        int targetPosY = targetSquare.getPosY();

        int currentPosX = piece.getPosition().getPosX();
        int currentPosY = piece.getPosition().getPosY();

        board[currentPosX][currentPosY] = null;
        board[targetPosX][targetPosY] = piece;

        piece.setMoved();
        piece.setPosition(targetSquare);
    }
}