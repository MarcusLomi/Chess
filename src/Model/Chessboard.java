/**
 * @author Marcus Lomi, Daniel Ayoub
 */
package Model;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import Chess.Chess;
public class Chessboard {
	
	/**
	 * The two-dimensional array storing the locations of Pieces
	 * on the board
	 */
	public static Piece[][] board = new Piece[9][8];
	/**
	 * Convenience pointer to black's king
	 */
	public static	King blackKing;
	/**
	 * Convenience pointer to white's king
	 */
	public static	King whiteKing;
	/**
	 * Pointer to the pawn that has just moved two spaces forward in the previous turn
	 */
	public static	Pawn twoRankPawn;
	/**
	 * The Class that a Pawn should be promoted to should it
	 * reach the other side of the board
	 */
	public static	Class<?> promotionType = Queen.class;
	public Chessboard(){
		
		for(int r=0;r<9;r++){
			
			for(int c=0;c<8;c++){
				if(r==2){
					board[r][c]= new Pawn("w", r, c);
				}
				if(r==1){
					if(c==2||c==5){
						board[r][c]=new Bishop("w", r, c);
					}
					if(c==3){
						board[r][c]=new Queen("w", r, c);
					}
					if(c==4){
						board[r][c]=new King("w", r, c);
						whiteKing=(King)board[r][c];
					}
					if(c==1||c==6){
						board[r][c]=new Knight("w", r, c);
					}
					if(c==0||c==7){
						board[r][c]=new Rook("w", r, c);
					}
				}
				if(r==8){
					if(c==0||c==7){
						board[r][c]=new Rook("b", r, c);
					}
					if(c==4){
						board[r][c]= new King("b", r, c);
						blackKing=(King)board[r][c];
					}
					if(c==1||c==6){
						board[r][c]=new Knight("b", r, c);
					}
					if(c==2||c==5){
						board[r][c]=new Bishop("b", r, c);
					}
					if(c==3){
						board[r][c]=new Queen("b", r, c);
					}
				}
				else if(r==7){
					board[r][c]=new Pawn("b", r, c);
				}
				
			}// end column for 
			
		}//end row for
		
	}
	
