package logic;

import model.Board;
import model.Move;
import java.util.List;

public class ComputerPlayer {
   public int minimax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer){
       if(depth == 0 || board.gameOver(!maximizingPlayer)){
           return board.getBoardEval();
       }

       List<Move> moves = board.getAllPossibleMoves(!maximizingPlayer);

       if(maximizingPlayer){
           int maxEval = Integer.MIN_VALUE;
           for(Move move : moves){
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
           for(Move move : moves){
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

   public Move findBestMove(Board board, int depth){
       int bestValue = Integer.MIN_VALUE;
       Move bestMove = null;

       List<Move> legalMoves = board.getAllPossibleMoves(false);

       for(Move move : legalMoves) {
           board.makeMove(move);
           int eval = minimax(board, depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
           board.undoMove();
           if(eval > bestValue) {
               bestValue = eval;
               bestMove = move;
           }
       }
       return bestMove;
   }
}
