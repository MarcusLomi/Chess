/**
 * @author Marcus Lomi, Daniel Ayoub
 */
package Model;

public class King extends Piece {
	
	public String type = "K";
	
	/**
	 * Stores whether or not this king is in check.
	 */
	private boolean inCheck=false;
	
	public King(String color, int row, int col){
		super(color, row, col);
		this.inCheck=false;
	}
	public String toString(){
		return this.color+""+this.type;
	}
	public String getType(){
		return this.type;
	}
	public boolean checkStatus(){
		return inCheck;
	}
	
	/**
	 * This move checks the squares surrounding the king and counts the number of valid moves for him.
	 * @return result number of valid moves for the king. Max return value is 9
	 */
	public int validMoveCount(){
		int result = 0;
		
		/*Checks the surrounding squares*/
		for(int r=1;r>=-1;r--){
			for(int c=1;c>=-1;c--){
				
				if(this.row+r>8||this.row+r<1||this.col+c>7||this.col+c<0){	//coordinates out of range
					continue;
				}
				if(r==0&&c==0){		// by subtracting 0 from the row and column, you get the king's current square
					continue;		// I may just want to keep this check here and run Checkthreats on it
				}
				if(Chessboard.hasPiece(this.row+r,this.col+c)){
					if(Chessboard.getPiece(this.row+r,this.col+c).getColor().equals(this.getColor())){
						continue;
					}//Friendly piece is occupying a surrounding square. It is not available to move to;
				}
				if(this.checkThreats(this.row+r,this.col+c)==false){
					result++;
				}//There are no threats on a surrounding square. You can move to it. 
			}
		}
		
		return result;
	}
	
