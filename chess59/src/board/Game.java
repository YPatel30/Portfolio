package board;

import java.util.ArrayList;
import pieces.*;

/**
 * Game class: keeps track of the board pieces and possible moves,
 *
 * @author Om Shah (os207)
 * @author Yash Patel (ykp15)
 */
public class Game {
	
	public Piece[][] gameboard; 
	boolean whiteTurn = true;
	boolean check = false;
	
	/**
	 * constructor: this creats the board and adds pieces as well as null spaces
	 * @author Om Shah (os207)
	 */
	public Game(){
		gameboard = new Piece[8][8];
		
		
		gameboard[0][0] = new Rook(false);
		gameboard[0][1] = new Knight(false);
		gameboard[0][2] = new Bishop(false);
		gameboard[0][3] = new Queen(false);
		gameboard[0][4] = new King(false);
		gameboard[0][5] = new Bishop(false);
		gameboard[0][6] = new Knight(false);
		gameboard[0][7] = new Rook(false);
		
		for(int i = 0; i < 8; i++){
			gameboard[1][i] = new Pawn(false);
		}
		
		
		for(int i = 0; i < 8; i++){
			gameboard[2][i] = null;
			gameboard[3][i] = null;
			gameboard[4][i] = null;
			gameboard[5][i] = null;
		}

		
		for(int i = 0; i < 8; i++){
			gameboard[6][i] = new Pawn(true);
		}
		
		gameboard[7][0] = new Rook(true);
		gameboard[7][1] = new Knight(true);
		gameboard[7][2] = new Bishop(true);
		gameboard[7][3] = new Queen(true);
		gameboard[7][4] = new King(true);
		gameboard[7][5] = new Bishop(true);
		gameboard[7][6] = new Knight(true);
		gameboard[7][7] = new Rook(true);
		
	}
	
    /**
     * simulateBoard() method: this is called before/after a move
     */
    public void simulateBoard(){
        System.out.println();

        for (int r = 0; r < 8; r++){
            for (int c = 0; c < 8; c++){

                if ((gameboard[r][c]== null) && (((r % 2 == 0) && (c % 2 != 0)) || ((r % 2 != 0) && (c % 2 == 0)))){
                    System.out.print("##");
                } 
				else if (gameboard[r][c]== null){
                    System.out.print("  ");
                }
				else{
                    System.out.print(gameboard[r][c]);
                }

                if (c != 7){
                    System.out.print(" ");
                } 
				else{
                    System.out.print(" " + (8-r));
                }
            }
            System.out.println();
        }
        System.out.println(" a  b  c  d  e  f  g  h");
        System.out.println();
    }
	
	/**
     * activePiece() method: sees if a move is valid 
     * sees if a king is in check 
	 *
	 * @author Om Shah (os207)
	 * @param startCords gets coordinate when piece is being moved
	 * @param endCords	gets coordinate after the piece is moved 
	 * @param whiteMove	shows whos turn it is 
	 * @return shows true if valid and invalid if false 
	 */
	public boolean activePiece  (int[] startCords, int[] endCords, boolean whiteMove){
		
		Piece currPiece = gameboard[startCords[0]][startCords[1]];

		if(currPiece == null){
			return false;
		}
		else if((currPiece.isWhite && whiteMove) || (!currPiece.isWhite && !whiteMove)){

			if(currPiece.whenLegal(startCords, endCords, this)){
				
				Piece otherPiece = gameboard[endCords[0]][endCords[1]];
		
				gameboard[startCords[0]][startCords[1]] = null;
				gameboard[endCords[0]][endCords[1]] = currPiece;
				
				for(int i = 0; i < 8; i++){
					for(int j = 0; j < 8; j++){
						int[] temp = {i, j};

						if(inCheck(temp, whiteMove)){
							
							gameboard[startCords[0]][startCords[1]] = currPiece;
							gameboard[endCords[0]][endCords[1]] = otherPiece;		
							return false;
						}
					}
				}
				return true;
			}
			else{
				return false;
			}
		}
		return false;
	}
	
	public boolean returnCheck(){
		return check;
	}

	/**
	 * enPassPoss() method: stops en passant after one turn
     * checks all pieces in the row and sets the piece's en passant value to false 
	 * 
	 * @author Yash Patel (ykp15)
	 * @param whiteTurn checks if it is white's turn and keeps track of y value
	 */

	public void enPassPoss(boolean whiteTurn){
		if(whiteTurn){
			for(int i = 0; i < 8; i++){
				
				if(gameboard[4][i] != null && gameboard[4][i].isWhite){
					gameboard[4][i].enPass = false;
				}
			}
		}
		else{
			for(int i = 0; i < 8; i++){

				if(gameboard[3][i] != null && !gameboard[3][i].isWhite){
					gameboard[3][i].enPass = false;
				}
			}
		}
	}
	/**
	 * gettingIndex() method: this translate it into coordinates 
	 * @author Om Shah (os207)
	 * @param str	string to be translated
	 * @return produces an array of coordinates 
	 */
	public static int[] gettingIndex(String str){
		int x = 0;
		int y;
		int[] cords = new int[2];
		
		if(str.charAt(0) == 'a'){
			x = 0;
		}
		else if(str.charAt(0) == 'b'){
			x = 1;
		}
		else if(str.charAt(0) == 'c'){
			x = 2;
		}
		else if(str.charAt(0) == 'd'){
			x = 3;
		}
		else if(str.charAt(0) == 'e'){
			x = 4;
		}
		else if(str.charAt(0) == 'f'){
			x = 5;
		}
		else if(str.charAt(0) == 'g'){
			x = 6;
		}
		else if(str.charAt(0) == 'h'){
			x = 7;
		}
		
		y = 8 - (str.charAt(1)-'0');
		
		cords[0] = y;
		cords[1] = x;
	
		return cords;
	}

