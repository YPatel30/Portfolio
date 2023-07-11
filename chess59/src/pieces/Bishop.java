package pieces;
import board.Game;

/**
 * 
 * Bishop Class: Implements Bishop
 * 
 * @author Om Shah (os207)
 *
 */

public class Bishop extends Piece {

	public Bishop(boolean isWhite) {
		super(isWhite);
	}

    public String toString(){
		if(isWhite){
			return "wB";
		}
		return "bB";
	}

	/**
	 * checks if move is legal based on the coordinates
     * checks diagonality by seeing if the slope between the starting and finishing locations is 1
	 * checks if the piece is attacking, and if the piece it is attacking is on the same team 
	 * checks if there are pieces in the path from the starting point to the ending point
	 * 
	 * @author Om Shah (os207)
	 * 
	 * @param stc	starting coordinates of piece
	 * @param endc	ending coordinates of piece
	 * @param game	instance of a game
	 * @return true if the move is legal
	 * @return false if the move is illegal
	 */

	public boolean whenLegal(int[] stc, int[] endc, Game game) {
		boolean right, up;
		int stcX = stc[1];
		int stcY = stc[0];
		int endcX = endc[1];
		int endcY = endc[0];
		
		Piece finalPiece = game.gameboard[endcY][endcX];
		
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
		
		
		if(stcX == endcX || stcY == endcY){
			return false;
		}
		
		/*if absolute value of the difference between the initial x 
		values final x values and initial y values and final y values are not the same, then */
		
		if(Math.abs(stcX - endcX) != Math.abs(stcY - endcY)){
			return false;
		}
		
		
		if(finalPiece == null){
			if(up == true){
				if(right == true){
					int col = stcY-1;
					for(int row = stcX + 1; row < endcX; row++){
						if(game.gameboard[col][row] != null){
							return false;
						}
						col--;
					}
				}
				else{
					int col = stcY-1;
					for(int row = stcX-1; row > endcX; row--){
						if(game.gameboard[col][row] != null){
							return false;
						}
						col--;
					}
				}
			}
			else{
				if(right == true){
					int col = stcY + 1;
					for(int row = stcX + 1; row < endcX; row++){
						if(game.gameboard[col][row] != null){
							return false;
						}
						col++;
					}
				}
				else{
					int col = stcY + 1;
					for(int row = stcX- 1 ; row > endcX; row--){			
						if(game.gameboard[col][row] != null){
							return false;
						}
						col++;
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
			if(up == true){
				if(right == true){
					int col = stcY - 1;
					for(int row = stcX+1; row < endcX; row++){
						if(game.gameboard[col][row] != null){
							return false;
						}
							col--;
					}
				}
				else{
					int col = stcY - 1;
					for(int row = stcX-1; row > endcX; row--){
						if(game.gameboard[col][row] != null){
							return false;
						}
					}
				}
			}
			else{
				if(right == true){
					int col = stcY+1;
					for(int row = stcX+1; row < endcX; row++){
						if(game.gameboard[col][row] != null){
							return false;
						}
						col++;
					}
				}
				else{
					int col = stcY+1;
					for(int row = stcX-1; row > endcX; row--){
						if(game.gameboard[col][row] != null){
							return false;
						}
						col++;
					}
				}
			}
		}
		return true;
	}
	
	
}