/**
 * @author Marcus Lomi, Daniel Ayoub
 */
package Chess;

import java.util.*;
import java.util.function.*;
import Model.*;

public class Chess {
	
	/**
	 * True if it is white's turn
	 */
	public static boolean whiteTurn=true;
	/**
	 * True if it is black's turn
	 */
	public static boolean blackTurn=false;
	/**
	 * The board storing the locations of the chess pieces
	 */
	public static Chessboard board;
	/**
	 * True if the game has reached a draw
	 */
	public static boolean draw = false;
	
	public static void main(String[] args) {
		board=new Chessboard();
		String playerMove;
		Scanner input = new Scanner(System.in);
		boolean play = true;
		boolean illegalMove=false;
	
		while(play){
			
			board.printboard();
			if(whiteTurn){
				if(Chessboard.whiteKing.checkThreats()){
					if(checkCheckMate(Chessboard.whiteKing)){
						System.out.println("Checkmate");
						System.out.println("Black wins");
						System.exit(0);
					}
					else{
						System.out.println("Check");
					}
					
					//System.out.println(checkCheckMate(Chessboard.whiteKing) ? " AND CHECKMATE ON WHITE" : "");
					//System.out.println(Chessboard.whiteKing.validMoveCount());
				}
				System.out.print("White's turn:");
			}
			else if(blackTurn){
				if(Chessboard.blackKing.checkThreats()){
					
					if(checkCheckMate(Chessboard.blackKing)){
						System.out.println("Checkmate");
						System.out.println("White wins");
						System.exit(0);
					}
					else{
						System.out.println("Check");
					}
					
					//System.out.println(checkCheckMate(Chessboard.blackKing) ? " AND CHECKMATE ON BLACK" : "");
					//System.out.println(Chessboard.blackKing.validMoveCount());
				}
				System.out.print("Black's turn:");
			}
			playerMove=input.nextLine();	
			if (playerMove.equals("d7 c6")) {
				String something = "something";
			}
			/*Catches syntactically incorrect moves (format other than <letter/number>)*/
			while(!validInput(playerMove.trim())){	
				if(playerMove.equals("resign")){
					if (whiteTurn) {
						System.out.println("Black wins");
					} else if (blackTurn) {
						System.out.println("White wins");
					}
					System.exit(0);
				}
				System.out.println("Illegal move, try again");
				playerMove=input.nextLine();
			}
			
			/*Do while loop repeats if it catches illegal moves*/
			do{
				int[] array=textToCoord(playerMove);
				try{
					board.move(array[1], array[0], array[3], array[2]);
					illegalMove=false;									
				}catch (Exception e){
					illegalMove=true;		
					do{
						System.out.println("Illegal move, try again");
						playerMove=input.nextLine();	
						if(playerMove.equals("end")){
							play=false;
						}
					}while(!validInput(playerMove.trim()));
					
				}
				
			}while(illegalMove==true);
			whiteTurn=!(whiteTurn);
			blackTurn=!(blackTurn);
		}
		
	}
	
	
	/**
	 * Converts a string representation of a piece type to 
	 * an instance of that type's Class
	 * @param s the string representation of the piece type (N, Q, R, or B)
	 * @return The piece's class
	 */
	private static Class<?> convertToPieceType(String s) {
		s = s.trim();
		if (s.equals("N")) return Knight.class;
		if (s.equals("Q")) return Queen.class;
		if (s.equals("R")) return Rook.class;
		if (s.equals("B")) return Bishop.class;
		return null;
	}
	
	/**
	 * Checks if a king is in check-mate
	 * @param def The defending King
	 * @return True if the king is check-mated, false otherwise.
	 */
	private static boolean checkCheckMate(King def) {
		String dColor = def.color;
		String aColor = (def.color.equals("w")) ? "b" : "w";
		for (int r = 1; r <= 8; r++) {
			for (int c = 0; c <= 7; c++) {
				Piece mover = board.getPiece(r, c);
				if (mover != null && mover.color.equals(dColor)) { // This is a piece that could maybe defend the king
					if (canSaveKing(def, mover)) return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Checks whether a particular piece can be moved to 
	 * protect a king that is currently in check.
	 * @param k The King in check
	 * @param p The Piece that could potentially save the King
	 * @return True if the piece can be moved such that the King will no longer be in check
	 */
	private static boolean canSaveKing(King k, Piece p) {
		int originalRow = p.row;
		int originalCol = p.col;
		boolean possible = false;
		for (int r = 1; r <= 8; r++) {
			for (int c = 0; c <= 7; c++) {
				Piece originalPiece = board.getPiece(r, c);
				if (originalRow == 4 && originalCol == 6 && r == 3 && c == 7) {
					String something = "something";
				}
				if(p.move(r, c)) {
					p.row = r;
					p.col = c;
					Chessboard.board[originalRow][originalCol] = null;
					if (!k.checkThreats()) {
						possible = true;
						//System.out.println("Row: " + originalRow + " Col: " + originalCol + " can move to:");
						//System.out.println("Row: " + r + " Col: " + c);
					}
					board.board[r][c] = originalPiece;			// Undo the move
					board.board[originalRow][originalCol] = p;
					p.row = originalRow;
					p.col = originalCol;
					if (possible) return possible; // Not necessary, but saves us a lot of checks
				}
			}
		}
		return possible;
	}
	
	/**
	 * @param s String of the user input
	 * @return True/false whether or not the string is syntactically correct
	 */
	public static boolean validInput(String s){
		String [] moves = s.split(" ");
		s=s.toLowerCase();
		if (moves.length == 1) {
			if (moves[0].trim().equals("draw") && draw) {
				System.out.println("It's a draw!");
				System.exit(0);
			} else {
				return false;
			}
		}
		if (moves.length == 3) {
			String[] temp = {moves[0], moves[1]};
			if (convertToPieceType(moves[2])!=null) {
				board.promotionType = convertToPieceType(moves[2]);
			} else if (moves[2].equals("draw?")) {
				draw = true;
			}
			moves = temp;
		}
		
		if(moves.length!=2){
			return false;
		}
		if(moves[0].trim().length()!=2||moves[1].trim().length()!=2){
			return false;
		}
		if(moves[0].trim().equals(moves[1].trim())){
			return false;
		}
		for(String a: moves){
			
			if(!Character.isAlphabetic(a.charAt(0))||!Character.isDigit(a.charAt(1))){
				return false;
			}
			char col=a.toLowerCase().charAt(0);
			if(col!='a'&&col!='b'&&col!='c'&&col!='d'&&col!='e'&&col!='f'&&col!='g'&&col!='h'){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @param s Syntactically correct string that needs to be converted to number coordinates
	 * @return result Int array of number coordinates in the form {row1,col1,row2,col2}
	 */
	public static int[] textToCoord(String s){
		while(s.contains(" ")){
			s=s.replace(" ", "");
		}
		int[] result = new int[4];
		result[0]=(s.charAt(0)%48)-1;
		result[1]=(int)s.charAt(1)%48;
		result[2]=(s.charAt(2)%48)-1;
		result[3]=(int)s.charAt(3)%48;
		
		return result;
		
	}

}