	/**
	 * getKingCords() method: finds the king's coordinates
	 * 
	 * @author Yash Patel (ykp15)
	 * @param wKing	true if finding white king coordinates, false if finding black king coordinates
	 * @return coordinates of the king of the color specified by the parameter
	 */

	public int[] getKingCords(boolean wKing){
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				Piece currPiece = gameboard[i][j];

				if(currPiece != null){

					if(King.class.isInstance(currPiece)){
						int[] cord = {i, j};

						if(wKing && currPiece.isWhite){
							return cord;
						}
						else if(!wKing && !currPiece.isWhite){
							return cord;
						}
					}
				}
			}
		}
		return null;
	}
	/**
	 * kingPossibleMove() method: shows if a king can make a particular move 
	 * 
	 * @author Yash Patel (os207)
	 * @param startCords shows the coordinates of the king starting position 
	 * @param endCords shows the coordinates of the kings ending pos 
	 * @param wKing where white king means true and black king means false 

	 * @return shows true if the king can move and false if king can not move or if it is in check
	 */	
	public boolean kingPossibleMove(int[] startCords, int[] endCords, boolean wKing){

		if(endCords[0] < 0 || endCords[0] > 7 || endCords[1] < 0 || endCords[1] > 7){
			return false;
		}
		Piece primary = gameboard[startCords[0]][startCords[1]]; 
		Piece otherPiece = gameboard[endCords[0]][endCords[1]]; 
		
		if(otherPiece != null){
			return false;
		}

		check = true;

		if(primary.whenLegal(startCords, endCords, this)){
			gameboard[startCords[0]][startCords[1]] = null;
			gameboard[endCords[0]][endCords[1]] = primary;
			check = false;

			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					int[] tempStart = {i, j};
					
					if(inCheck(tempStart, wKing)){
						gameboard[startCords[0]][startCords[1]] = primary;
						gameboard[endCords[0]][endCords[1]] = otherPiece;	
						return false;
					}
				}
			}
			
			gameboard[startCords[0]][startCords[1]] = primary;
			gameboard[endCords[0]][endCords[1]] = otherPiece;	
			check = false;
			return true;
		}
		else {
			check = false;
			return false;
		}

	}
	/**
	 * inCheck() mehod: shows if piece has king in chekc
	 * 
	 * @author Om Shah (os207)
	 * @param startCords coordinates of piece that may be putting king in check
	 * @param wKing true if the king is in check, false if black king is in check
	 * @return returns true if the piece puts the king in check, returns false otherwise 
	 */
	public boolean inCheck(int[] startCords, boolean wKing){
		int[] endCords = getKingCords(wKing);

		Piece beginningPiece = gameboard[startCords[0]][startCords[1]];

		if(beginningPiece == null){
			return false;
		}
		check = true;

		if(beginningPiece.whenLegal(startCords, endCords, this)){
			check = false;
			return true;
		}
		check = false;
		return false;
	}
	
	/**
	 * inCheckmate() method: shows the end of the game if the king move can be out of check
	 * shows false if any moves does not have king back to check 
	 * 
	 * @author Yash Patel (ykp15)
	 * @param wKing shows true if the white king is in check false if not
	 * @return shows true if there are not legal moves, false if otherwise 
	 */
	public boolean inCheckmate(boolean wKing){
		
		int[] kingCords = getKingCords(wKing);
		int[] temp = {kingCords[0], kingCords[1]};
		
		temp[1] = temp[1] - 1;
		if(kingPossibleMove(kingCords, temp, wKing)){
			return false;
		}
				
		temp[0] = temp[0] - 1;
		if(kingPossibleMove(kingCords, temp, wKing)){
			return false;
		}

		temp[1] = temp[1] + 1;
		if(kingPossibleMove(kingCords, temp, wKing)) {
			return false;
		}
	
		temp[1] = temp[1] + 1;
		if(kingPossibleMove(kingCords, temp, wKing)) {
			return false;
		}
		
		temp[0] = temp[0] + 1;
		if(kingPossibleMove(kingCords, temp, wKing)) {
			return false;
		}
		
		temp[0] = temp[0] + 1;
		if(kingPossibleMove(kingCords, temp, wKing)) {
			return false;
		}
		
		temp[1] = temp[1] - 1;
		if(kingPossibleMove(kingCords, temp, wKing)) {
			return false;
		}
		
		temp[1] = temp[1] - 1;
		if(kingPossibleMove(kingCords, temp, wKing)) {
			return false;
		}

				
		ArrayList<int[]> opponents  = new ArrayList<int[]>();
		
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
				int[] tempStart = {i, j};
				Piece oppPiece = gameboard[i][j];

				if(oppPiece != null && oppPiece.whenLegal(tempStart, kingCords, this)){
					opponents .add(tempStart);
				}
			}
		}
		
		if(opponents .size() > 1){
			return true;
		}
		
		if(!opponents .isEmpty()){
			int[] opp = opponents .get(0);
			for(int i = 0; i < 8; i++){
				for(int j = 0; j < 8; j++){
					int[] save = {i, j};
					Piece savePiece = gameboard[i][j];

					if(savePiece != null && savePiece.whenLegal(save, opp, this)){
						return false;
					}
				}
			}
		}
		return true;
	}	
}