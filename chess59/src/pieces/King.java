package pieces;

import board.Game;

/**
 * King Class: Implements King 
 * 
 * @author Yash Patel (ykp15)
 *
 */

public class King extends Piece {

	public King(boolean isWhite) {
		super(isWhite);
	}

	public String toString(){
		if(isWhite){
			return "wK";
		}
		return "bK";
	}

	/**
	 * checks if move is legal based on the coordinates
	 * implements castling which moves the rook
	 * @author Yash Patel (ykp15)
	 * 
	 * @param stc	starting corrdinates of piece
	 * @param endc	ending coordinates of piece 
	 * @param game	instance of a game 
	 * @return true if move is legal
	 * @return false if move is illegal
	 */

	public boolean whenLegal(int[] stc, int[] endc, Game game) {
		int stcX = stc[1];
		int stcY = stc[0];
		int endcX = endc[1];
		int endcY = endc[0];
		
		Piece finalPiece = game.gameboard[endcY][endcX];
	
		
		if(first && stcY == endcY){
			
			if(endcX == stcX + 2){

				for(int row = stcX + 1; row < 7; row++){
					Piece temporaryPiece = game.gameboard[endcY][row];

					if(temporaryPiece != null){
						return false;
					}
				}

				Piece rookPiece = game.gameboard[endcY][7];
				if(rookPiece.first == false){
					return false;
				}
				
				game.gameboard[endcY][endcX-1] = rookPiece;
				game.gameboard[endcY][7] = null;
				first = false;
				rookPiece.first = false;
				return true;
			}
			
			else if(endcX == stcX - 2){

				for(int row = stcX- 1; row > 0; row--){
					Piece tempPiece = game.gameboard[endcY][row];

					if(tempPiece != null){
						return false;
					}
				}
				Piece rookPiece = game.gameboard[endcY][0];

				if(rookPiece.first == false){
					return false;
				}
				
				game.gameboard[endcY][endcX + 1] = rookPiece;
				game.gameboard[endcY][0] = null;
				first = false;
				rookPiece.first = false;
				return true;
			}
		}
		

		if(stcX == endcX && stcY == endcY){
			return false;
		}
		
		if(finalPiece != null){

			if(isWhite && finalPiece.isWhite){
				return false;
			}

			if(!isWhite && !finalPiece.isWhite){
				return false;
			}
		}


		if(Math.abs(stcY - endcY) > 1){
			return false;
		}
		

		if(Math.abs(stcX - endcX) > 1){
			return false;
		}

		if(stcX == endcX){

			if(stcY != endcY + 1 && stcY != endcY - 1){
				return false;
			}
		}
		else if(stcY == endcY){

			if(stcX != endcX + 1 && stcX != endcX - 1){
				return false;
			}
		}
		else if(stcY == endcY + 1 || stcY == endcY - 1){
			
			if(stcX != endcX + 1 && stcX != endcX - 1){
				return false;
			}
		}
		
		if(!game.returnCheck()){
			first = false;
		}

		return true;
	}

	
	
	
	
	
}