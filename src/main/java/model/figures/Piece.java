package model.figures;

import model.Board;
import model.Move;
import model.MoveResult;
import model.Position;

import java.util.List;

public abstract class Piece {

    private final boolean white;
    private boolean moved = false;
    private Position position;

    /**
     * Constructor for piece with specified color and position.
     *
     * @param white true is white, false black
     * @param position starting position on board
     */
    public Piece(boolean white, Position position){
        this.white = white;
        this.position = position;
    }

    public boolean isWhite() {
        return this.white;
    }

    /**
     * Return position for piece.
     * @return piece Position
     */
    public Position getPosition(){
        return this.position;
    }

    public boolean haveMoved(){
        return this.moved;
    }

    /**
     * Set the piece moved flag to true.
     * Indicates that the piece moved.
     */
    public void setMoved(){
        this.moved = true;
    }

    /**
     * Set the piece moved flag to false.
     * This is used in AI's undo move to restore state of the moved flag.
     */
    public void undoMoved() {
        this.moved = false;
    }

    public void setPosition(Position targetSquare) {
        this.position = targetSquare;
    }

    /**
     * Check if the move to the target square is valid for this piece.
     *
     * @param board current game state.
     * @param targetSquare position to move to.
     * @return move result valid/invalid.
     */
    public abstract MoveResult isValidMove(Board board, Position targetSquare);

    /**
     * Generates all the possible moves for piece.
     *
     * @param board current game state
     * @return List of possible moves
     */
    public abstract List<Move> getPossibleMoves(Board board);

    /**
     * Return the type of this piece.
     *
     * @return PieceType of this piece
     */
    public abstract PieceType getPieceType();

    /**
     * Calculate the value of this piece, considering its position on board.
     * This is done with each piece's own position table.
     * @return the value for this piece.
     */
    public abstract int getValue();
}
