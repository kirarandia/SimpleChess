/*
 1. (1%) Initialization:
 a. Initialize the 8x8 chess board to the usual starting state of a chess game.
 Use a 2D array of Strings for this step.
 b. Prompt the user to choose the color (s)he wants to start the game with:
 black or white.
 c. Display the current status of the chess board, and the color that the user is
 currently playing for.
 2. (1%) Move Inputs:
 a. Prompt the user to make a move.
 b. The user has to specify the current location of the piece (the row index,
 and then the column index), and the desired destination of the piece (again,
 row and column index). For example, if the user specifies 0, 0, and then
 2,0, then (s)he wants to move the piece at location (0,0) to location (2,0),
 which is 2 squares vertically.
 c. Check for valid row and column indexes.
 3. (6.5%) Making the Move:
 The program needs to check the following to complete the move.
 a. Check if there is a piece of the correct color at the specified current
 location. If there isn’t, then inform the user and, go back to step 2.
 b. Check if the destination location is empty. If it is not, then inform the user
 and, go back to step 2.
 Check whether the resulting move is allowed for the piece. The following
 are the allowed set of moves: (Note: Each of the above rules should be
 implemented as a separate method.)
 i. A king can move one square in any direction (horizontally,
 vertically, or diagonally).
 ii. The queen can move any number of squares in any direction
 (vertically, horizontally, or diagonally).
 iii. The rook can move any number of squares horizontally or vertically.
 iv. The bishop can move any number of squares diagonally.
 v. The knight moves in an “L” shape. For example, two squares
 vertically and one square horizontally, or two squares horizontally
 and one square vertically.
 vi. The pawn may move one square forward.
 vii. A piece cannot jump over other pieces in the path of the move,
 except for the knight.
 c. If the requested move satisfies all the above requirements then complete
 the move, inform the user as such, and display the updated state of the
 chessboard.
 4. (0.25%) Switch the color that will now make a move, and inform the user about
 it.
 5. (0.25%) Ask the user if (s)he wants to continue playing or wants to end the game.
 If user chooses to continue then go back to Step 2 and continue from there, else
 exit.
 (0.5%) Include comments wherever appropriate.
 (0.5%) Your program needs to execute without errors, and produce the correct
 outcome(s).
 There are many places in this assignment where the use of methods will reduce code
 repetition. Thus you are encouraged to use methods throughout this assignment (not
 just in step 3 where methods are mandatory).
 */
package simplechess;

/**
 *
 * @author Kira
 */
import java.io.*;
import java.util.Scanner;

public class SimpleChess {

//    BufferedInputStream in = new BufferedInputStream(System.in);
    Scanner input = new Scanner(System.in);
    private final static boolean YES = true;//chess piece is called
    private final static boolean NO = false;//the cell is empty

    static String playerColor;

    String king;
    String queen;
    String rook;
    String bishop;
    String knight;
    String pawn;

    //empty chess pieces
    static String[][] emptyChessBoard = new String[][]{
        {" ", "-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8|"},
        {"1|", "b ", "w ", "b ", "w ", "b ", "w ", "b ", "w"},
        {"2|", "w ", "b ", "w ", "b ", "w ", "b ", "w ", "b"},
        {"3|", "b ", "w ", "b ", "w ", "b ", "w ", "b ", "w",},
        {"4|", "w ", "b ", "w ", "b ", "w ", "b ", "w ", "b"},
        {"5|", "b ", "w ", "b ", "w ", "b ", "w ", "b ", "w",},
        {"6|", "w ", "b ", "w ", "b ", "w ", "b ", "w ", "b"},
        {"7|", "b ", "w ", "b ", "w ", "b ", "w ", "b ", "w",},
        {"8|", "w ", "b ", "w ", "b ", "w ", "b ", "w ", "b"}
    };

    //8x8 chess board array, filled with initial chess pieces
    static String[][] chessBoard = new String[][]{
        {" ", "-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8|"},
        {"1|", "Rb", "kb", "Bb", "Qb", "Kb", "Bb", "kb", "Rb"},
        {"2|", "Pb", "Pb", "Pb", "Pb", "Pb", "Pb", "Pb", "Pb"},
        {"3|", "b ", "w ", "b ", "w ", "b ", "w ", "b ", "w"},
        {"4|", "w ", "b ", "w ", "b ", "w ", "b ", "w ", "b"},
        {"5|", "b ", "w ", "b ", "w ", "b ", "w ", "b ", "w"},
        {"6|", "w ", "b ", "w ", "b ", "w ", "b ", "w ", "b"},
        {"7|", "Pw", "Pw", "Pw", "Pw", "Pw", "Pw", "Pw", "Pw"},
        {"8|", "Rw", "kw", "Bw", "Qw", "Kw", "Bw", "kw", "Rw"}
    };

