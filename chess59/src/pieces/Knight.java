package pieces;

import board.Game;

/**
 * Knight Class: Implements Knight 
 * 
 * @author Yash Patel (ykp15)
 *
 */

public class Knight extends Piece {

	public Knight(boolean isWhite) {
		super(isWhite);
	}

	public String toString(){
		if(isWhite){
			return "wN";
		}
		return "bN";
	}

	/**
	 * checks if move is legal based on the coordinates
	 * Makes sure the move is in a "L" shape and not attacking a friendly piece.
	 * 
	 * @author Yash Patel (ykp15)
	 * 
	 * @param stc	starting coordinates of piece
	 * @param endc	ending coordinates of piece 
	 * @param game	an instance of a game 
	 * @return true if move is legal
	 * @return false if move is illegal
	 */

	public boolean whenLegal(int[] stc, int[] endc, Game game) {
		int stcX = stc[1];
		int stcY = stc[0];
		int endcX = endc[1];
		int endcY = endc[0];
		
		Piece finalPiece = game.gameboard[endcY][endcX];
		if(finalPiece == null){

			if(stcX + 1 == endcX || stcX -1 == endcX){
				if(stcY - 2 == endcY || stcY + 2 == endcY){
					return true;
				}
			}

			else if(stcX + 2 == endcX || stcX -2 == endcX){

				if(stcY -1 == endcY || stcY +1 == endcY){
					return true;
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
			if(stcX + 1 == endcX || stcX - 1 == endcX){

				if(stcY - 2 == endcY || stcY + 2 == endcY){
					return true;
				}
			}
			else if(stcX + 2 == endcX || stcX -2 == endcX){

				if(stcY -1 == endcY || stcY +1 == endcY){
					return true;
				}
			}
		}
		return false;
	}

}