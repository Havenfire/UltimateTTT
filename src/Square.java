
/*
 *Description: Represents a single square of a tic tac toe board
 */
public class Square {
	
	private String value = "";
	
	public String getValue(){
		//returns "X" or "O", or a blank string if the square is empty
		return value;
	}
	
	//boolean getters for ease of use
	public boolean isX(){
		return value.equals("X");
	}
	
	public boolean isO(){
		return value.equals("O");
	}
	
	public boolean isEmpty(){
		return value.equals("");
	}
	
	public boolean isTaken(){
		return !value.equals("");
	}

	
	//setters
	
	//if the square is empty, sets value to "X" and returns true
	//if the square is taken, does nothing and returns false
	public boolean setX(){
		if (isEmpty()){
			value = "X";
			return true;
		}else{
			return false;
		}
	}
	
	//if the square is empty, sets value to "O" and returns true
	//if the square is taken, does nothing and returns false
	public boolean setO(){
		if (isEmpty()){
			value = "O";
			return true;
		}else{
			return false;
		}
	}
	
	//sets the square to be blank, no matter what
	public void setBlank(){
		value = "";
	}
		
}
