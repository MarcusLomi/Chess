/**
 * @author Marcus Lomi, Daniel Ayoub
 */
package Model;

public class Knight extends Piece {
	
	public String type = "N";
	
	public Knight(String color, int row, int col){
		super(color, row, col);
		this.moved=false;
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
		/*Checks if the input coordinates are one of eight possible moves for the knight*/
		if((row==this.row+2&&col==this.col+1)||
		   (row==this.row+2&&col==this.col-1)||
		   (row==this.row+1&&col==this.col-2)||
		   (row==this.row+1&&col==this.col+2)||
		   (row==this.row-1&&col==this.col+2)||
		   (row==this.row-1&&col==this.col-2)||
		   (row==this.row-2&&col==this.col-1)||
		   (row==this.row-2&&col==this.col+1)){
				Chessboard.board[row][col]=this;
				this.moved=true;
				return true;
		}
		return false;
	}
	
}