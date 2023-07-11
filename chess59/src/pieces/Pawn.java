package pieces;

import board.Game;

/**
 * This class implements the Pawn piece
 * 
 * @author Om Shah (os207)
 *
 */
public class Pawn extends Piece {

	public Pawn(boolean isWhite) {
		super(isWhite);
	}
	
	/** 
	 * @return String
	 */
	
	public String toString(){
		if(isWhite){
			return "wp";
		}
		return "bp";
	}

	/**
	 * sees if move is legal by checking the coordinates 
	 * pawn can move up two spaces upon the first move 
	 * en passant is put into effect
	 * @author Om Shah (os207)
	 * 
	 * @param stc	shows starting coordinates 
	 * @param endc	shows ending coordinates
	 * @param game	provides an instance of game of valid moves 
	 * @return true if move is legal 
	 * @return false if move is illegal
	 */
	public boolean whenLegal(int[] stc, int[] endc, Game game) 
	{	
		int stcX = stc[1];
		int stcY = stc[0];
		int endcX = endc[1];
		int endcY = endc[0];
		
		Piece finalPiece = game.gameboard[endcY][endcX];


		if(finalPiece == null && endcX == stcX){
			if(isWhite){
				if(first && (endcY == (stcY - 1) || endcY == (stcY - 2))){

					if(endcY == stcY-2 && game.gameboard[endcY+1][endcX] != null){
						return false;
					}
					first = false;
					enPass = true;
					return true;
				}

				if(endcY == (stcY - 1)){
					return true;
				}
				return false;
			}
			else{
				if(first && (endcY == (stcY + 1) || endcY == (stcY + 2))){

					if(endcY == stcY + 2 && game.gameboard[endcY-1][endcX] != null){
						return false;
					}
					first = false;
					enPass = true;
					return true;
				}
				if(endcY == (stcY + 1)){
					return true;
				}
				return false;
			}
		}

		
		else{
			
			if(isWhite && stcY == 3 && Math.abs(stcX - endcX) == 1 && stcY - endcY == 1){

				if(game.gameboard[endcY][endcX] == null){
					Piece temp = game.gameboard[endcY+1][endcX];

					if(temp.enPass == true && !temp.isWhite){
						game.gameboard[endcY+1][endcX].dead = true;
						game.gameboard[endcY+1][endcX] = null;
						return true;
					}
				}
			}


			else if(!isWhite && stcY == 4 && Math.abs(stcX - endcX) == 1 && endcY - stcY == 1){
				if(game.gameboard[endcY][endcX] == null){
					Piece temp = game.gameboard[endcY-1][endcX];

					if(temp.enPass == true && temp.isWhite){
						game.gameboard[endcY-1][endcX].dead = true;
						game.gameboard[endcY-1][endcX] = null;
						return true;
					}
				}
			}
			
			if(game.gameboard[endcY][endcX] == null){
				return false;
			}


			if(isWhite && finalPiece.isWhite){
				return false;
			}

			if(!isWhite && !finalPiece.isWhite){
				return false;
			}
			
			if((stcX + 1) == endcX || (stcX - 1) == endcX){
				if((isWhite && endcY == (stcY - 1)) || (!isWhite && endcY == (stcY + 1))){
					return true;
			}

				
			return false;
			}
		}
		return false;
	}

}
