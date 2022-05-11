
/*
 *Description: Represents a single tic tac toe board, made up of 9 squares.
 */
public class Board {
	
	private Square[] squares = new Square[9];
	private static boolean XTurn = true;
	private String winner = "";
	
	public Board(){
		//constructs a board with 9 squares
		for (int i=0; i<9; i++){
			squares[i] = new Square();
		}
	}
	
	//returns the square at the given index
	public Square squareAt(int index){
		return squares[index];
	}
	
	//returns true if it is X's turn or false if it is O's turn
	public boolean isXTurn(){
		return XTurn;
	}
	
	//returns true if it is O's turn or false if it is X's turn
	public boolean isOTurn(){
		return !XTurn;
	}
	
	//prints the current state of the board to the console
	public void printBoard(){
		for(int i=0; i<9; i+=3){
			for (int j=0; j<3; j++){
				if (squareAt(i+j).isEmpty()){
					System.out.print("_ ");
				}else{
					System.out.print(squareAt(i+j).getValue()+" ");
				}				
			}
			System.out.println("");
		}
	}
	//sets the square at the given index to X or O or blank
	public boolean setX(int index){
		return squares[index].setX();
	}
	public boolean setO(int index){
		return squares[index].setO();
	}
	public void setBlank(int index){
		squares[index].setBlank();
	}
	
	//pre: the square to place a letter
	//post: places an X or O in the given square based on whose turn it is.
	//Returns false if the square is taken or the game is won, returns true if a move is made.
	public boolean takeTurn(int index){
		if(gameIsWon())return false;
		if (squareAt(index).isTaken()) return false;
		if(isXTurn()){
			setX(index);
		}else{
			setO(index);
		}
		XTurn= !XTurn;
		setWinner();
		return true;
	}
	
		
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
	
	//sets the winner to newWinner
	public boolean setWinner(String newWinner){
		winner = newWinner;
		return true;
	}
	
	//returns if game is won
	public boolean gameIsWon(){
		return winner != "";
	}
	
	//returns if O has won
	public boolean OHasWon(){
		//check rows
		if(squareAt(0).isO()&&squareAt(1).isO()&&squareAt(2).isO()) return true;
		if(squareAt(3).isO()&&squareAt(4).isO()&&squareAt(5).isO()) return true;
		if(squareAt(6).isO()&&squareAt(7).isO()&&squareAt(8).isO()) return true;
		//check columns
		if(squareAt(0).isO()&&squareAt(3).isO()&&squareAt(6).isO()) return true;
		if(squareAt(1).isO()&&squareAt(4).isO()&&squareAt(7).isO()) return true;
		if(squareAt(2).isO()&&squareAt(5).isO()&&squareAt(8).isO()) return true;
		//check diagonals
		if(squareAt(0).isO()&&squareAt(4).isO()&&squareAt(8).isO()) return true;
		if(squareAt(2).isO()&&squareAt(4).isO()&&squareAt(6).isO()) return true;
		return false; //game not won
	}
	//returns if X has won
	public boolean XHasWon(){
		//check rows
		if(squareAt(0).isX()&&squareAt(1).isX()&&squareAt(2).isX()) return true;
		if(squareAt(3).isX()&&squareAt(4).isX()&&squareAt(5).isX()) return true;
		if(squareAt(6).isX()&&squareAt(7).isX()&&squareAt(8).isX()) return true;
		//check columns
		if(squareAt(0).isX()&&squareAt(3).isX()&&squareAt(6).isX()) return true;
		if(squareAt(1).isX()&&squareAt(4).isX()&&squareAt(7).isX()) return true;
		if(squareAt(2).isX()&&squareAt(5).isX()&&squareAt(8).isX()) return true;
		//check diagonals
		if(squareAt(0).isX()&&squareAt(4).isX()&&squareAt(8).isX()) return true;
		if(squareAt(2).isX()&&squareAt(4).isX()&&squareAt(6).isX()) return true;
		return false; //game not won
	}
	
	//sets XTurn to be true. Used to restart the game.
	public void setXTurn(){
		XTurn = true;
	}
		
	
}
