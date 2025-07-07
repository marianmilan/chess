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
            board[6][i] = new Pawn(true, new Position(6, i));
            board[1][i] = new Pawn(false, new Position(1, i));
        }
    }

    private void placeRooks(){
        // Place black rooks
        board[0][0] = new Rook(false, new Position(0,0));
        board[0][7] = new Rook(false, new Position(0, 7));

        // Place white rooks
        board[7][0] = new Rook(true, new Position(7, 0));
        board[7][7] = new Rook(true, new Position(7, 7));
    }

    private void placeKnights(){
        // Place black knights
        board[0][1] = new Knight(false, new Position(0,1));
        board[0][6] = new Knight(false, new Position(0, 6));

        // Place white knights
        board[7][1] = new Knight(true, new Position(7, 1));
        board[7][6] = new Knight(true, new Position(7, 6));
    }

    private void placeBishops(){
        // Place black bishops
        board[0][2] = new Bishop(false, new Position(0, 2));
        board[0][5] = new Bishop(false, new Position(0, 5));

        // Place white bishops
        board[7][2] = new Bishop(true, new Position(7, 2));
        board[7][5] = new Bishop(true, new Position(7, 5));
    }

    private void placeQueens(){
        // Place black queen
        board[0][3] = new Queen(false, new Position(0, 3));

        // Place white queen
        board[7][3] = new Queen(true, new Position(7, 3));
    }

    private void placeKings(){
        // Place black king
        board[0][4] = new King(false, new Position(0, 4));

        // Place white king
        board[7][4] = new King(true, new Position(7, 4));
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