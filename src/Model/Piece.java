/**
 * @author Marcus Lomi, Daniel Ayoub
 */
package Model;

public abstract class Piece {	
		
		/**
		 * The piece's color
		 */
		public String color;
		
		/**
		 * Single character representing the piece's type
		 * (P,N,K,B,R,Q)
		 */
		public String type;
		
		/**
		 * The piece's location on the board
		 */
		public int row, col;
		
		/**
		 * True if the piece has been moved, false otherwise
		 */
		public boolean moved;

		/**
		 * Constructs a new Piece
		 */
		public Piece(){
			this.color="";
		}
		
		/**
		 * Constructs a new Piece
		 * @param color The color of the Piece
		 * @param row The initial row of the Piece
		 * @param col The initial column of the Piece
		 */
		public Piece(String color, int row, int col){
			this.color=color;
			this.row = row;
			this.col = col;
			this.moved=false;
		}
		
		/**
		 * @return piece type in the form of a one character string
		 */
		public String getType(){
			return this.type;
		}
		/**
		 * @return color in the form of a one character string
		 */
		public String getColor(){
			return this.color;
		}
		
		/**
		 * Checks validity of a diagonal move up and to the right
		 * @param row row of destination square
		 * @param col column of destination square
		 * @return true or false, whether or not move is valid
		 */
		public boolean upperRightCheck(int row, int col){
			int i=this.row;	
			for(int j = this.col; j<=7;j++){
				if(Chessboard.hasPiece(i, j)&&i!=this.row&&i!=row){	//Checking if a piece is blocking your path
					if(i!=row&&j!=col){
						return false;
					}
				}
				if(i==row&&j==col){	//No piece is blocking the path
					Chessboard.board[row][col]=this;
					this.moved=true;
					return true;
				}
				i++;
			}
			return false;
		}
		/**
		 * Checks validity of a diagonal move up and to the left
		 * @param row row of destination square
		 * @param col column of destination square
		 * @return true or false, whether or not move is valid
		 */
		public boolean upperLeftCheck(int row, int col){
			int i = this.row;
			for(int j = this.col; j>=0;j--){
				if(Chessboard.hasPiece(i, j)&&i!=this.row&&i!=row){	//Checking if a piece is blocking your path
					if(i!=row&&j!=col){
						return false;
					}
				}
				if(i==row&&j==col){	//No piece is blocking the path
					Chessboard.board[row][col]=this;
					this.moved=true;
					return true;
				}
			i++;
			}
			return false;
		}
		/**
		 * Checks validity of a diagonal move down and to the right
		 * @param row row of destination square
		 * @param col column of destination square
		 * @return true or false, whether or not move is valid
		 */
		public boolean lowerRightCheck(int row, int col){
			int i = this.row;
			for(int j = this.col; j<=7;j++){
				if(Chessboard.hasPiece(i, j)&&i!=this.row&&i!=row){	//Checking if a piece is blocking your path
					if(i!=row&&j!=col){
						return false;
					}
				}
				if(i==row&&j==col){	//No piece is blocking the path
					Chessboard.board[row][col]=this;
					moved=true;
					return true;
				}
				i--;
			}
			return false;
		}
		/**
		 * Checks validity of a diagonal move down and to the left
		 * @param row row of destination square
		 * @param col column of destination square
		 * @return true or false, whether or not move is valid
		 */
		public boolean lowerLeftCheck(int row, int col){
			int i = this.row;
			for(int j = this.col; j>=0;j--){
				if(Chessboard.hasPiece(i, j)&&i!=this.row){	//Checking if a piece is blocking your path
					if(i!=row&&j!=col){
						return false;
					}
				}
				if(i==row&&j==col){	//No piece is blocking the path
					Chessboard.board[row][col]=this;
					moved=true;
					return true;
				}
				i--;
			}
			return false;
		}
		
		/**
		 * Checks validity of a vertical move upward
		 * @param row row of destination square
		 * @param col column of destination square
		 * @return true or false, whether or not move is valid
		 */
		public boolean upperVertCheck(int row, int col){
			for(int i = this.row; i<=8;i++){	//Checks rows above 
				
				if(Chessboard.hasPiece(i, col)&&i!=this.row&&i!=row){	//Checking if a piece is blocking your path
					return false;
				}
				else if(i==row){	//No piece is blocking the path
					Chessboard.board[row][col]=this;
					this.moved=true;
					return true;
				}
				
			}
			return false;
		}
		/**
		 * Checks validity of a vertical move downward
		 * @param row row of destination square
		 * @param col column of destination square
		 * @return true or false, whether or not move is valid
		 */
		public boolean lowerVertCheck(int row, int col){
			for(int i = this.row; i>=1;i--){
				
				if(Chessboard.hasPiece(i, col)&&i!=this.row&&i!=row){	//Checking if a piece is blocking your path
					return false;
				}
				else if(i==row){	//No piece is blocking the path
					Chessboard.board[row][col]=this;
					this.moved=true;
					return true;
				}
				
			}
			return false;
		}
		
		/**
		 * Checks validity of a horizontal move to the right
		 * @param row row of destination square
		 * @param col column of destination square
		 * @return true or false, whether or not move is valid
		 */
		public boolean rightHorizontalCheck(int row, int col){
			for(int i = this.col; i<=7;i++){	
				if(Chessboard.hasPiece(row, i)&&i!=this.col&&i!=col){	//Checking if a piece is blocking your path
					return false;
				}
				else if(i==col){	//No piece is blocking the path
					Chessboard.board[row][col]=this;
					this.moved=true;
					return true;
				}
			}
			return false;
		}
		/**
		 * Checks validity of a horizontal move to the left
		 * @param row row of destination square
		 * @param col column of destination square
		 * @return true or false, whether or not move is valid
		 */
		public boolean leftHorizontalCheck(int row, int col){
			for(int i = this.col; i>=0;i--){ 	
				if(Chessboard.hasPiece(row, i)&&i!=this.col&&i!=col){	//Checking if a piece is blocking your path
					return false;
				}
				else if(i==col){	//No piece is blocking the path
					Chessboard.board[row][col]=this;
					this.moved=true;
					return true;
				}
			}
			return false;
		}
		
		/**
		 * Moves the piece from it's current position
		 * @param a The target row number [1,8]
		 * @param b The target column number [0,7]
		 * @return
		 */
		public abstract boolean move(int a, int b);
		
		public class IllegalMoveException extends IllegalArgumentException{
			
			/**
			 * Constructs a new IllegalMoveException
			 * @param e The error message.
			 */
			public IllegalMoveException(String e){
				super(e);
			}
			
		}
	
}
