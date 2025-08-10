package logic;

import model.Board;
import model.Move;
import model.MoveResult;
import model.Position;
import model.figures.PieceType;

/**
 * Manages the flow of the game, changing turns.
 * Sends moves, promotions to board object.
 * Handles restarting game.
 */

public class GameManager {
    private Board board;
    private boolean whiteTurn;
    private final ComputerPlayer computerPlayer;

    public GameManager() {
        board = new Board();
        whiteTurn = true;
        computerPlayer = new ComputerPlayer();
    }

    /**
     * Sends the move to board to perform it, get the result back and returns it.
     *
     * @param start starting square on chessboard
     * @param targetSquare target square on chessboard
     * @return move result
     */
    public MoveResult applyMove(Position start, Position targetSquare) {
        MoveResult result = board.movePiece(start, targetSquare, whiteTurn);
        if (board.staleMate(!whiteTurn)) {
            return MoveResult.STALE_MATE;
        }

        if (board.checkMate(!whiteTurn)) {
            return MoveResult.CHECK_MATE;
        }

        // if black turn dont pass Promotion move result - make the promotion and returns valid
        // this is because ai can only promote to queen (if it returns promotion it would prompt player to choose)
        if (!isWhiteTurn() && result == MoveResult.PROMOTION) {
            applyPromotion(PieceType.QUEEN, targetSquare.getPosX(), targetSquare.getPosY());
            return MoveResult.VALID;
        }

        if (result == MoveResult.VALID) {
            whiteTurn = !whiteTurn;
        }
        return result;
    }

    /**
     * Sends promotion to the board to perform it.
     *
     * @param type piece type the pawn will be promoted to
     * @param posX row position on board
     * @param posY column position on board
     */
    public void applyPromotion(PieceType type, int posX, int posY) {
        board.promotion(type, whiteTurn, posX, posY);
        whiteTurn = !whiteTurn;
    }

    public void resetGame() {
        this.board = new Board();
        this.whiteTurn = true;
    }

    public boolean isWhiteTurn() {return whiteTurn;}

    /**
     * Get the computer best move to perform.
     * @return move result
     */
    public MoveResult computerMove() {
        Move move = computerPlayer.findBestMove(board, 3);
        System.out.println("black: " + whiteTurn);
        return applyMove(move.from, move.to);
    }

    public Board getBoard() {
        return this.board;
    }
}
