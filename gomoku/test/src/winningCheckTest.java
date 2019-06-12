
public class winningCheckTest {
    public static void main(String args[]){
        Board board1 = new Board();
        board1.generateFig1();
        board1.printGameboard();

        Gamelogic logic = new Gamelogic();

        Pair pair = logic.winngCheck(board1, 1);
        if (pair != null) {
            System.out.println("Row: " + pair.getRow() + " Column: " + pair.getCol() );
        }

        Board board2 = new Board();
        board2.generateFig2();
        board2.printGameboard();

        pair = logic.blockWinner(board2, 1);
        if (pair != null) {
            System.out.println("Row: " + pair.getRow() + " Column: " + pair.getCol() );
        }

        Board board3 = new Board();
        board3.generateFig3();
        board3.printGameboard();

        pair = logic.threeCheck(board3, 1);
        if (pair != null) {
            System.out.println("Row: " + pair.getRow() + " Column: " + pair.getCol() );
        }
        Board board4 = new Board();
        board4.generateFig4();
        board4.printGameboard();

        pair = logic.winningBlock(board4, 2);
        if (pair != null) {
            System.out.println("Row: " + pair.getRow() + " Column: " + pair.getCol() );
        }
        /*Board board5 = new Board();
        board5.generateFig5();
        board5.printGameboard();
        //board4.genera
        pair = logic.firstMove(board5, 1);
        if (pair != null) {
            System.out.println("Row: " + pair.getRow() + " Column: " + pair.getCol());
        }*/

        Board board6 = new Board();
        board6.generateFig6();
        board6.printGameboard();

        pair = logic.winngCheck(board6, 1);
        if (pair != null) {
            System.out.println("Row: " + pair.getRow() + " Column: " + pair.getCol() );
        }

        Board board7 = new Board();
        board7.generateFig7();
        board7.printGameboard();

        pair = logic.winngCheck(board7, 1);
        if (pair != null) {
            System.out.println("Row: " + pair.getRow() + " Column: " + pair.getCol() );
        }
/*
        Board board8 = new Board();
        board8.generateFig5();
        GameManager manager = new GameManager(board8);
        Board result = manager.play();
        result.printGameboard();*/

        /*Board test = new Board();
        test.generateFigRealGame();
        GameManager manager2 = new GameManager(test);
        Board testResult = manager2.play();
        testResult.printGameboard();*/


/*        Board test2 = new Board();
        test2.generateFig5();
        GameManager manager3 = new GameManager(test2);
        Board testResult1 = manager3.minimaxreflexplay(1);
        testResult1.printGameboard();*/
/*
        Board test3 = new Board();
        test3.generateFig5();
        test3.gameboard[0][2].setPlayer(2);
        GameManager manager4 = new GameManager(test3);
        Board testResult2 = manager4.alphabetareflex(1);
        testResult2.printGameboard();*/

        int winningCount = 0;
        int trial = 0;
        for (int i = 0 ; i < 7; i ++) {
            for (int j = 0 ; j < 7; j++) {
                trial ++;
                Board test4 = new Board();
                test4.generateFig5();
                test4.gameboard[i][j].setPlayer(2);
                GameManager manager5 = new GameManager(test4);
                Board testResult3 = manager5.alphabetareflex(1);
                MinimaxLogic minimaxLogic = new MinimaxLogic();
                if (minimaxLogic.winningCase(testResult3, 1) == 10000) {
                    winningCount++;
                }
                System.out.println("Cur wining count : " + winningCount);
                System.out.println("Cur trial : " + trial);
            }
        }
        System.out.println("Total winning count: " + winningCount);
    }

}