	/**
	 * Checks if the intended position is safe for the king by checking if any 
	 * 		pieces will place it in check. The loops in this method will all complete or
	 * 		break if there are no threats. Can detect out of bounds. 
	 * @param r row of intended destination square for the king
	 * @param c col of intended destination square for the king
	 * @return true or false/ depending on whether or not the new position will put the king in check
	 */
	public boolean checkThreats(int r, int c){
		
		if(r>8||r<1||c<0||c>7){
			return false;
		}
		
		if(this.getColor().equals("b")){
			if(Chessboard.getPiece(r-1, c-1)!=null){
				Piece c1 = Chessboard.getPiece(r-1, c-1);
				if(c1.getColor().equals("w")&&c1.getType().equals("p")){
					return true;
				}
			}
			if(Chessboard.getPiece(r-1, c+1)!=null){
				Piece c2 = Chessboard.getPiece(r-1, c+1);
				if(c2.getColor().equals("w")&&c2.getType().equals("p")){
					return true;
				}
			}
		}
		else if(this.getColor().equals("w")){
			if(Chessboard.getPiece(r+1, c-1)!=null){
				Piece c1 = Chessboard.getPiece(r+1, c-1);
				if(c1.getColor().equals("b")&&c1.getType().equals("p")){
					return true;
				}
			}
			if(Chessboard.getPiece(r+1, c+1)!=null){
				Piece c2 = Chessboard.getPiece(r+1, c+1);
				if(c2.getColor().equals("b")&&c2.getType().equals("p")){
					return true;
				}
			}
		}
		
		/*This block checks for knights in positions that will endanger the king*/
		Piece[] knights = new Piece[8];
		knights[0]=Chessboard.getPiece(r+2, c+1);
		knights[1]=Chessboard.getPiece(r+2, c-1);
		knights[2]=Chessboard.getPiece(r+1, c-2);
		knights[3]=Chessboard.getPiece(r+1, c+2);
		knights[4]=Chessboard.getPiece(r-1, c+2);
		knights[5]=Chessboard.getPiece(r-1, c-2);
		knights[6]=Chessboard.getPiece(r-2, c-2);
		knights[7]=Chessboard.getPiece(r-2, c+1);						
		for(int i=0;i<8;i++){
			if(knights[i]==null){
				continue;
			}
			if(knights[i].getType().equals("N")&&!(knights[i].getColor().equals(this.getColor()))){
				return true;
			}
		}
		
		/*This block checks for a king the immediate area surrounding the intended 
		 * square.*/
		Piece[] surround = new Piece[8];
		surround[0]=Chessboard.getPiece(r+1, c+1);
		surround[1]=Chessboard.getPiece(r+1, c-1);
		surround[2]=Chessboard.getPiece(r+1, c);
		surround[3]=Chessboard.getPiece(r ,  c+1);
		surround[4]=Chessboard.getPiece(r , c-1);
		surround[5]=Chessboard.getPiece(r-1, c-1);
		surround[6]=Chessboard.getPiece(r-1, c+1);
		surround[7]=Chessboard.getPiece(r-1, c);						
		for(int i=0;i<8;i++){
			if(surround[i]==null){
				continue;
			}
			if(surround[i].getType().equals("K")&&!(surround[i].getColor().equals(this.getColor()))){
				return true;
			}
		}
		
		
		//UPPER RIGHT CHECK
		int i=r;
		for(int j = c; j<=7;j++){
			if(Chessboard.hasPiece(i, j)&&i!=r){						//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(i, j).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(i, j).getType();		//Type of the piece within king range
				if(dType.equals(this.getType())&&dColor.equals(this.getColor())){	//Crossing over itself in the check
					continue;
				}
				if(!dColor.equals(this.color)){
					if(dType.equals("Q")||dType.equals("B")){
						return true;
					}
					break;
				}
				else{
					break;
				}
			}
			if(i>8){
				break;
			}
			i++;
		}
		//UPPER LEFT CHECK
		i = r;
		for(int j = c; j>=0;j--){
			if(Chessboard.hasPiece(i, j)&&i!=r){	//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(i, j).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(i, j).getType();		//Type of the piece within king range
				if(dType.equals(this.getType())&&dColor.equals(this.getColor())){	//Crossing over itself in the check
					continue;
				}
				if(!dColor.equals(this.color)){
					if(dType.equals("Q")||dType.equals("B")){
						return true;
					}
					break;
				}
				else{
					break;
				}
			}
			if(i>8){
				break;
			}
			i++;
		}
		
		//LOWER RIGHT CHECK
		i=r;
		for(int j = c; j<=7;j++){
			if(Chessboard.hasPiece(i, j)&&i!=r){	//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(i, j).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(i, j).getType();		//Type of the piece within king range
				if(dType.equals(this.getType())&&dColor.equals(this.getColor())){	//Crossing over itself in the check
					continue;
				}
				if(!dColor.equals(this.color)){
					if(dType.equals("Q")||dType.equals("B")){
						return true;
					}
					break;
				}
				else{
					break;
				}
			}
			if(i<1){
				break;
			}
			i--;
		}
		
		//LOWER LEFT CHECK
		i = r;
		for(int j = c; j<=7;j--){
			if(Chessboard.hasPiece(i, j)&&i!=r){	//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(i, j).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(i, j).getType();		//Type of the piece within king range
				if(dType.equals(this.getType())&&dColor.equals(this.getColor())){	//Crossing over itself in the check
					continue;
				}
				if(!dColor.equals(this.color)){
					if(dType.equals("Q")||dType.equals("B")){
						return true;
					}
					break;
				}
				else{
					break;
				}
			}
			if(i<1){
				break;
			}
			i--;
		}
		
		//Upper Vertical Check LONG RANGE
		for(i = r; i<=8;i++){	//Checks rows above 
			if(Chessboard.hasPiece(i, c)&&i!=r){	//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(i, c).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(i, c).getType();		//Type of the piece within king range
				if(dType.equals(this.getType())&&dColor.equals(this.getColor())){	//Crossing over itself in the check
					continue;
				}
				if(!dColor.equals(this.color)){
					if(dType.equals("R")||dType.equals("Q")){
						return true;
					}
					break;
				}
				else{
					break;
				}
			
			}
		
		}
		
		//LOWER VERTICAL CHECK LONG RANGE
		for(i = r; i>=1;i--){	//Checks rows above 
			if(Chessboard.hasPiece(i, c)&&i!=r){						//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(i, c).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(i, c).getType();		//Type of the piece within king range
				if(dType.equals(this.getType())&&dColor.equals(this.getColor())){	//Crossing over itself in the check
					continue;
				}
				if(!dColor.equals(this.color)){
					if(dType.equals("R")||dType.equals("Q")){
						return true;
					}
					break;
				}
				else{
					break;
				}
			
			}
			
		}
		//RIGHT HORIZONTAL CHECK
		for(i = c; i<=7;i++){	
			if(Chessboard.hasPiece(r, i)&&i!=c){	//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(r, i).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(r, i).getType();		//Type of the piece within king range
				if(dType.equals(this.getType())&&dColor.equals(this.getColor())){	//Crossing over itself in the check
					continue;
				}
				if(!dColor.equals(this.color)){
					if(dType.equals("R")||dType.equals("Q")){
						return true;
					}
					break;
				}
				else{
					break;
				}
			
			}
			
		}
		//LEFT HORIZONTAL CHECK
		for(i = c; i>=0;i--){ 	
			if(Chessboard.hasPiece(r, i)&&i!=c){					//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(r, i).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(r, i).getType();		//Type of the piece within king range
				if(dType.equals(this.getType())&&dColor.equals(this.getColor())){	//Crossing over itself in the check
					continue;
				}
				if(!dColor.equals(this.color)){
					if(dType.equals("R")||dType.equals("Q")){
						return true;
					}
					break;
				}
				else{
					break;
				}
			
			}
			
		}
		
		return false;
	}
	
