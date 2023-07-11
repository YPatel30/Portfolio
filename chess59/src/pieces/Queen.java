package pieces;

import board.Game;

/**
 * Queen Class: implements the queen
 * 
 * @author Yash Patel (ykp15)
 */
public class Queen extends Piece {

	public Queen(boolean isWhite) {
		super(isWhite);
	}

	public String toString(){
		if(isWhite){
			return "wQ";
		}
		return "bQ";
	}
	/**
	 * sees if move is legal 
	 * sees if the move is possible in a diagonal fashion 
	 * 
	 * @author Yash Patel (ykp15)
	 * 
	 * @param stc	shows starting coordinates 
	 * @param endc	shows ending coordnates
	 * @param game	instance of a game
	 * @return true if move is legal
	 * @return false if move is illegal
	 */
	
	public boolean whenLegal(int[] stc, int[] endc, Game game) {
		boolean right, up, vertical;
		int stcX = stc[1];
		int stcY = stc[0];
		int endcX = endc[1];
		int endcY = endc[0];
		
		Piece destinationPiece  = game.gameboard[endcY][endcX];
		
		if(stcX == endcX && stcY == endcY){
			return false;
		}
		
		if(stcX != endcX && stcY != endcY){
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
			
			 
			if(Math.abs(stcX - endcX) != Math.abs(stcY - endcY)){
				return false;
			}
			
			
			if(destinationPiece  == null){
				if(up == true){
					if(right == true){
						int col = stcY - 1;
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
						for(int row = stcX+1; row < endcX; row++){
							if(game.gameboard[col][row] != null){
								return false;
							}
							col++;
						}
					}
					else{
						int col = stcY + 1;
						for(int row = stcX - 1; row > endcX; row--){			
							if(game.gameboard[col][row] != null){
								return false;
							}
							col++;
						}
					}
				}
			}
			
			else{
				if(isWhite && destinationPiece .isWhite){
					return false;
				}
				if(!isWhite && !destinationPiece .isWhite){
					return false;
				}
				if(up == true){
					if(right == true){
						int col = stcY-1;
						for(int row = stcX+1; row < endcX; row++){
								if(game.gameboard[col][row] != null){
									return false;
								}
								col--;
						}
					}
					else{
						int col = stcY - 1;
						for(int row = stcX - 1; row > endcX; row--){
								if(game.gameboard[col][row] != null){
									return false;
								}
							
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
						for(int row = stcX - 1; row > endcX; row--){
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
		else if(stcX == endcX || stcY == endcY){
			if(stcX == endcX && stcY != endcY){
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
			
			if(stcY > endcX){
				up = true;
			}
			else{
				up = false;
			}
			
			
			if(destinationPiece  == null){
				if(vertical == true){
					if(up == true){
						for(int col = stcY - 1; col > endcY; col--){
							if(game.gameboard[col][endcX] != null){
								return false;
							}
						}
					}
					else{
						for(int col = stcY + 1; col < endcY; col++){
							if(game.gameboard[col][endcX] != null){
								return false;
							}
						}
					}
				}
				else{
					if(right == true){
						for(int row = stcX + 1; row < endcX; row++){
							if(game.gameboard[endcX][row] != null){
								return false;
							}
						}
					}
					else{
						for(int row = stcX - 1; row > endcX; row--){
							if(game.gameboard[endcY][row] != null){
								return false;
							}
						}
					}
				}
			}
			else{
				if(isWhite && destinationPiece .isWhite){
					return false;
				}
				if(!isWhite && !destinationPiece .isWhite){
					return false;
				}
				if(vertical == true){
					if(up == true){
						for(int col = stcY - 1; col > endcY; col--){
							if(game.gameboard[col][endcX] != null){
								return false;
							}
						}
					}
					else{
						for(int col = stcY + 1; col < endcY; col++){
							if(game.gameboard[col][endcX] != null){
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
						for(int row = stcX - 1; row > endcX; row--){
							if(game.gameboard[endcY][row] != null){
								return false;
							}
						}
					}
				}
			}
			return true;
		}
		else{
			return false;
		}
	}
}