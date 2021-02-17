
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;


class MainGame {
    private CheckersGame cg;
    private AlphaBeta player1;
    private AlphaBeta player2;
    private boolean gameOver = false;

    public static void main(String[] args) {
        MainGame mg = new MainGame();
        mg.cg = new CheckersGame();
        mg.player1 = new AlphaBeta(1, 3);
        mg.player2 = new AlphaBeta(2, 3);
        Boolean player1IsComputer = false, player2IsComputer = false;
        Scanner scan = new Scanner(System.in);
        
        System.out.println("Choose player number. Enter 1 or 2. ");
		int player = scan.nextInt();
		if (player == 1) {
			player2IsComputer = true;
			System.out.println("Player 1 is user and Player 2 is AI.");
		}
		else if (player == 2) {
			player1IsComputer = true;
			System.out.println("Player 2 is user and Player 1 is AI.");
		}
        
        mg.cg.newBoard();
        mg.cg.printGameBoard();
        

       
        while (mg.gameOver == false) {
            List<Move> getLegalMoves;
            int moveNumber = -1;
            switch(mg.cg.curPlayer) {
                case 1:
                    getLegalMoves = mg.cg.legalMoves(mg.cg.gameBoard);
                    if (getLegalMoves.isEmpty()) {
                        mg.gameOver = true;
                        System.out.println("Player 2 is the winner!");
                        continue;
                    }
                    System.out.println("Player 1's Turn: ");
                    mg.cg.printListMoves(getLegalMoves);
                    if (player1IsComputer){
                        Move move = mg.player1.alphaBetaSearch(mg.cg);
                        mg.cg.makeMove(move, mg.cg.gameBoard);
                        System.out.print("Player 1 Chose: ");
                        mg.cg.printMove(move);
                    }
                    else {
                        while (moveNumber < 0 || moveNumber >= getLegalMoves.size()) {
                            System.out.println("Choose a move number: ");
                            try {
                                moveNumber = scan.nextInt();
                                scan.nextLine();
                            }
                            catch (InputMismatchException e){
                                System.out.println("Not a valid number!");
                                scan.next();
                            }
                        }
                        mg.cg.makeMove(getLegalMoves.get(moveNumber), mg.cg.gameBoard);
                        System.out.print("Player 1 chose the move: ");
                        mg.cg.printMove(getLegalMoves.get(moveNumber));
                    }
                    
                    mg.cg.printGameBoard();
                    break;
                case 2:
                    getLegalMoves = mg.cg.legalMoves(mg.cg.gameBoard);
                    if (getLegalMoves.isEmpty()) {
                        mg.gameOver = true;
                        System.out.println("Player 1 is the winner!");
                        continue;
                    }
                    System.out.println("Player 2's Turn: ");
                    mg.cg.printListMoves(getLegalMoves);
                    if (player2IsComputer) {
                        Move move = mg.player2.alphaBetaSearch(mg.cg);
                        mg.cg.makeMove(move, mg.cg.gameBoard);
                        System.out.print("Player 2 chose the move: ");
                        mg.cg.printMove(move);
                    }
                    else {
                        while (moveNumber < 0 || moveNumber >= getLegalMoves.size()) {
                            System.out.println("Choose a move number: ");
                            try {
                                moveNumber = scan.nextInt();
                                scan.nextLine();
                            }
                            catch (InputMismatchException e) {
                                System.out.println("Not a valid number!");
                                scan.next();
                            }
                        }
                        mg.cg.makeMove(getLegalMoves.get(moveNumber), mg.cg.gameBoard);
                        System.out.print("Player 2 chose the move: ");
                        mg.cg.printMove(getLegalMoves.get(moveNumber));
                    }
                    

            }
        }
    }
}
