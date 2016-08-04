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
import java.util.Scanner;

public class SimpleChess {

//    BufferedInputStream in = new BufferedInputStream(System.in);
    Scanner input = new Scanner(System.in);

    static String playerColor;

    public static final String KING = "K";
    public static final String QUEEN = "Q";
    public static final String ROOK = "R";
    public static final String BISHOP = "B";
    public static final String KNIGHT = "N";
    public static final String PAWN = "P";

    public static final String WHITE = "w";
    public static final String BLACK = "b";

    public static final String[] FIGURES = new String[]{ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK};
    public static final int BOARD_SIZE = 8;

    //8x8 chess board array, filled with initial chess pieces
    static String[][] chessBoard = new String[BOARD_SIZE][BOARD_SIZE];

    String getCell(int x, int y) {
        return chessBoard[y][x];
    }
    
    void setCell(int x, int y, String val) {
        chessBoard[y][x] = val;
    }
    
    String emptyCell(int x, int y) {
        String f;
        if ((x + y) % 2 == 0) {
            f = BLACK + BLACK;
        } else {
            f = WHITE + WHITE;
        }
        return f;
    }
    
    
    void fillChessBoard() {
        String f;
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                switch (y) {
                case 0:
                    f = FIGURES[x] + WHITE;
                    break;
                case 1:
                    f = PAWN + WHITE;
                    break;
                case 6:
                    f = PAWN + BLACK;
                    break;
                case 7:
                    f = FIGURES[x] + BLACK;
                    break;
                default:
                    f = emptyCell(x,y);
                    break;
                }
                setCell(x,y,f);
            }
        }
    }

    public void printChessBoard() {
        //prints out updated chessBoard string
        for (int y = BOARD_SIZE - 1; y >= 0; y--) {
            System.out.print((y+1) + "  ");
            for (int x = 0; x < BOARD_SIZE; x++) {
                System.out.print(getCell(x, y) + " ");
            }
            System.out.println();
        }
        System.out.print("   ");
        for (int x = 0; x < BOARD_SIZE; x++) {
            System.out.print(" " + (x+1) + " ");
        }
        System.out.println();
    }

    class MoveException extends Exception {

        MoveException(String why) {
            msg = why;
        }
        final String msg;

        public String toString() {
            return msg;
        }
    }

    boolean possiblePath(Pos from, Pos to) {
        //checks if Figure has the ability to move: diagonally, horizontally or vertically 
        int dX = Math.abs(from.x - to.x);
        int dY = Math.abs(from.y - to.y);

        return (dX == dY) || (dX == 0) || (dY == 0);
    }

    boolean isBlockedPath(Pos from, Pos to) {
        //chekcs if the move has obsticles on its path
        Pos current = new Pos(from);
        Pos step = new Pos();

        if (!possiblePath(from, to)) {
            //path is impossible
            return false;
        }
        //path is possible, determine the direction 
        int dx = to.x - from.x;
        int dy = to.y - from.y;

        //increments by 1 horizontally
        if (dx > 0) {
            step.x = 1;
        }
        //decrements by 1 horizontally
        if (dx < 0) {
            step.x = -1;
        }
        //increments by 1 vertically
        if (dy > 0) {
            step.y = 1;
        }
        //decrements by 1 vertically 
        if (dy < 0) {
            step.y = -1;
        }
        //doesn't move horizontally
        if (dx == 0) {
            step.x = 0;
        }
        //doesn't move vertically
        if (dy == 0) {
            step.y = 0;
        }
        /*moves piece one step foward to it's destination(to) until isSpotEmpty is false
         and until current(x,y) reached destination(to)*/
        while (!current.equals(to)) {
            current.add(step);
            if (!isSpotEmpty(current.x, current.y)) {
                return false;
            }
        }

        return true;
    }

    boolean isRookMove(Pos from, Pos to) {
        //
        int dX = Math.abs(from.x - to.x);
        int dY = Math.abs(from.y - to.y);

        if (!(dX == 0) || !(dY == 0)) {
            return false;
        }
        return isBlockedPath(from, to);
    }

    boolean isPawnMove(Pos from, Pos to) {
        //
        int dX = to.x - from.x;
        int dY = to.y - from.y;
        int dir = 1;

        if (playerColor.contains(WHITE)) {
            dir = 1;
        } else if (playerColor.contains(BLACK)) {
            dir = -1;
        }
        //check isBlockedPath if Figure is moving forward by 1 cell
        if (dX != 0 || dY != dir) {
            return false;
        }
        return isBlockedPath(from, to);
    }

    boolean isKingMove(Pos from, Pos to) {
        //
        int dX = Math.abs(from.x - to.x);
        int dY = Math.abs(from.y - to.y);
        //check isBlockedPath if Figure is moving by 1 cell in every direction 
        if (!(dX == 1) && !(dY == 1)) {
            return false;
        }
        return isBlockedPath(from, to);
    }

    boolean isBishopMove(Pos from, Pos to) {
        int dX = Math.abs(from.x - to.x);
        int dY = Math.abs(from.y - to.y);
        //rule bishops may ONLY move diagonally, pass to isBlockedPath if true
        if (!(dX == dY)) {
            return false;
        }
        return isBlockedPath(from, to);
    }

    boolean isQueenMove(Pos from, Pos to) {
        //can move in any path
        return isBlockedPath(from, to);
    }

    boolean isKnightMove(Pos from, Pos to) {
        int dX = Math.abs(from.x - to.x);
        int dY = Math.abs(from.y - to.y);

        //if the min value of either x or y is 2, then the maximum value of y,x is 3
        if (!(Math.min(dX, dY) == 1 && Math.max(dX, dY) == 2)) {
            return false;
        }
        return true;
    }

    String getFigureCode(Pos pos) {
        //gets the first character of the String of the coordinats passed from class Pos
        return getCell(pos.x,pos.y).substring(0, 1);
    }

    boolean isValidMove(Pos from, Pos to) {
        //matches the getFigureCode with the chessFigure
        boolean result = false;
        switch (getFigureCode(from)) {
            case ROOK:
                result = isRookMove(from, to);
                break;
            case KING:
                result = isKingMove(from, to);
                break;
            case QUEEN:
                result = isQueenMove(from, to);
                break;
            case KNIGHT:
                result = isKnightMove(from, to);
                break;
            case BISHOP:
                result = isBishopMove(from, to);
                break;
            case PAWN:
                result = isPawnMove(from, to);
                break;
            default:
                // error : total garbage
                System.out.println("The entry is not valid.");
                break;
        }
        if (!result) {
            System.out.println("Failed on rule for " + getFigureCode(from) + "; " + from + to + " passed through.");
        }
        return result;
    }

    public boolean isSpotEmpty(int x, int y) {
        //check if location has a cell color Black or WHITE
        if ((BLACK+BLACK).equals(getCell(x,y)) || (WHITE+WHITE).equals(getCell(x,y))) {
            return true;
        }
        return false;
    }

    public boolean isMatchingColor(int fromX, int fromY) {
        //checks if the player is trying to move the right color
        if ((getCell(fromX,fromY).contains(BLACK) && playerColor.contains(BLACK))
                || (getCell(fromX,fromY).contains(WHITE) && playerColor.contains(WHITE))) {
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

    public boolean requireOccupied(int x, int y) {
        //checks if cell is empty
        if (isSpotEmpty(x, y)) {
            System.out.println("You are not moving a chess piece. Cell is empty. Try Again.");
            return false;
        }
        return true;
    }

    public boolean requireEmpty(int x, int y) {
        //checks if cell has a chess figure string
        if (!isSpotEmpty(x, y)) {
            System.out.println("There is a piece at " + getCell(x,y) + ". Try again.");
            return false;
        }
        return true;
    }

    boolean isValidMovePos(Pos from, Pos to) {
        return //checks if coordinates are empty
                (requireOccupied(from.x, from.y)
                && requireEmpty(to.x, to.y)
                && //checks if the piece moved belongs to the player
                requireSameColor(from.x, from.y)
                && //checks if the figure moved can move legally 
                isValidMove(from, to));
    }

    public static boolean checkInputFor(int x, int y) {
        //check valid coordinate values x,y
        if ((x > 8) || (x < 1) || (y > 8) || (y < 1)) {
            System.out.println("Valid input is 1 through 8: Try again (x, y): ");
            return false;
        } else {
            return true;
        }
    }

    void promptInput(Pos pos, String prompt) {
        //promts input passing the Pos(x,y) and String question
        System.out.println(prompt);
        do {
            pos.x = input.nextInt();
            pos.y = input.nextInt();
            //checking input is 1-8
        } while (!checkInputFor(pos.x, pos.y));
        pos.x --;
        pos.y --;
    }

    boolean promptMove(Pos from, Pos to) {
        //prompts user for input until the move of the chessFigure is valid
        do {
            promptInput(from, "Enter current location (row, column) of the piece you want to move: ");
            promptInput(to, "Enter destination (row, column): ");
            //checks if the input positions are possible
        } while (!isValidMovePos(from, to));
        return true;
    }

    void switchPlaces(Pos from, Pos to) {
        //copies strings from emptyChessBoard to chessBoard
        String temp = getCell(from.x, from.y);
        setCell(from.x, from.y, emptyCell(from.x, from.y));
        setCell(to.x, to.y, temp);

        System.out.println("You moved " + temp + " from " + from + " to " + to);
        printChessBoard();

    }

    public void play() {
        //upper level method that initiates the game
        Pos from = new Pos();
        Pos to = new Pos();
        printChessBoard();
        //after promptMove completes a valid move, playerColor field switches color
        while (promptMove(from, to)) {

            switchPlaces(from, to);
            switchPlayer();//prompts another player to move
            System.out.println("Player: " + playerColor);
        }
    }

    public void switchPlayer() {
        //switches color the player used during a move
        if (playerColor.equals(BLACK)) {
            playerColor = WHITE;
        } else {
            playerColor = BLACK;
        }
    }

    class Pos {

        //creates x/y variables used to instantiate input 
        public int x;
        public int y;

        Pos() {
            //default constructor
            x = 0;
            y = 0;
        }

        Pos(Pos other) {
            x = other.x;
            y = other.y;
        }

        void add(Pos p) {
            //add method
            x += p.x;
            y += p.y;
        }

        boolean equals(Pos pos) {
            //
            return x == pos.x && y == pos.y;
        }

        @Override
        public String toString() {
            //returns a string that prints x/y indecies 
            return "(" + Integer.toString(x+1) + ", " + Integer.toString(y+1) + ")";
        }
    }

    public String choosePlayer() {
        fillChessBoard();
        System.out.println("Choose color to start the game, w-white or b-black): ");
        String color = input.next();
        while (!(WHITE.equals(color)) && !(BLACK.equals(color))) {
            System.out.println("Enter the correct color to begin the game (black or white): ");
            color = input.next();
        }
        System.out.println("You are playing " + color + ".");///prints chosen color 

        return color;
    }

    public static void main(String[] args) {
        SimpleChess chess = new SimpleChess();

        playerColor = chess.choosePlayer();
        chess.play();
    }
}