	/** 
	 * @param row row of the spot being checked
	 * @param col column of the spot being checked
	 * @return true/false if a piece is on the selected spot.
	 * 			If out of bounds, returns false
	 */
	public static boolean hasPiece(int row, int col){
		if(col>7||row<1||row>8||col<0){
			return false;
		}
		if(board[row][col]!=null){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param col column	
	 * @param row row
	 * @return pointer to the piece at the location specified
	 */
	public static Piece getPiece(int row, int col){
		if(row<1||row>8||col<0||col>7){
			return null;
		}
		return board[row][col];
	}
	
	/**
	 * 
	 * @param t type of the indended piece on the spot being checked
	 * @param row row of the spot being checked
	 * @param col column of the spot being checked
	 * @return true/false if a piece is on the selected spot
	 * 			and if it is of the selected type
	 */
	public static boolean hasPiece(String t, int row, int col){
		if(board[row][col]!=null){
			return false;
		}
		if(board[row][col].getType().equals(t)){
			return true;
		}
		
		return false;
	}
	
	/**
	 * Prints board
	 */
	public void printboard(){
		
		//4System.out.println();
		String[] letters={"a","b","c","d","e","f","g","h"};
		
		for(int r=8;r>=0;r--){
			System.out.println();
			for(int c=0;c<8;c++){
				
				/*Prints the letters below the board*/
				if(r==0&&c!=8){
					System.out.print(" "+letters[c]+" ");
				}
				
				/*Prints the Checkered pattern*/
				else if(board[r][c]==null){		
					
					if(r%2==1){
						if(c%2==0){
							System.out.print("## ");
						}
						else{
							System.out.print("   ");
						}
					}
					else if(r%2==0){
						if(c%2==1){
							System.out.print("## ");
						}
						else{
							System.out.print("   ");
						}
					}	
				}
				
				/*prints the board piece in its current position*/ 
				if(board[r][c]!=null){		
					System.out.print(board[r][c]+" ");
				}
				
				/*prints the numbers for rows*/
				if(c==7&&r!=0){				
					System.out.print(r);
				}
				
			}// end column for loop
			
		}//end row for loop
		System.out.println();
		
	}
	
	/**
	 * Promotes a pawn that has reached rank 8
	 * @param r the row occupied by the pawn
	 * @param c the column occupied by the pawn
	 */
	private static void promote(int r, int c) {
		Pawn pawn = (Pawn) board[r][c];
		Constructor<?> pieceConstructor = null;
		try {
			pieceConstructor = promotionType.getConstructor(String.class, int.class, int.class);
		} catch (Exception e) {
			e.printStackTrace(); 	// This should never happen
		}
		try {
			board[r][c] = (Piece) pieceConstructor.newInstance(pawn.color, r, c);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();	// This should never happen
		}
		promotionType = Queen.class;
	}
	
	/**
	 * 
	 * @param r1 row of the piece being moved
	 * @param c1 column of the piece being moved
	 * @param r2 destination row of the piece being moved
	 * @param c2 destination row of the piece being moved
	 * @throws IllegalMoveException
	 * 			if the piece's move method returns false, 
	 * 			the move is illegal and an exception is thrown.
	 */
	public void move(int r1, int c1, int r2, int c2) throws IllegalMoveException{
		if(r2<1||r2>8||c2>7||c2<0){
			throw new IllegalMoveException("Illegal move, try again");
		}
		if(r1<1||r1>8||c1>7||c1<0){
			throw new IllegalMoveException("Illegal move, try again");
		}
		if(this.hasPiece(r1, c1)){
			/*Gets the color of the piece you want to move, check's that it
				is the same color as the player. */
			if(Chess.whiteTurn){
				if(this.getPiece(r1, c1).getColor().equals("b")){
					throw new IllegalMoveException("Illegal move, try again");
				}
			}
			if(Chess.blackTurn){
				if(this.getPiece(r1, c1).getColor().equals("w")){
					throw new IllegalMoveException("Illegal move, try again");
				}
			}
			
			Piece mover = board[r1][c1];
			Piece originalPiece = board[r2][c2];			
			
			if(board[r1][c1].move(r2, c2)){
				board[r2][c2].row=r2;		//Updates the piece's current row position
				board[r2][c2].col=c2;		//Updates the piece's current column position
				board[r1][c1]=null;
				if (board[r2][c2] instanceof Pawn && Math.abs(r2-r1) == 2) { // En passant stuff
					this.twoRankPawn = (Pawn) board[r2][c2];	// Keep track of the pawn that just moved two
				} else {
					this.twoRankPawn = null;					// Unset the pointer after any other move
				}
				if (board[r2][c2] instanceof Pawn) {			// Promotion stuff
					Pawn pawn = (Pawn) board[r2][c2];
					if ((pawn.color == "w" && r2 == 8) 
					 || (pawn.color == "b" && r2 == 1)) {		// PROMOTE!
						promote(r2, c2);
					}
				}
				String mColor = mover.color; // Check if this was a dumb move (K now in check)
				if (mColor.equals("w") && whiteKing.checkThreats()) {
					mover.row = r1;
					mover.col = c1;
					board[r1][c1] = mover;
					board[r2][c2] = originalPiece;
					throw new IllegalMoveException("Illegal move, try again");
				} else if (mColor.equals("b") && blackKing.checkThreats()) {
					mover.row = r1;
					mover.col = c1;
					board[r1][c1] = mover;
					board[r2][c2] = originalPiece;
					throw new IllegalMoveException("Illegal move, try again");
				}
			}//Illegal move
			else{
				board[r1][c1]=mover;
				throw new IllegalMoveException("Illegal move, try again");
			}
		}
		else{ //DOES NOT HAVE PIECE
			throw new IllegalMoveException("Illegal move, try again");
		}
			
	}
	
	public class IllegalMoveException extends IllegalArgumentException{
		
		public IllegalMoveException(String e){
			super(e);
		}
		
	}
	
}
