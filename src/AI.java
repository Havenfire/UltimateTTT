import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/*
 *Description: An Artificial Intelligence with varying difficulty levels, for a one player game.
 */

public class AI {
	
	private BigBoard board;
	private int difficulty;
	
	public AI(BigBoard newBoard, int newDifficulty){
		board = newBoard;
		difficulty = newDifficulty;
	}
	
	//returns difficulty
	public int getDifficulty(){
		return difficulty;
	}
	
	//Calls a helper method based on the difficulty of the bot.
	//Easy difficulty plays randomly, Hard plays with good strategy, and Medium picks randomly between Easy and Hard every turn.
	public boolean play(){
		
		if(board.boardAt(0).isOTurn()){
			switch(difficulty){
			case 0: playRandom();
			break;
			case 1: if((int)(Math.random()*2)==0)playRandom(); //pick random between random and hard
					else playHard();
			break;
			case 2: playHard();
			break;
			default: System.out.println("What are you doing?");
			break;
			}
			return true;
		}
		return false;
	}

	
	//plays as O in a random board and a random square
	private void playRandom() {
			ArrayList<Integer> availableBoards = board.getAvailableBoards();
			int boardNum = (int)(Math.random()*availableBoards.size());
			boardNum = availableBoards.get(boardNum);
			ArrayList<Integer> availableSquares = board.getAvailableSquares(boardNum);
			int squareNum = (int)(Math.random()*availableSquares.size());
			squareNum = availableSquares.get(squareNum);
			
			board.takeTurn(boardNum, squareNum);
		
	}
	
	/* An AI with good strategy
	 * Wins the game if possible, checking for a win in the "best" boards first.
	 * If it cannot win, it will block X is possible, again checking the best boards first
	 * If it cannot win or block, it will play in the best possible board
	 * It will not play in a square that sends X to a board where X can win, if possible
	 * It chooses randomly from the squares that satisfy this constraint.
	 */
	private void playHard() {
		ArrayList<Integer> availableBoards = board.getAvailableBoards();
		availableBoards = sortBest(availableBoards);
		
		for(int i:availableBoards){    //Win if possible
			ArrayList<Integer> availableSquares = board.getAvailableSquares(i);
			for(int j:availableSquares){
					if(board.testForWin(i, j)){
						board.takeTurn(i, j);
						return;
					}
			}		
		}
		for(int i:availableBoards){   //Block if possible
			ArrayList<Integer> availableSquares = board.getAvailableSquares(i);
			for(int j:availableSquares){
					if(board.testForBlock(i, j)){
						board.takeTurn(i, j);
						return;
					}
			}		
		}
		
		int boardNum = availableBoards.get(0);		//play in the best board
		ArrayList<Integer> availableSquares = board.getAvailableSquares(boardNum);
		int squareNum = (int)(Math.random()*availableSquares.size());
		squareNum = availableSquares.get(squareNum);
		boolean isWinnable = true;
		int dontBeAnInfiniteLoop=0;
		while(isWinnable && dontBeAnInfiniteLoop<20){ //Don't send X to a square X can win, if possible
			
			isWinnable=false;
			for(int j = 0; j<9; j++){
				if(board.testForBlock(squareNum, j)) isWinnable = true;
			}
			if(isWinnable){
				squareNum = (int)(Math.random()*availableSquares.size());
				squareNum = availableSquares.get(squareNum);
			}
			dontBeAnInfiniteLoop++;
		}
		
		
		board.takeTurn(boardNum, squareNum);
	}
	
	//Sorts an ArrayList to put the numbers of the "best" boards first
	//The best board is the middle, then the corners, and finally the edges.
	private ArrayList<Integer> sortBest(ArrayList<Integer> list){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		int[] best = {4,0,2,6,8,1,3,5,7};
		for(int i = 0; i< 9; i++){
			if(list.contains(best[i])) temp.add(best[i]);
		}
		return temp;
	}
}
