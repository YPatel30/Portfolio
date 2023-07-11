package pieces;

import board.Game;
/**
 * 
 * Rook Class: Implements Rook
 * 
 * @author Om Shah (os207)
 *
 */


public class Rook extends Piece{

	public Rook(boolean isWhite) {
		super(isWhite);
	}

	public String toString(){
		if(isWhite){
			return "wR";
		}
		return "bR";
	}

	/**
	 * sees if the move is legal 
	 * sees if a horizontal or vertial move is possible and if a friendly piece is attacked
	 * looks to see if there is anything in the path from start to finish 
     *
	 * @author Om Shah (os207)
     *
	 * @param stc	shows starting coordinates 
	 * @param endc	shows ending coordinates 
	 * @param game	instance of a game
	 * @return true if move is legal 
	 * @return false if move is illegal
	 */

	
	public boolean whenLegal(int[] stc, int[] endc, Game game) {
		boolean right;
		boolean up;
		boolean vertical;

		int stcX = stc[1];
		int stcY = stc[0];
		int endcX = endc[1];
		int endcY = endc[0];
		
		if(stcX == endcX && stcY == endcY){
			return false;
		}
		
		
		if(stcX != endcX && stcY != endcY){ 
			return false;
		}
		
		Piece finalPiece = game.gameboard[endcY][endcX];
		if(stcY != endcY){
			vertical = true;
		}

		else{
			vertical = false;
		}

		if(stcX > endcX){
			right = false;
		}

		else{
			right = true;
		}
		
		if(stcY > endcY){
			up = true;
		}
		else{
			up = false;
		}
		
		
		if(finalPiece == null){
			if(vertical == true){
				if(up == true){
					for(int row = stcY-1; row > endcY; row--){

						if(game.gameboard[row][endcX] != null){
							return false;
						}
					}
				}
				else{
					for(int row = stcY+1; row < endcY; row++){

						if(game.gameboard[row][endcX] != null){
							return false;
						}
					}
				}
			}

			else{
				if(right == true){

					for(int row = stcX + 1; row < endcX; row++){

						if(game.gameboard[endcY][row] != null){
							return false;
						}
					}
				}

				else{
					for(int row = stcX-1; row > endcX; row--){
						if(game.gameboard[endcY][row] != null){
							return false;
						}
					}
				}
			}
		}
		else{
			if(isWhite && finalPiece.isWhite){
				return false;
			}
			if(!isWhite && !finalPiece.isWhite){
				return false;
			}
			if(vertical == true){
				if(up == true){

					for(int row = stcY-1; row > endcY; row--){
						if(game.gameboard[row][endcX] != null){
							return false;
						}
					}
				}
				else{
					for(int row = stcY+1; row < endcY; row++){
						if(game.gameboard[row][endcX] != null){
							return false;
						}
					}
				}
			}
			else{
				if(right == true){
					for(int row = stcX+1; row < endcX; row++){
						if(game.gameboard[endcY][row] != null){
							return false;
						}
					}
				}
				else{
					for(int row = stcX-1; row > endcX; row--){
						if(game.gameboard[endcY][row] != null){
							return false;
						}
					}
				}
			}
		}
		first = false;
		return true;
	}
}