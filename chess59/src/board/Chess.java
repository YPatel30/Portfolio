package board;

import java.util.Scanner;
import java.lang.System;
import pieces.*;

/**
 * Chess class: scans and validates input, runs game (main class) 
 *
 * @author Yash Patel (ykp15)
 * @author Om Shah (os207)
 */

public class Chess {

	public static void main(String[] args) {
		
		Game game = new Game();
		boolean isWhiteTurn = true;
		
        Scanner scan = new Scanner(System.in);

		while(true){

			game.simulateBoard();

			if(isWhiteTurn){
				game.enPassPoss(true);
				System.out.print("White's move: ");
			}else{
				game.enPassPoss(false);
				System.out.print("Black's move: ");
			}
			String input = scan.nextLine();
			
			while(!validMove(input)){
                System.out.print("Not valid input. Try again: ");
                input = scan.nextLine();
            }
            
            
            if("resign".equals(input)){
                if(!isWhiteTurn)System.out.print("White wins");
                else System.out.print("Black wins");
                return;
            }else if(input.contains("draw?")){
                    System.out.println("Draw");
                    return;
            }
			
			String start = input.substring(0, 2);
			String end = input.substring(3);
			
			int[] startPosition = game.gettingIndex(start);
			int[] endPosition = game.gettingIndex(end);

			while(!game.activePiece  (startPosition, endPosition, isWhiteTurn)){
				System.out.print("Illegal move, try again: ");

                input = scan.nextLine();
                while(!validMove(input)){
                    System.out.println();
                    System.out.print("Not valid input. Try again: ");
                    input = scan.nextLine();
                }
                
                if("resign".equals(input)){
                    if(!isWhiteTurn)System.out.print("White wins");
                    else System.out.print("Black wins");
                    return;
                }else if(input.contains("draw?")){
            
                        System.out.println("Draw");
                        return;
                    
                }
				
				start = input.substring(0, 2);
				end = input.substring(3);

				startPosition = game.gettingIndex(start);
				endPosition = game.gettingIndex(end);
			}
			
			Piece endPiece = game.gameboard[endPosition[0]][endPosition[1]];
			
			if(Pawn.class.isInstance(endPiece)){
				char type = '0';
				char color = '0';
				if(input.length() == 7) type = input.charAt(6);
				if(endPosition[0] == 0 && endPiece.isWhite) color = 'W';
				else if(endPosition[0] == 7 && !endPiece.isWhite) color = 'B';
				
				Piece promoted = promote(type, color); 
				if(promoted != null){
					game.gameboard[endPosition[0]][endPosition[1]] = promoted;
				}
			}

            
			if(game.inCheck(endPosition, true) && !isWhiteTurn){
				
				if(game.inCheckmate(true)){
					game.simulateBoard();
					System.out.println("Checkmate");
					System.out.print("Black wins");
					return;
				} else {
					System.out.println("Check");
				}
				
			}else if(game.inCheck(endPosition, false) && isWhiteTurn){
				
				if(game.inCheckmate(false)){
					game.simulateBoard();
					System.out.println("Checkmate");
					System.out.print("White wins");
					return;
				} else {
					System.out.println("Check");
				}
			}
			isWhiteTurn = !isWhiteTurn;
			
        }
	}

	/**
	 * validMove() method: examines the user input to make sure it is valid
	 * correct format is a letter from a to h paired with a number from 1 to 8 with a space and then another pairing 
     * of a letter from a to h with a number from 1 to 8
     * 
	 * @author Yash Patel (ykp15)
	 * @param input	a string that a user inputs in order to make a move, resign or draw
	 * @return true if the input is valid, false otherwise
	 */
	
	public static boolean validMove(String input){
		
		input = input.toLowerCase();
		if(input.length() == 0){
            return false;
        }

		if("resign".equals(input)){
            return true;
        }


		if("draw?".equals(input)){
            return true;
        }

        if(!Character.isAlphabetic(input.charAt(0)) || !Character.isAlphabetic(input.charAt(3))){
            return false;
        } 

        if(input.charAt(0) < 'a' || input.charAt(0) > 'h') {
            return false;
        }

        if(!Character.isDigit(input.charAt(1)) || !Character.isDigit(input.charAt(4))){
            return false;
        }

        if(input.charAt(1) < '1' || input.charAt(1) > '8'){
            return false;
        }
        
        return true;

	}

	/**
	 * promote() method: given a particular piece type and color, return the appropriate promoted piece type and color  
	 *
	 * @author Om Shah (os207)
	 * @param piece specify between Pawn, Rook, Bishop, Knight, and Queen
	 * @param color	specify white or black

	 * @return returns the particular piece to promote with the specified color and type
	 */

	private static Piece promote(char piece, char color) {
		if(color == '0') return null;
		
		boolean white;
		if(color == 'W') 
			white = true;
		else
			white = false;
		
		if((piece) == 'R' || (piece) == 'r')
			return new Rook(white);
		if((piece) == 'N' || (piece) == 'n')
			return new Knight(white);
		if((piece) == 'B' || (piece) == 'b')
			return new Bishop(white);
		if((piece) == 'P' || (piece) == 'p')
			return new Pawn(white);
		
		return new Queen(white);
	}
		
}
