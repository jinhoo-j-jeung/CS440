import java.util.Arrays;

public class Main {
    public static void main(String args[]){
        Board board = new Board();
        board.generateFig1();
        board.printGameboard();

        Gamelogic logic = new Gamelogic();

        /*
        Test for winningCheck
         */
        Pair pair = logic.winngCheck(board, 1);
        if (pair != null) {
            System.out.println("Row: " + pair.getRow() + " Column: " + pair.getCol() );
        }

        //TEST FOR FIVECHECKER
        /*Stone[] fives = Arrays.copyOfRange(board.getGameboard()[2],2,7);
        Pair pair = logic.fiveChecker(fives, 1, 2, 0);
        if (pair != null) {
            System.out.println("Row: " + pair.getRow() + " Column: " + pair.getCol() );
        }
        else {
            System.out.println("No result");
        }*/


        //test for getfives function
        /*Stone[] stones = logic.getFives(board.getGameboard(), 4, 4, "diagNeg", 2);

        for (int i = 0; i < stones.length; i ++) {
            if (stones[i] != null) {
                int row = stones[i].getPosition().getRow();
                int col = stones[i].getPosition().getCol();
                System.out.println("Row: " + row + ", Col: " + col);
            }
        }*/
    }
}
