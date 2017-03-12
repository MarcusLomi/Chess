/**
 * @author Marcus Lomi, Daniel Ayoub
 */
package Model;

public class Bishop extends Piece {
	
	public String type = "B";
	
	public Bishop(String color, int row, int col){
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
		//Destination is Up and to the right
		if(row>=this.row&&col>=this.col){
			return this.upperRightCheck(row, col);
		}
		//Destination is up to the left
		else if(row>=this.row&&col<=this.col){
			return this.upperLeftCheck(row, col);
		}
		//Destination is down to right
		else if(row<=this.row&&col>=this.col){
			return this.lowerRightCheck(row, col);
		}
		//Destination is down to left
		else if(row<=this.row&&col<=this.col){
			return this.lowerLeftCheck(row, col);
		}
		return false;
	}
	
}