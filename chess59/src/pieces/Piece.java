package pieces;

import board.Game;

/**
 * Piece abstract class: overrides and implements the whenLegal() method
 * 
 * @author Yash Patel (ykp15)
 */

public abstract class Piece {
	
	public boolean isWhite;
	public boolean enPass = false; 
	boolean dead = false;
	boolean first = true;
	
	
	public Piece(boolean isWhite){
		this.isWhite = isWhite;
	}

	/**
	 * Abstract whenLegal() method: checks whether or not a move is legal
	 * 
	 * @author Yash Patel (ykp15)
	 * @param stc	starting coordinates of piece
	 * @param endc	ending coordinates of piece
	 * @param game	instance of a game

	 * @return if move is legal, it will return true. if move is illegal, it will return false.
	 */	

	public abstract boolean whenLegal(int[] stc, int[] endc, Game game);

	}
