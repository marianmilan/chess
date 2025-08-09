package model.figures;

import model.Board;
import model.Move;
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

    public static void getDiagonalMoves(Board board, Piece piece, List<Move> moves, int numOfSquares){
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

               moves.add(new Move(piece.getPosition(), position, piece, board.getFigureOnSquare(position), false));
           }
       }
    }

    public static void getStraightMoves(Board board, Piece piece, List<Move> moves, int numOfSquares){
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
                moves.add(new Move(piece.getPosition(), position, piece, board.getFigureOnSquare(position), false));
            }
        }
    }

    public static void getCastlingMoves(Board board, Piece piece, List<Move> moves){
        if(piece.getPieceType() != PieceType.KING){
            return;
        }

        int row = piece.getPosition().getPosX();
        if(MoveResult.CASTLE_KINGSIDE == canCastleKingSide(board, piece)){
            Position castlePosition = new Position(row, 6);
            Move move = new Move(piece.getPosition(), castlePosition, piece, null, true);
            Piece rook = board.getFigureOnSquare(new Position(row, 7));
            if (rook == null){
                return;
            }
            move.castleRook = rook;
            move.castleRookPos = rook.getPosition();
            moves.add(move);
        }

        if(MoveResult.CASTLE_QUEENSIDE == canCastleQueenSide(board, piece)){
            Position castlePosition = new Position(row, 2);
            Move move = new Move(piece.getPosition(), castlePosition, piece, null, true);
            Piece rook = board.getFigureOnSquare(new Position(row, 7));
            if (rook == null){
                return;
            }
            move.castleRook = rook;
            move.castleRookPos = rook.getPosition();
            moves.add(move);
        }
    }

    public void testCastle(Board board, Piece piece, List<Move> moves) {
        if(piece.getPieceType() != PieceType.KING) return;
        if(piece.haveMoved()) return;
        int[] kingSide = {5, 6};
        int[] queenSide = {1, 2, 3};
        int row = piece.getPosition().getPosX();

        Piece kingRook = board.getFigureOnSquare(new Position(row, 7));
        Piece queenRook = board.getFigureOnSquare(new Position(row, 0));
        if(kingRook == null || kingRook.haveMoved()) return;
        if(queenRook == null || queenRook.haveMoved()) return;

        boolean canCastleKing = checkMove(board, piece, kingSide, row);
        boolean canCastleQueen = checkMove(board, piece, queenSide, row);

        Position kingCastlePos = new Position(row, 6);
        Position queenCastlePos = new Position(row, 2);

        if(canCastleKing) {
            Move move = new Move(piece.getPosition(), kingCastlePos, piece, null, true);
            move.castleRook = kingRook;
            move.castleRookPos = kingRook.getPosition();
            moves.add(move);
        }

        if(canCastleQueen) {
            Move move = new Move(piece.getPosition(), queenCastlePos, piece, null, true);
            move.castleRook = queenRook;
            move.castleRookPos = queenRook.getPosition();
            moves.add(move);
        }
    }

    public boolean checkMove(Board board, Piece piece, int[] cols, int row) {
        for(int col : cols) {
            Position pos = new Position(row, col);
            Piece square = board.getFigureOnSquare(pos);
            if (square != null) return false;
            if (board.kingInCheck(piece.isWhite())) return false;
            if (board.checkAfterMove(piece.getPosition(), pos)) return false;
        }
        return true;
    }

    public static MoveResult canCastleKingSide(Board board, Piece piece){
        Position start = piece.getPosition();
        int row = start.getPosX();

        Position square1 = new Position(row, 5);
        Position square2 = new Position(row, 6);
        Piece rook = board.getFigureOnSquare(new Position(row, 7));

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
        int row = start.getPosX();

        Position square1 = new Position(row, 1);
        Position square2 = new Position(row, 2);
        Position square3 = new Position(row, 3);
        Piece rook = board.getFigureOnSquare(new Position(row, 0));

        if(board.getFigureOnSquare(square1) != null || board.getFigureOnSquare(square2) != null || board.getFigureOnSquare(square3) != null){
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
    public static List<Move> filterMoves(Board board, Piece piece, List<Move> moves){
        return moves.stream()
                .filter(move -> piece.isValidMove(board, move.to) == MoveResult.VALID)
                .filter(move -> !board.checkAfterMove(move.from, move.to))
                .collect(Collectors.toList());
    }
}

