
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Random;

public class AlphaBeta {
    
    public int maxPlayer;
   
    public int maxDepth;
    
    public int heuristic;
    
    public long startTime;
    public long currentTime;
    
    public int numAlly, numKingsAlly, numOpposite, numOppositeKings;
    
    public AlphaBeta(int maxPlayer, int heuristic) {
        this.maxPlayer = maxPlayer;
        this.heuristic = heuristic;
    }
    
    public Move alphaBetaSearch(CheckersGame cg) {
        
        Date date = new Date();
        startTime = date.getTime();
        
        getStatus(cg);
        
        Random random = new Random();
        int bestMoveVal = 0;
        int depthSearched = 0;
        Move bestMove = null;
        List<Move> bestMoveCurDepth;
        List<Move> moveListLegal = cg.legalMoves(cg.gameBoard);
        
        if (moveListLegal.size() == 1) {
            System.out.println("Depth searched is 0 in 0 seconds.");
            return moveListLegal.get(0);
        }
        
        for (maxDepth = 0; maxDepth < 15; maxDepth++) {
            bestMoveCurDepth = new ArrayList<Move>();
            int bestVal = Integer.MIN_VALUE;
            for (Move move : moveListLegal) {
            	CheckersGame copy = new CheckersGame(cg);
                copy.makeMove(move, copy.gameBoard);
                int min = minVal(copy, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
                
                
                if (min == bestVal) {
                    bestMoveCurDepth.add(move);
                }
                if (min > bestVal) {
                    bestMoveCurDepth.clear();
                    bestMoveCurDepth.add(move);
                    bestVal = min;
                }
                if (bestVal == Integer.MAX_VALUE) break;
            }
            
            int chosenMove = random.nextInt(bestMoveCurDepth.size());
            bestMove = bestMoveCurDepth.get(chosenMove);
            depthSearched = maxDepth;
            bestMoveVal = bestVal;
           
            if (bestMoveVal == Integer.MAX_VALUE) break;
        }
        
        System.out.println("Depth Searched is " + depthSearched + " in " + ((currentTime-startTime)/1000) + " seconds.");
        return bestMove;
    }
    
    public boolean cutOff(int numMoves, int depth) {
        if (numMoves == 0 || depth == maxDepth){
            return true;
        }
        return false;
    }
    
    public int evalFunc(CheckersGame cg) {
    	return evalHeuristic(cg);   
    }
    
    public int evalHeuristic(CheckersGame cg) {
        int numRows = cg.gameBoard.length;
        int numCols = cg.gameBoard[0].length;
        int boardVal = 0;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (maxPlayer == 1){
                    switch(cg.gameBoard[i][j]) {
                        case 1:
                            boardVal = boardVal + 3;
                            break;
                        case 2:
                            boardVal = boardVal - 3;
                            break;
                        case 3:
                            boardVal = boardVal + 5;
                            break;
                        case 4:
                            boardVal = boardVal - 5;
                            break;
                    }
                } else {
                    switch(cg.gameBoard[i][j]) {
                        case 1:
                            boardVal = boardVal - 3;
                            break;
                        case 2:
                            boardVal = boardVal + 3;
                            break;
                        case 3:
                            boardVal = boardVal - 5;
                            break;
                        case 4:
                            boardVal = boardVal + 5;
                            break;
                    }
                }
            }
        }
        return boardVal;
    }
    
    public int maxVal(CheckersGame cg, int alpha, int beta, int depth) {
        
    	Date d = new Date();
    	currentTime = d.getTime();
    	
        List<Move> listLegalMoves = cg.legalMoves(cg.gameBoard);
        if (cutOff(listLegalMoves.size(), depth)) {
            return evalFunc(cg);
        }
        int v = Integer.MIN_VALUE;
        for (Move move: listLegalMoves) {
        	CheckersGame cgCopy = new CheckersGame(cg);
            cgCopy.makeMove(move, cgCopy.gameBoard);
            v = Math.max(v, minVal(cgCopy, alpha, beta, depth + 1));
            if (v >= beta) 
            	return v;
            alpha = Math.max(alpha, v);
        }
        return v;
    }

    public int minVal(CheckersGame cg, int alpha, int beta, int depth) {
        
    	Date d = new Date();
    	currentTime = d.getTime();
    	
    	List<Move> listLegalMoves = cg.legalMoves(cg.gameBoard);
        if (cutOff(listLegalMoves.size(), depth)) {
            return evalFunc(cg);
        }
        int v = Integer.MAX_VALUE;
        for (Move move: listLegalMoves) {
        	CheckersGame cgCopy = new CheckersGame(cg);
            cgCopy.makeMove(move, cgCopy.gameBoard);
            v = Math.min(v, maxVal(cgCopy, alpha, beta, depth + 1));
            if (v <= alpha) 
            	return v;
            beta = Math.min(beta, v);
        }
        return v;
    }
    public void getStatus(CheckersGame cg) {
        int numRows = cg.gameBoard.length;
        int numCols = cg.gameBoard[0].length;
        numAlly = 0;
        numKingsAlly = 0;
        numOpposite = 0;
        numOppositeKings = 0;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (maxPlayer == 1) {
                    switch (cg.gameBoard[i][j]) {
                        case 1:
                            numAlly++;
                            break;
                        case 2:
                            numOpposite++;
                            break;
                        case 3:
                            numKingsAlly++;
                            break;
                        case 4:
                            numOppositeKings++;
                            break;
                    }
                }
                else {
                    switch (cg.gameBoard[i][j]) {
                        case 1:
                            numOpposite++;
                            break;
                        case 2:
                            numAlly++;
                            break;
                        case 3:
                            numOppositeKings++;
                            break;
                        case 4:
                            numKingsAlly++;
                            break;
                    }
                }
            }
        }
    }
}
