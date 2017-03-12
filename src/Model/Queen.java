/**
 * @author Marcus Lomi, Daniel Ayoub
 */
package Model;

public class Queen extends Piece {
	
	public String type = "Q";
	
	public Queen(String color, int row, int col){
		//super(color, row, col);
		this.moved=false;
	}
	public String toString(){
		return this.color+""+this.type;
	}
	public String getType(){
		return this.type;
	}
	public boolean move(int row,int col ){
		
		/*The move method iterates square by square to see if the destination square is
		 * in the correct path of movement, with no pieces blocking it. */
		Piece dst = Chessboard.board[row][col];
		if(dst!=null){
			if(dst.getColor().equals(this.getColor())||dst.getType().equals("K")){
				return false;
			}
		}
		
		/*When the piece is moved up a column vertically
		 * Iterates row by row*/
		if(this.col==col){
			if(row>this.row){
				return this.upperVertCheck(row, col);
			}
			else if(row<this.row){
				return this.lowerVertCheck(row, col);
			}
			return false;		
		}//End Vertical Check
		
		/*When the queen stays in the same row, moving horizontally.
		 * Iterates column by column */ 
		else if(this.row==row){
			if(col>this.col){	//Iterates through the columns to the right if the destination is to the right
				return this.rightHorizontalCheck(row, col);
			}		
			else if(col<this.col){	//Iterates left through columns if the destination is to the left 
				return this.leftHorizontalCheck(row, col);
			}
			return false;		//return false if either of the loops complete	
		}//End Horizontal Check
		
		/*If the move isn't strictly horizontal or vertical it is diagonal
		 * This checks validity of Diagonal moves.*/
		else{
			//Destination is Up and to the right
			if(row>=this.row&&col>=this.col){
				return this.upperRightCheck(row, col);
			}
			//Destination is up to the left
			if(row>=this.row&&col<=this.col){
				return this.upperLeftCheck(row, col);
			}
			//Destination is down to right
			if(row<=this.row&&col>=this.col){
				return this.lowerRightCheck(row, col);
			}
			//Destination is down to left
			if(row<=this.row&&col<=this.col){
				return this.lowerLeftCheck(row, col);
			}
			
		}
		
		return false;
	}

}
