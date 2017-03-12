/**
 * @author Marcus Lomi, Daniel Ayoub
 */
package Model;

public class Rook extends Piece {
	
	public String type = "R";

	
	public Rook(String color, int row, int col){
		super(color, row, col);
	}
	public String toString(){
		return this.color+""+this.type;
	}
	public String getType(){
		return this.type;
	}
	public boolean move(int row, int col){
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
		
		return false;
	}
	
}