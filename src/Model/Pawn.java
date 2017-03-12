/**
 * @author Marcus Lomi, Daniel Ayoub
 */
package Model;
import Model.Chessboard;

public class Pawn extends Piece {
	
	private boolean moved;
	
	public Pawn(String color, int row, int col){
		super(color, row, col);
		this.type="p";
		this.moved = false;
	}
	
	public String toString(){
		return this.color+""+this.type;
	}
	
	public String getType(){
		return this.type;
	}
	public boolean hasMoved(){
		return moved;
	}
	public boolean move(int row, int col){
		
		//Saves a pointer to the destination Piece
		Piece dst = Chessboard.board[row][col];
		if(this.color.equals("b")){
			//black pawns move down the board
			if(this.row-row<=0){
				return false;
			}
		}
		else if(this.color.equals("w")){
			//white pawns move up the board
			if(this.row-row>=0){
				return false;
			}
		}
		if(dst==null){
			
			
			// En passant
			if (Math.abs(this.col - col) == 1) {
				if (Chessboard.twoRankPawn != null && Chessboard.twoRankPawn == Chessboard.board[this.row][col]) {	// Found the en passant pawn
					Chessboard.board[this.row][col] = null;
					Chessboard.board[row][col] = this;
					// Moved is true
					return true;
				}
			}
			//Checks that you're moving over at most 1 column
			if(this.col-col!=0){ 	
				return false;											
			}
			if(this.col-col==1||this.col-col==-1){ 	
				//This is a diagonal move
				if(this.color.equals("b")){
					//There needs to be a piece of the opposite color to move
					if(Chessboard.hasPiece("w", row, col)){
						Chessboard.board[row][col]=this;
						moved=true;
						return true;
					}
				}
				else if(this.color.equals("w")){
					if(Chessboard.hasPiece("b", row, col)){
						Chessboard.board[row][col]=this;
						moved=true;
						return true;
					}
					
				}
				return false;
			}
			
			//Allows for moving more than one space at the start position
			if(moved==false){
				if(this.row-row==2||this.row-row==-2||this.row-row==1||this.row-row==-1){
					Chessboard.board[row][col]=this;
					moved=true;
					return true;
				}
			}
			else if(moved==true){
				if(this.row-row==2||this.row-row==-2){
					return false;
				}
				Chessboard.board[row][col]=this;
				moved=true;
				return true;
			}
			
		}
		else if(dst!=null){
			if(Math.abs(this.row-row) == 1 && (this.col-col==1||this.col-col==-1)){ 	
				//This is a diagonal move
				if(this.color.equals("b")){
					//There needs to be a piece of the opposite color to move
					if(dst.getColor().equals("w")){
						Chessboard.board[row][col]=this;
						moved=true;
						return true;
					}
				}
				else if(this.color.equals("w")){
					if(dst.getColor().equals("b")){
						Chessboard.board[row][col]=this;
						moved=true;
						return true;
					}
					
				}
			}
			return false;
		}
		return false;
	}
}