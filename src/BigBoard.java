import java.util.ArrayList;

/*
 *Description: Represents the full board, made up of nine smaller Boards
 */
public class BigBoard implements Cloneable{
	
	private Board[] boards;
	
	//the board the next move must be made in. a value of 10 means a move can be taken anywhere.
	private int boardToPlay = 10; 
	
	private String winner = "";
	
	//creates an array filled with Boards
	public BigBoard(){
		boards = new Board[9];
		
		for(int i = 0; i < 9; i++)
		{
			boards[i] = new Board();
		}
		boardAt(0).setXTurn();
	}
	
	//pre: the board and square to place a letter.
	//post: places an X or O in the given square based on whose turn it is.
	//If it is in the correct square, a move is made. If a move is made, sets boardToPlay to whatever square was claimed.
	public void takeTurn(int smallBoard, int square){
		
		if(boardToPlay==10 || smallBoard==boardToPlay){
			if(boardAt(smallBoard).takeTurn(square)){
				if(boardAt(square).gameIsWon()) boardToPlay = 10;
				else boardToPlay=square;
			}
		}
		setWinner();
	}
	
	//returns if game is won
	public boolean gameIsWon(){
		return winner != "";
	}
	
	//returns name of the winner
	public String getWinner(){
		return winner;
	}
	
	//Checks for a winner and sets the winner. 
	//Returns true if there is a winner or false if there is not.
	public boolean setWinner(){
		if (!winner.equals("")) return true;
		if(OHasWon()){
			winner = "O";
			return true;
		}
		if(XHasWon()){
			winner = "X";
			return true;
		}
		return false;
	}

	//returns if O has won
	public boolean OHasWon(){
		//check rows
		if(boardAt(0).OHasWon()&&boardAt(1).OHasWon()&&boardAt(2).OHasWon()) return true;
		if(boardAt(3).OHasWon()&&boardAt(4).OHasWon()&&boardAt(5).OHasWon()) return true;
		if(boardAt(6).OHasWon()&&boardAt(7).OHasWon()&&boardAt(8).OHasWon()) return true;
		//check columns
		if(boardAt(0).OHasWon()&&boardAt(3).OHasWon()&&boardAt(6).OHasWon()) return true;
		if(boardAt(1).OHasWon()&&boardAt(4).OHasWon()&&boardAt(7).OHasWon()) return true;
		if(boardAt(2).OHasWon()&&boardAt(5).OHasWon()&&boardAt(8).OHasWon()) return true;
		//check diagonals
		if(boardAt(0).OHasWon()&&boardAt(4).OHasWon()&&boardAt(8).OHasWon()) return true;
		if(boardAt(2).OHasWon()&&boardAt(4).OHasWon()&&boardAt(6).OHasWon()) return true;
		return false; //game not won
	}
	//returns if O has won
	public boolean XHasWon(){
		//check rows
		if(boardAt(0).XHasWon()&&boardAt(1).XHasWon()&&boardAt(2).XHasWon()) return true;
		if(boardAt(3).XHasWon()&&boardAt(4).XHasWon()&&boardAt(5).XHasWon()) return true;
		if(boardAt(6).XHasWon()&&boardAt(7).XHasWon()&&boardAt(8).XHasWon()) return true;
		//check columns
		if(boardAt(0).XHasWon()&&boardAt(3).XHasWon()&&boardAt(6).XHasWon()) return true;
		if(boardAt(1).XHasWon()&&boardAt(4).XHasWon()&&boardAt(7).XHasWon()) return true;
		if(boardAt(2).XHasWon()&&boardAt(5).XHasWon()&&boardAt(8).XHasWon()) return true;
		//check diagonals
		if(boardAt(0).XHasWon()&&boardAt(4).XHasWon()&&boardAt(8).XHasWon()) return true;
		if(boardAt(2).XHasWon()&&boardAt(4).XHasWon()&&boardAt(6).XHasWon()) return true;
		return false; //game not won
	}
	
	//returns the board at the given index.
	public Board boardAt(int index){
		return boards[index];
	}
	

	//sets the boardToplay to num
	public int getBoardToPlay(){
		return boardToPlay;
	}
	
	
	//The following methods are used by the AI
	
	//returns the value of the square at the given board and index
	public String getValue(int boardNum, int index){
		return boardAt(boardNum).squareAt(index).getValue();
	}
	
	//returns an ArrayList of the boards available to play
	public ArrayList<Integer> getAvailableBoards(){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		if(boardToPlay == 10){
			for(int i = 0; i < 9; i++){
				if(!boardAt(i).gameIsWon()) temp.add(i);
			}
		}else{
			temp.add(boardToPlay);
		}
		return temp;
	}
	
	//returns an ArrayList of the squares available to play in a given board.
	//Returns an empty ArrayList if the board is won
		public ArrayList<Integer> getAvailableSquares(int boardNum){
			ArrayList<Integer> temp = new ArrayList<Integer>();
			if(boardAt(boardNum).gameIsWon()) return temp;
			for(int i = 0; i < 9; i++){
				if(getValue(boardNum, i) == "") temp.add(i);
			}
			return temp;
		}
		
	
		//tests the given Board and Square to see if O can win
		public boolean testForWin(int smallBoard, int square){
			if(boardAt(smallBoard).squareAt(square).isTaken()) return false;
			boardAt(smallBoard).squareAt(square).setO();
			boardAt(smallBoard).setWinner();
			boolean isWon = boardAt(smallBoard).gameIsWon();
			boardAt(smallBoard).squareAt(square).setBlank();
			boardAt(smallBoard).setWinner("");
			return isWon;
			
		}
		//tests the given Board and Square to see if X can win
		public boolean testForBlock(int smallBoard, int square){
			if(boardAt(smallBoard).squareAt(square).isTaken()) return false;
			boardAt(smallBoard).squareAt(square).setX();
			boardAt(smallBoard).setWinner();
			boolean isWon = boardAt(smallBoard).gameIsWon();
			boardAt(smallBoard).squareAt(square).setBlank();
			boardAt(smallBoard).setWinner("");
			return isWon;
			
		}
}
