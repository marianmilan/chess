package model.figures;

import model.Board;
import model.MoveResult;
import model.Position;

import java.util.List;
import java.util.stream.Collectors;


/*
 * This class is helper class to reuse code for diagonal and sliding pieces
 * and to check if the move is within bounds of the board which all pieces use
 */

public class MoveHelper {

    public static MoveResult isDiagonalValid(Board board, Piece startPiece, Position targetSquare) {
        Position start = startPiece.getPosition();

        int rowDiff = start.yAbsPosDifference(targetSquare);
        int colDiff = start.xAbsPosDifference(targetSquare);

        // calculate directions for diagonal moves
        int rowDirection = targetSquare.getPosY() > start.getPosY() ? 1 : -1;
        int colDirection = targetSquare.getPosX() > start.getPosX() ? 1 : -1;

        // Not a diagonal move - return
        if (rowDiff != colDiff) {
            return MoveResult.INVALID;
        }

        int thisRow = start.getPosY();
        int thisCol = start.getPosX();

        // check if the path is blocked
        for (int i = 1; i < rowDiff; i++) {
            int xCor = thisCol + i * colDirection;
            int yCor = thisRow + i * rowDirection;

            if (board.getFigureOnSquare(new Position(xCor, yCor)) != null) {
                return MoveResult.INVALID;
            }
        }

        return isValidTarget(board, startPiece, targetSquare);
    }

    public static MoveResult isStraightValid(Board board, Piece startPiece, Position targetSquare) {
        Position start = startPiece.getPosition();

        int colDiff = start.xAbsPosDifference(targetSquare);
        int rowDiff = start.yAbsPosDifference(targetSquare);

        int colDirection = targetSquare.getPosX() > start.getPosX() ? 1 : -1;
        int rowDirection = targetSquare.getPosY() > start.getPosY() ? 1 : -1;

        // one value must be 0 to be a valid straight move
        if (rowDiff != 0 && colDiff != 0) {
            return MoveResult.INVALID;
        }


        Piece target = board.getFigureOnSquare(targetSquare);

        // Check for the vertical move
        if (start.getPosX() == targetSquare.getPosX()) {
            for (int i = 1; i < rowDiff; i++) {
                int yCor = start.getPosY() + i * rowDirection;
                if (board.getFigureOnSquare(new Position(start.getPosX(), yCor)) != null) {
                    return MoveResult.INVALID;
                }
            }
            return isValidTarget(board, startPiece, targetSquare);
        }

        // Check for a horizontal move
        if (start.getPosY() == targetSquare.getPosY()) {
            for (int i = 1; i < colDiff; i++) {
                int xCor = start.getPosX() + i * colDirection;
                if (board.getFigureOnSquare(new Position(xCor, start.getPosY())) != null) {
                    return MoveResult.INVALID;
                }
            }
            return isValidTarget(board, startPiece, targetSquare);
        }
        return MoveResult.INVALID;
    }

    // return true if the target square is null or a piece that can be captured
    public static MoveResult isValidTarget(Board board, Piece piece, Position targetSquare) {
        Piece target = board.getFigureOnSquare(targetSquare);
        if(target == null || target.isWhite() != piece.isWhite()){
            return MoveResult.VALID;
        }
        return MoveResult.INVALID;
    }

    // return true if the move is withing board range and not the same start and end position
    public static MoveResult isWithinBounds(Board board, Piece piece, Position targetSquare){
        Position start = piece.getPosition();

        int xPos = targetSquare.getPosX();
        int yPos = targetSquare.getPosY();

        if(!start.equals(targetSquare) && xPos <= 7 && xPos >= 0 && yPos <= 7 && yPos >= 0) {
            return  MoveResult.VALID;
        }
        return MoveResult.INVALID;
    }

