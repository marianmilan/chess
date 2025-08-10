package logic;

import model.Board;
import model.Move;
import java.util.List;

/**
 * Class for a computer made move.
 * Uses minimax algorithm with alpha beta prunning for optimized search.
 */

public class ComputerPlayer {
    /**
     * Implements minimax algorithm with alpha-beta prunning to evaluate chess positions.
     * Recursively searches moves to a given depth.
     * Alpha and beta values are used to prune branches that don't affect the result.
     *
     * @param board current game state.
     * @param depth how deep to search.
     * @param alpha best value for maximizing player so far.
     * @param beta best value for minimizing player so far.
     * @param maximizingPlayer from which perspective to search for.
     * @return evaluation of board from maximizing player perspective.
     */
   public int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer) {
       if (depth == 0 || board.gameOver(!maximizingPlayer)) {
           return board.getBoardEval();
       }

       List<Move> moves = board.getAllPossibleMoves(!maximizingPlayer);

       if (maximizingPlayer) {
           int maxEval = Integer.MIN_VALUE;
           for (Move move : moves) {
               board.makeMove(move);
               int eval = minimax(board, depth - 1, alpha, beta, false);
               board.undoMove();
               maxEval = Math.max(maxEval, eval);
               alpha = Math.max(alpha, eval);
               if(beta <= alpha) break;
           }
           return maxEval;
       } else {
           int minEval = Integer.MAX_VALUE;
           for (Move move : moves) {
               board.makeMove(move);
               int eval = minimax(board, depth - 1, alpha, beta, true);
               board.undoMove();
               minEval = Math.min(minEval, eval);
               beta = Math.min(beta, eval);
               if(beta <= alpha) break;
           }
           return minEval;
       }
   }
    /**
     * Finds the best possible move for a player by searching to a given depth. (currently only for black)
     *
     * @param board current game state.
     * @param depth how deep to search.
     * @return best possible move.
     */
   public Move findBestMove(Board board, int depth) {
       int bestValue = Integer.MIN_VALUE;
       Move bestMove = null;

       List<Move> legalMoves = board.getAllPossibleMoves(false);

       for (Move move : legalMoves) {
           board.makeMove(move);
           int eval = minimax(board, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
           board.undoMove();
           if (eval > bestValue) {
               bestValue = eval;
               bestMove = move;
           }
       }
       return bestMove;
   }
}