	/**
	 * Based on the king's current position, it checks if any pieces are placing it in check
	 * 		The loops in this method will all complete or break if there are no threats.
	 * @return true or false/ depending on whether or not the king is in check
	 */
	public boolean checkThreats(){
		
		/*Checks for any pieces in the squares surrounding the King
		 * White pawns attack the black king from a different angle than black
		 * pawns attacking a white king.
		 * 
		 *     CS ES CS <--- Black Pawns Threaten a White King on these corners
   		 *     ES KK ES <--- Edge pieces can will have rooks, or queens threatening the king. 
		 *     CS ES CS <--- White Pawns Threaten a Black King on these corners
		 *     
		 *     Queens will threaten the king on any square immediately surrounding him.
		 *     Bishops only on corner squares. 
		 *     
		 * ES=Edge square
		 * 
		 * CS=Corner Square
		 * */ 
		
		//Checks for pawns endangering the king
		if(this.getColor().equals("b")){
			if(Chessboard.getPiece(this.row-1, this.col-1)!=null){
				Piece c1 = Chessboard.getPiece(this.row-1, this.col-1);
				if(c1.getColor().equals("w")&&c1.getType().equals("p")){
					return true;
				}
			}
			if(Chessboard.getPiece(this.row-1, this.col+1)!=null){
				Piece c2 = Chessboard.getPiece(this.row-1, this.col+1);
				if(c2.getColor().equals("w")&&c2.getType().equals("p")){
					return true;
				}
			}
		}
		else if(this.getColor().equals("w")){
			if(Chessboard.getPiece(this.row+1, this.col-1)!=null){
				Piece c1 = Chessboard.getPiece(this.row+1, this.col-1);
				if(c1.getColor().equals("b")&&c1.getType().equals("p")){
					return true;
				}
			}
			if(Chessboard.getPiece(this.row+1, this.col+1)!=null){
				Piece c2 = Chessboard.getPiece(this.row+1, this.col+1);
				if(c2.getColor().equals("b")&&c2.getType().equals("p")){
					return true;
				}
			}
		}
		
		Piece[] knights = new Piece[8];
		if(Chessboard.getPiece(this.row+2, this.col+1)!=null){
			knights[0]=Chessboard.getPiece(this.row+2, this.col+1);
		}
		if(Chessboard.getPiece(this.row+2, this.col-1)!=null){
			knights[1]=Chessboard.getPiece(this.row+2, this.col-1);
		}
		if(Chessboard.getPiece(this.row+1, this.col-2)!=null){
			knights[2]=Chessboard.getPiece(this.row+1, this.col-2);
		}
		if(Chessboard.getPiece(this.row+1, this.col+2)!=null){
			knights[3]=Chessboard.getPiece(this.row+1, this.col+2);
		}
		if(Chessboard.getPiece(this.row-1, this.col+2)!=null){
			knights[4]=Chessboard.getPiece(this.row-1, this.col+2);		
		}
		if(Chessboard.getPiece(this.row-1, this.col-2)!=null){
			knights[5]=Chessboard.getPiece(this.row-1, this.col-2);
		}	
		if(Chessboard.getPiece(this.row-2, this.col-2)!=null){
			knights[6]=Chessboard.getPiece(this.row-2, this.col-2);
		}
		if(Chessboard.getPiece(this.row-2, this.col+1)!=null){
			knights[7]=Chessboard.getPiece(this.row-2, this.col+1);
		}
		
		
		
	
		
								
		for(int i=0;i<8;i++){
			if(knights[i]==null){
				continue;
			}
			if(knights[i].getType().equals("N")&&!(knights[i].getColor().equals(this.getColor()))){
				return true;
			}
		}
		
		/*The lower checks are meant for pieces endangering the king from a long range*/
		
		//UPPER RIGHT CHECK
		int i=this.row;
		for(int j = this.col; j<=7;j++){
			if(Chessboard.hasPiece(i, j)&&i!=this.row&&i!=row){			
				String dColor = Chessboard.getPiece(i, j).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(i, j).getType();		//Type of the piece within king range
				if(!dColor.equals(this.color)){
					if(dType.equals("Q")||dType.equals("B")){
						return true;
					}
					break;
				}
				else{
					break;
				}
			}
			if(i>8){
				break;
			}
			i++;
		}
		//UPPER LEFT CHECK
		i = this.row;
		for(int j = this.col; j<=7;j--){
			if(Chessboard.hasPiece(i, j)&&i!=this.row&&i!=row){	//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(i, j).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(i, j).getType();		//Type of the piece within king range
				if(!dColor.equals(this.color)){
					if(dType.equals("Q")||dType.equals("B")){
						return true;
					}
					break;
				}
				else{
					break;
				}
			}
			if(i>8){
				break;
			}
			i++;
		}
		
		//LOWER RIGHT CHECK
		i=this.row;
		for(int j = this.col; j<=7;j++){
			if(Chessboard.hasPiece(i, j)&&i!=this.row&&i!=row){	//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(i, j).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(i, j).getType();		//Type of the piece within king range
				if(!dColor.equals(this.color)){
					if(dType.equals("Q")||dType.equals("B")){
						return true;
					}
					break;
				}
				else{
					break;
				}
			}
			if(i<1){
				break;
			}
			i--;
		}
		
		//LOWER LEFT CHECK
		i = this.row;
		for(int j = this.col; j<=7;j--){
			if(Chessboard.hasPiece(i, j)&&i!=this.row&&i!=row){	//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(i, j).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(i, j).getType();		//Type of the piece within king range
				if(!dColor.equals(this.color)){
					if(dType.equals("Q")||dType.equals("B")){
						return true;
					}
					break;
				}
				else{
					break;
				}
			}
			if(i<1){
				break;
			}
			i--;
		}
		
		//Upper Vertical Check LONG RANGE
		for(i = this.row; i<=8;i++){	//Checks rows above 
			if(Chessboard.hasPiece(i, this.col)&&i!=this.row&&i!=row){	//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(i, this.col).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(i, this.col).getType();		//Type of the piece within king range
				if(!dColor.equals(this.color)){
					if(dType.equals("R")||dType.equals("Q")){
						return true;
					}
					break;
				}
				else{
					break;
				}
			
			}
		
		}
		
		//LOWER VERTICAL CHECK LONG RANGE
		for(i = this.row; i>=1;i--){	//Checks rows above 
			if(Chessboard.hasPiece(i, this.col)&&i!=this.row&&i!=row){	//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(i, this.col).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(i, this.col).getType();		//Type of the piece within king range
				if(!dColor.equals(this.color)){
					if(dType.equals("R")||dType.equals("Q")){
						return true;
					}
					break;
				}
				else{
					break;
				}
			
			}
			
		}
		//RIGHT HORIZONTAL CHECK
		for(i = this.col; i<=7;i++){	
			if(Chessboard.hasPiece(this.row, i)&&i!=this.col&&i!=col){	//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(this.row,i).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(this.row,i).getType();		//Type of the piece within king range
				if(!dColor.equals(this.color)){
					if(dType.equals("R")||dType.equals("Q")){
						return true;
					}
				}
				else{
					break;
				}
			
			}
			
		}
		//LEFT HORIZONTAL CHECK
		for(i = this.col; i>=0;i--){ 	
			if(Chessboard.hasPiece(this.row, i)&&i!=this.col&&i!=col){	//Checking if a piece is blocking your path
				String dColor = Chessboard.getPiece(this.row,i).getColor();	//Color of the piece within king range
				String dType = Chessboard.getPiece(this.row,i).getType();		//Type of the piece within king range
				if(!dColor.equals(this.color)){
					if(dType.equals("R")||dType.equals("Q")){
						return true;
					}
				}
				else{
					break;
				}
			
			}
			
		}
		
		return false;
	}
	/**
	 * Moves the king to destination spot if it is valid.
	 * @param row row of destination square
	 * @param col column of destination square
	 * 
	 * @return true/false depending on whether or not the move is valid 
	 */
	public boolean move(int row, int col){
		Piece dst = Chessboard.getPiece(row, col);
		if(dst!=null){
			if(dst.getColor().equals(this.getColor())){
				return false;
			}
		}
		
		// Check for castle
		if (Math.abs(col - this.col) == 2 && row == this.row && !this.moved&&this.checkThreats()==false) {
			if (col - this.col > 0) { // Rook on the right, col 7
				Piece rk = Chessboard.getPiece(this.row, 7);
				if (rk instanceof Rook) { 
					rk = (Rook) rk;
				}
				else {
					return false;
				}
				if (!rk.moved) {
					for (int i = this.col+1; i < rk.col; i++) {
						if (Chessboard.hasPiece(this.row, i)||this.checkThreats(this.row,i)==true) {
							return false; 
						}
					}	// No pieces between and not castling across check; all conditions met.
					Chessboard.board[rk.row][rk.col] = null;
					rk.move(row, col-1);
					rk.row=row;
					rk.col=col-1;
					Chessboard.board[row][col] = this;
					moved = true;
					return true;
				}
				return false;				
			} else {
				Piece rk = Chessboard.getPiece(this.row, 0);
				if (!(rk instanceof Rook)) { 
					return false;
				}
				rk = (Rook) rk;
				if (!rk.moved) {
					for (int i = this.col-1; i > rk.col; i--) {
						if (Chessboard.hasPiece(this.row, i)||this.checkThreats(this.row,i)==true) {
							return false; 
						}
					}	// No pieces between and not castling across check; all conditions met.
					Chessboard.board[rk.row][rk.col] = null;
					rk.move(row, col+1);
					rk.row=row;
					rk.col=col+1;
					Chessboard.board[row][col] = this;
					moved = true;
					return true;
				}
				return false;
			}
		}
				
		//If the destination is out of range
		if(Math.abs(row-this.row)>1||Math.abs(col-this.col)>1){
			return false;
		}
		if(checkThreats(row,col)==false){
			Chessboard.board[row][col]=this;
			moved=true;
			return true;
		}
	
		
		return true;
	}
	
}