    public static void getDiagonalMoves(Board board, Piece piece, List<Position> moves, int numOfSquares){
       int[][] diagonal = {
               {-1, -1}, {-1, 1}, {1, -1}, {1, 1}
       };

       int currentPosX = piece.getPosition().getPosX();
       int currentPosY = piece.getPosition().getPosY();

       for (int[] dir : diagonal){
           int dirX = dir[0];
           int dirY = dir[1];

           for(int i = 1; i <= numOfSquares; i++){
               Position position = new Position(i * dirX + currentPosX, i * dirY + currentPosY);

               if(MoveResult.INVALID == isWithinBounds(board, piece, position)){
                   break;
               }

               if(MoveResult.INVALID == piece.isValidMove(board, position)){
                   break;
               }

               moves.add(position);
           }
       }
    }

    public static void getStraightMoves(Board board, Piece piece, List<Position> moves, int numOfSquares){
        int[][] straight = {
                {0, -1}, {0, 1}, {-1, 0}, {1, 0}
        };

        int currentPosX = piece.getPosition().getPosX();
        int currentPosY = piece.getPosition().getPosY();

        for (int[] dir : straight){
            int dirX = dir[0];
            int dirY = dir[1];

            for(int i = 1; i <= numOfSquares; i++){
                Position position = new Position(i * dirX + currentPosX, i * dirY + currentPosY);

                if(MoveResult.INVALID == isWithinBounds(board, piece, position)){
                    break;
                }

                if(MoveResult.INVALID == piece.isValidMove(board, position)){
                    break;
                }
                moves.add(position);
            }
        }
    }

    public static void getCastlingMoves(Board board, Piece piece, List<Position> moves){
        if(piece.getPieceType() != PieceType.KING){
            return;
        }

        if(MoveResult.CASTLE_KINGSIDE == canCastleKingSide(board, piece)){
            moves.add(new Position(6, piece.getPosition().getPosY()));
        }

        if(MoveResult.CASTLE_QUEENSIDE == canCastleQueenSide(board, piece)){
            moves.add(new Position(2, piece.getPosition().getPosY()));
        }
    }

    public static MoveResult canCastleKingSide(Board board, Piece piece){
        Position start = piece.getPosition();
        Position square1 = new Position(5, piece.getPosition().getPosY());
        Position square2 = new Position(6, piece.getPosition().getPosY());
        Piece rook = board.getFigureOnSquare(new Position(7, piece.getPosition().getPosY()));

        if(board.getFigureOnSquare(square1) != null || board.getFigureOnSquare(square2) != null){
            return MoveResult.INVALID;
        }

        if(rook == null || rook.haveMoved() || rook.isWhite() != piece.isWhite() || rook.getPieceType() != PieceType.ROOK){
            return MoveResult.INVALID;
        }


        if(board.kingInCheck(piece.isWhite())) {return MoveResult.INVALID;}
        if(board.checkAfterMove(start, square1)) {return MoveResult.INVALID;}
        if(board.checkAfterMove(start, square2)) {return MoveResult.INVALID;}

        return MoveResult.CASTLE_KINGSIDE;
    }

    public static MoveResult canCastleQueenSide(Board board, Piece piece){
        Position start = piece.getPosition();
        Position square1 = new Position(3, piece.getPosition().getPosY());
        Position square2 = new Position(2, piece.getPosition().getPosY());
        Piece rook = board.getFigureOnSquare(new Position(0, piece.getPosition().getPosY()));

        if(board.getFigureOnSquare(square1) != null || board.getFigureOnSquare(square2) != null){
            return MoveResult.INVALID;
        }

        if(rook == null || rook.haveMoved() || rook.isWhite() != piece.isWhite() || rook.getPieceType() != PieceType.ROOK){
            return MoveResult.INVALID;
        }

        if(board.checkAfterMove(start, start)) {return MoveResult.INVALID;}
        if(board.checkAfterMove(start, square1)) {return MoveResult.INVALID;}
        if(board.checkAfterMove(start, square2)) {return MoveResult.INVALID;}

        return MoveResult.CASTLE_QUEENSIDE;
    }

    // helper method to filter valid moves and moves that will prevent or will not lead to check
    public static List<Position> filterMoves(Board board, Piece piece, List<Position> moves){
        return moves.stream()
                .filter(position -> piece.isValidMove(board, position) == MoveResult.VALID)
                .filter(position -> !board.checkAfterMove(piece.getPosition(), position))
                .collect(Collectors.toList());
    }
}