    public SimpleChess() {
        //deault constructor 
    }

    //prints out updated chessBoard
    public static void printChessBoard() {
        //SimpleChess c= new SimpleChess();
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard[i].length; j++) {
                System.out.print(chessBoard[i][j] + " ");
            }
            System.out.println();
        }
    }

    public boolean isValidMove(Pos x, Pos y/*,Color c*/) {
        //checks if move can be made by the chosen chess figure
        return true;
    }

    public void switchPlaces(Pos from, Pos to) {
        //copies strings from emptyChessBoard to chessBoard
        String temp = chessBoard[from.x][from.y];
        chessBoard[from.x][from.y] = emptyChessBoard[from.x][from.y];
        chessBoard[to.x][to.y] = temp;

        System.out.println("You moved " + temp + " from " + from + " to " + to);
        printChessBoard();

    }

    public boolean isSpotOccupied(int x, int y) {
        //inverts isSpotEmpty() method
        return !isSpotEmpty(x, y);
    }

    public boolean requireOccupied(int x, int y) {
        //checks if cell is empty
        if (isSpotEmpty(x, y)) {
            System.out.println("You are not moving a chess piece." + chessBoard[x][y] + " is empty. Try Again.");
            return false;
        }
        return true;
    }

    public boolean requireEmpty(int x, int y) {
        //checks if cell has a chess figure string
        if (!isSpotEmpty(x, y)) {
            System.out.println("There is a piece at " + chessBoard[x][y] + ". Try again.");
            return false;
        }
        return true;
    }

    public boolean isSpotEmpty(int x, int y) {
        //check if location has a cell color "b" or "w"
        if ("b ".equals(chessBoard[x][y]) || "w ".equals(chessBoard[x][y])) {
            return true;
        }
        return false;
    }

    public boolean requireSameColor(int x, int y) {
        if (!isMatchingColor(x, y)) {
            System.out.println("You are playing " + playerColor + ". You may only move pieces of that color.");
            return false;
        }
        return true;
    }

    public boolean isMatchingColor(int fromX, int fromY) {
        //checks if the player is trying to move the right color
        if ((chessBoard[fromX][fromY].contains("b") && playerColor.contains("b")) ||
            (chessBoard[fromX][fromY].contains("w") && playerColor.contains("w"))) {
            return true;
        }
        return false;
    }

    public void switchPlayer() {
        //switches color the player uses after each move
        if (playerColor.equals("b")) {
            playerColor = "w";
        } else {
            playerColor = "b";
        }
    }

    public String choosePlayer() {
        System.out.println("Choose color to start the game, w-white or b-black): ");
        String color = input.next();
        while (!("w".equals(color)) && !("b".equals(color))) {
            System.out.println("Enter the correct color to begin the game (black or white): ");
            color = input.next();
        }
        System.out.println("You are playing " + color + ".");///prints chosen color 

        return color;
    }

    //check valid coordinate values x,y
    public static boolean checkInputFor(int x, int y) {

        if ((x > 8) || (x < 1) || (y > 8) || (y < 1)) {
            System.out.println("Valid input is 1 through 8: Try again (x, y): ");
            return false;
        } else {
            return true;
        }
    }

    class Pos {
        public int x;
        public int y;
        public String toString() {
            return "(" + Integer.toString(x) + ", " + Integer.toString(y) + ")";
        }
    }

    public void promptInput(Pos pos, String prompt) {
        System.out.println(prompt);
        do {
            pos.x = input.nextInt();
            pos.y = input.nextInt();
        } while (!checkInputFor(pos.x, pos.y));
    }

    boolean isValidMovePos(Pos from, Pos to) {
        return  
                //checks if coordinates are empty
                requireOccupied(from.x, from.y) &&
                requireEmpty(to.x, to.y) && 
                //checks if the piece moved belongs to the player
                requireSameColor(from.x, from.y) &&
                //checks if the figure moved can move legally 
                isValidMove(from, to);
    }
    
    public boolean promptMove(Pos from, Pos to) {
        do {
            promptInput(from, "Enter current location (row, column) of the piece you want to move: ");
            promptInput(to, "Enter destination (row, column): ");
        } while (!isValidMovePos(from, to));
        return true;
    }

    public void play() {
        Pos from = new Pos();
        Pos to = new Pos();
        printChessBoard();
        while(promptMove(from, to)) {
            switchPlaces(from, to);
            switchPlayer();
        }
    }
    
    public static void main(String[] args) {
        SimpleChess chess = new SimpleChess();

        playerColor = chess.choosePlayer();//never gets called again
        chess.play();
    }
}
