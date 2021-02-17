
import java.util.ArrayList;
import java.util.List;

class CheckersGame {
    
	int[][] gameBoard;
	int curPlayer = 1;
    public static final String RED = "\u001B[91m";
    public static final String WHITE = "\u001B[47m";
    public static final String BLACK = "\u001B[30m";
    public static final String RESET = "\u001B[0m";
    
    public CheckersGame() {

    }
    
    public CheckersGame(CheckersGame cg) {
        this.gameBoard = new int[cg.gameBoard.length][cg.gameBoard[0].length];
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                this.gameBoard[i][j] = cg.gameBoard[i][j];
            }
        }
        this.curPlayer = cg.curPlayer;
        
    }
    
    void newBoard() {
        gameBoard = new int[8][8];
        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 8; j += 2) {
                if ((i & 1) == 1) {
                    gameBoard[i][j + 1] = -1;
                    gameBoard[i][j] = 1;
                } else {
                    gameBoard[i][j] = -1;
                    gameBoard[i][j + 1] = 1;
                }
            }
        }

        for (int i = 3; i < 5; i++) {
            for (int j = 0; j < 8; j += 2) {
                if ((i & 1) == 1) {
                    gameBoard[i][j + 1] = -1;
                    gameBoard[i][j] = 0;
                } else {
                    gameBoard[i][j] = -1;
                    gameBoard[i][j + 1] = 0;
                }
            }
        }

        for (int i = 5; i < 8; i++) {
            for (int j = 0; j < 8; j += 2) {
                if ((i & 1) == 1) {
                    gameBoard[i][j + 1] = -1;
                    gameBoard[i][j] = 2;
                } else {
                    gameBoard[i][j] = -1;
                    gameBoard[i][j + 1] = 2;
                }
            }
        }
    }
    void printGameBoard() {
        System.out.println("      0      1      2      3      4      5      6      7   ");
        for (int i = 0; i < 8; i++) {
            System.out.print("   ");
            if ((i & 1) == 0) { 
                for (int j = 0; j < 4; j++) {
                    System.out.print(WHITE + "       " + RESET + "       ");
                }

                System.out.println("");
                System.out.print(" " + (i) + " ");

                for (int j = 0; j < 8; j++) {
                    printPieces(gameBoard[i][j]);
                }

                System.out.println("");
                System.out.print("   ");

                
                for (int j = 0; j < 4; j++) {
                    System.out.print(WHITE + "       " + RESET + "       ");
                }

            } else { 
                for (int j = 0; j < 4; j++) {
                    System.out.print("       " + WHITE + "       " + RESET);
                }

                System.out.println("");
                System.out.print(" " + (i) + " ");

                for (int j = 0; j < 8; j++) {
                    printPieces(gameBoard[i][j]);
                }

                System.out.println("");
                System.out.print("   ");

                
                for (int j = 0; j < 4; j++) {
                    System.out.print("       " + WHITE + "       " + RESET);
                }
            }

            System.out.println("");
        }
    }
    
    void printPieces(int val) {
        switch (val) {
            
            case 1:
                System.out.print(RED + "   P   " + RESET);
                break;
            case 2:
                System.out.print(BLACK + "   P   " + RESET);
                break;
            case 3:
                System.out.print(RED + "   K   " + RESET);
                break;
            case 4:
                System.out.print(BLACK + "   K   " + RESET);
                break;
            case 0:
                System.out.print("       ");
                break;
            case -1:
                System.out.print(WHITE + "       " + RESET);
                break;

        }

    }
   
    List<Move> legalMoves(int[][] state) {
        List<Move> slidingMoveList = new ArrayList<Move>();
        List<Move> jumpingMoveList = new ArrayList<Move>();
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (curPlayer == 1) {
                    if (gameBoard[i][j] == 1 || gameBoard[i][j] == 3) {
                        getJumpMove(jumpingMoveList, null, gameBoard[i][j], i, j, state);
                        
                        if (jumpingMoveList.isEmpty()) {
                            getSlideMove(slidingMoveList, gameBoard[i][j], i, j, gameBoard);
                        }
                    }
                } else {
                    if (gameBoard[i][j] == 2 || gameBoard[i][j] == 4) {
                        getJumpMove(jumpingMoveList, null, gameBoard[i][j], i, j, state);
                        
                        if (jumpingMoveList.isEmpty()) {
                            getSlideMove(slidingMoveList, gameBoard[i][j], i, j, gameBoard);
                        }

                    }
                }
            }
        }
        
        if (jumpingMoveList.isEmpty()) {
        	return slidingMoveList;
        } 
        else {
        	return jumpingMoveList;
        }
    }
    
    void getSlideMove(List<Move> moves, int typeOfPiece, int rowStart, int colStart, int[][] state) {
        ArrayList<Integer> rowEnd = new ArrayList<Integer>();
        ArrayList<Integer> colEnd = new ArrayList<Integer>();

        switch (typeOfPiece) {
            case 1:
                rowEnd.add(rowStart + 1);
                rowEnd.add(rowStart + 1);
                colEnd.add(colStart + 1);
                colEnd.add(colStart - 1);
                break;
            case 2:
                rowEnd.add(rowStart - 1);
                rowEnd.add(rowStart - 1);
                colEnd.add(colStart + 1);
                colEnd.add(colStart - 1);
                break;
            case 4:
                rowEnd.add(rowStart + 1);
                rowEnd.add(rowStart + 1);
                rowEnd.add(rowStart - 1);
                rowEnd.add(rowStart - 1);
                colEnd.add(colStart + 1);
                colEnd.add(colStart - 1);
                colEnd.add(colStart + 1);
                colEnd.add(colStart - 1);
                break;
        }

        int numMoves = rowEnd.size();

        for (int i = 0; i < numMoves; i++) {
            
            if (rowEnd.get(i) < 0 || rowEnd.get(i) > 7 || colEnd.get(i) < 0 || colEnd.get(i) > 7) 
            	continue;
            
            if (state[rowEnd.get(i)][colEnd.get(i)] != 0) 
            	continue;
            
            moves.add(new Move(rowStart, colStart, rowEnd.get(i), colEnd.get(i), state));
        }
    }
    
    void getJumpMove(List<Move> moves, Move move, int typeOfPiece, int rowStart, int colStart, int[][] state) {
        
        ArrayList<Integer> rowEnd = new ArrayList<Integer>();
        ArrayList<Integer> colEnd = new ArrayList<Integer>();
        ArrayList<Integer> captureRow = new ArrayList<Integer>();
        ArrayList<Integer> captureCol = new ArrayList<Integer>();

        switch (typeOfPiece) {
            case 1:
                rowEnd.add(rowStart + 2);
                rowEnd.add(rowStart + 2);
                colEnd.add(colStart - 2);
                colEnd.add(colStart + 2);
                captureRow.add(rowStart + 1);
                captureRow.add(rowStart + 1);
                captureCol.add(colStart - 1);
                captureCol.add(colStart + 1);
                break;
            case 2:
                rowEnd.add(rowStart - 2);
                rowEnd.add(rowStart - 2);
                colEnd.add(colStart - 2);
                colEnd.add(colStart + 2);
                captureRow.add(rowStart - 1);
                captureRow.add(rowStart - 1);
                captureCol.add(colStart - 1);
                captureCol.add(colStart + 1);
                break;
            case 4:
                rowEnd.add(rowStart + 2);
                rowEnd.add(rowStart + 2);
                rowEnd.add(rowStart - 2);
                rowEnd.add(rowStart - 2);
                colEnd.add(colStart - 2);
                colEnd.add(colStart + 2);
                colEnd.add(colStart - 2);
                colEnd.add(colStart + 2);
                captureRow.add(rowStart + 1);
                captureRow.add(rowStart + 1);
                captureRow.add(rowStart - 1);
                captureRow.add(rowStart - 1);
                captureCol.add(colStart - 1);
                captureCol.add(colStart + 1);
                captureCol.add(colStart - 1);
                captureCol.add(colStart + 1);
                break;
        }

        int numMoves = rowEnd.size();
        boolean validMove = false;
        boolean[] validity = new boolean[numMoves];

        for (int i = 0; i < numMoves; i++) {
            
            if (rowEnd.get(i) < 0 || rowEnd.get(i) > 7 || colEnd.get(i) < 0 || colEnd.get(i) > 7) 
            	continue;
            
            if (move != null) {
                
                if (state[rowEnd.get(i)][colEnd.get(i)] != 0 && state[rowEnd.get(i)][colEnd.get(i)] != state[move.rowInitial][move.colInitial]) 
                	continue;
                
                if (move.squaresCaptured.contains(new Pair(captureRow.get(i), captureCol.get(i)))) 
                	continue;
            }
            else {
                
                if (state[rowEnd.get(i)][colEnd.get(i)] != 0) 
                	continue;
            }
            
            if (curPlayer == 1 && !(state[captureRow.get(i)][captureCol.get(i)] == 2 || state[captureRow.get(i)][captureCol.get(i)] == 4)) 
            	continue;

            if (curPlayer == 2 && !(state[captureRow.get(i)][captureCol.get(i)] == 1 || state[captureRow.get(i)][captureCol.get(i)] == 3)) 
            	continue;

            
            validMove = true;
            validity[i] = true;
        }

        if (move != null && !validMove) {
            moves.add(move);
            return;
        }

        if (move == null && validMove) {
            for (int i = 0; i < numMoves; i++) {
                if (validity[i]) {
                    Move newMove = new Move(rowStart, colStart, rowEnd.get(i), colEnd.get(i), state);
                    newMove.rowInitial = rowStart;
                    newMove.colInitial = colStart;
                    newMove.rowStart = rowStart;
                    newMove.colStart = colStart;
                    newMove.rowEnd = rowEnd.get(i);
                    newMove.colEnd = colEnd.get(i);
                    newMove.rowCaptured.add(captureRow.get(i));
                    newMove.colCaptured.add(captureCol.get(i));
                    newMove.rowVisited.add(rowEnd.get(i));
                    newMove.colVisited.add(colEnd.get(i));
                    newMove.squaresCaptured.add(new Pair(captureRow.get(i), captureCol.get(i)));
                    getJumpMove(moves, newMove, typeOfPiece, newMove.rowEnd, newMove.colEnd, state);
                }
            }
        }
        if (move != null && validMove) {
            for (int i = 0; i < numMoves; i++) {
                if (validity[i]) {
                    Move newMove = new Move(move);
                    newMove.rowStart = rowStart;
                    newMove.colStart = colStart;
                    newMove.rowEnd = rowEnd.get(i);
                    newMove.colEnd = colEnd.get(i);
                    newMove.rowCaptured.add(captureRow.get(i));
                    newMove.colCaptured.add(captureCol.get(i));
                    newMove.rowVisited.add(rowEnd.get(i));
                    newMove.colVisited.add(colEnd.get(i));
                    newMove.squaresCaptured.add(new Pair(captureRow.get(i), captureCol.get(i)));
                    getJumpMove(moves, newMove, typeOfPiece, newMove.rowEnd, newMove.colEnd, state);
                }
            }
        }
        return;
    }
    
    void makeMove(Move move, int[][] state) {
        
        if (move.rowCaptured.isEmpty()) {
            
            state[move.rowEnd][move.colEnd] = state[move.rowStart][move.colStart];
            
            if (state[move.rowStart][move.colStart] == 1 && move.rowEnd == 7) {
                state[move.rowEnd][move.colEnd] += 2;
            }
            if (state[move.rowStart][move.colStart] == 2 && move.rowEnd == 0) {
                state[move.rowEnd][move.colEnd] += 2;
            }
            
            state[move.rowStart][move.colStart] = 0;
        }
        
        else {
            
            for (int i = 0; i < move.rowCaptured.size(); i++) {
                state[move.rowCaptured.get(i)][move.colCaptured.get(i)] = 0;
            }
            
            state[move.rowEnd][move.colEnd] = state[move.rowInitial][move.colInitial];
            
            if (state[move.rowInitial][move.colInitial] == 1 && move.rowEnd == 7) {
                state[move.rowEnd][move.colEnd] += 2;
            }
            if (state[move.rowInitial][move.colInitial] == 2 && move.rowEnd == 0) {
                state[move.rowEnd][move.colEnd] += 2;
            }
            
            state[move.rowInitial][move.colInitial] = 0;
        }
        
        curPlayer = curPlayer == 1 ? 2 : 1;

    }
    
    void printListMoves(List<Move> movesList) {
        if (movesList.get(0).rowCaptured.isEmpty()) {
            for (int i = 0; i < movesList.size(); i++) {
                System.out.println("Move " + i + ": " + movesList.get(i).rowStart + "," + movesList.get(i).colStart + " -> " + movesList.get(i).rowEnd + "," + movesList.get(i).colEnd);

            }
        } else {
            for (int i = 0; i < movesList.size(); i++) {
                System.out.println("");
                System.out.print("Move " + i + ": " + movesList.get(i).rowInitial + "," + movesList.get(i).colInitial);
                for (int j = 0; j < movesList.get(i).rowVisited.size(); j++) {
                    System.out.print(" -> " + movesList.get(i).rowVisited.get(j) + "," + movesList.get(i).colVisited.get(j) );
                }
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
    void printMove(Move move) {
        if (move.rowCaptured.isEmpty()){
            System.out.println("Move " + ": " + move.rowStart + "," + move.colStart + " -> " + move.rowEnd + "," + move.colEnd);
        } else {
            System.out.print("Move " + ": " + move.rowInitial + "," + move.colInitial );
            for (int j = 0; j < move.rowVisited.size(); j++) {
                System.out.print(" -> " + move.rowVisited.get(j) + "," + move.colVisited.get(j));
            }
            System.out.println("");
        }
        System.out.println("");
    }
    
}
