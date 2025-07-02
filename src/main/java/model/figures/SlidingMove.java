package model.figures;

import model.Board;
import model.Position;

public class SlidingMove {

    public static boolean isDiagonalValid(Board board, Figure startPiece, Position targetSquare) {
        // Move to the same square is not allowed
        if (startPiece.getPosition() == targetSquare) {
            return false;
        }

        Position start = startPiece.getPosition();

        int rowDiff = start.yAbsPosDifference(targetSquare);
        int colDiff = start.xAbsPosDifference(targetSquare);

        // calculate directions for diagonal moves
        int rowDirection = targetSquare.getPosY() > start.getPosY() ? 1 : -1;
        int colDirection = targetSquare.getPosX() > start.getPosX() ? 1 : -1;

        // Not a diagonal move - return
        if (rowDiff != colDiff) {
            return false;
        }

        int thisRow = start.getPosY();
        int thisCol = start.getPosX();

        // check if the path is blocked
        for (int i = 1; i < rowDiff; i++) {
            int xCor = thisCol + i * colDirection;
            int yCor = thisRow + i * rowDirection;

            if (board.getFigureOnSquare(new Position(xCor, yCor)) != null) {
                return false;
            }
        }

        return isValidTarget(board, startPiece, targetSquare);
    }

    public static boolean isStraightValid(Board board, Figure startPiece, Position targetSquare) {
        Position start = startPiece.getPosition();

        int colDiff = start.xAbsPosDifference(targetSquare);
        int rowDiff = start.yAbsPosDifference(targetSquare);

        int colDirection = targetSquare.getPosX() > start.getPosX() ? 1 : -1;
        int rowDirection = targetSquare.getPosY() > start.getPosY() ? 1 : -1;

        // one value must be 0 to be a valid straight move
        if (rowDiff != 0 && colDiff != 0) {
            return false;
        }


        Figure target = board.getFigureOnSquare(targetSquare);

        // Check for the vertical move
        if (start.getPosX() == targetSquare.getPosX()) {
            for (int i = 1; i < rowDiff; i++) {
                int yCor = start.getPosY() + i * rowDirection;
                if (board.getFigureOnSquare(new Position(start.getPosX(), yCor)) != null) {
                    return false;
                }
            }
            return isValidTarget(board, startPiece, targetSquare);
        }

        // Check for a horizontal move
        if (start.getPosY() == targetSquare.getPosY()) {
            for (int i = 1; i < colDiff; i++) {
                int xCor = start.getPosX() + i * colDirection;
                if (board.getFigureOnSquare(new Position(xCor, start.getPosY())) != null) {
                    return false;
                }
            }
            return isValidTarget(board, startPiece, targetSquare);
        }
        return false;
    }

    // return true if the target square is null or a piece that can be captured
    private static boolean isValidTarget(Board board, Figure piece, Position targetSquare) {
        Figure target = board.getFigureOnSquare(targetSquare);
        return target == null || target.isWhite() != piece.isWhite();
    }
}
