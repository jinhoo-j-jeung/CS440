public class Board {
    public Stone[][] gameboard = new Stone[7][7];


    public Board()
    {
        for(int row =0; row<7; row++)
        {
            for(int col = 0; col < 7 ; col++)
            {
                Stone stone = new Stone(row,col,-1);
                gameboard[row][col] = stone;
            }
        }
    }

    public void setStone(Stone p1) {
        int x_pos = p1.getPosition().getRow();
        int y_pos = p1.getPosition().getCol();
        if(gameboard[x_pos][y_pos].getPlayer() == -1) {
            gameboard[x_pos][y_pos] = p1;
        }
        else {
            System.out.println("not available");
        }
    }

    public Stone[][] getGameboard() {
        return gameboard;
    }


    /**
     * Generate board which is same as figure 1
     */
    public void generateFig1 () {
        this.gameboard[2][1] = new Stone(2, 1, 1);
        this.gameboard[2][2] = new Stone(2, 2, 1);
        this.gameboard[2][3] = new Stone(2, 3, 1);
        this.gameboard[2][4] = new Stone(2, 4, 1);

        this.gameboard[3][1] = new Stone(3, 1, 2);
        this.gameboard[3][3] = new Stone(3, 3, 2);

        this.gameboard[4][3] = new Stone(4, 3, 2);
        this.gameboard[4][4] = new Stone(4, 4, 2);

        this.gameboard[6][2] = new Stone(6, 2, 2);
    }

    public void generateFig2 () {
        this.gameboard[2][0] = new Stone(2, 0, 1);
        this.gameboard[2][1] = new Stone(2, 1, 1);

        this.gameboard[3][0] = new Stone(3, 0, 1);
        this.gameboard[3][1] = new Stone(3, 1, 2);
        this.gameboard[3][2] = new Stone(3, 2, 2);
        this.gameboard[3][3] = new Stone(3, 3, 2);
        this.gameboard[3][4] = new Stone(3, 4, 2);
    }

    public void generateFig3 () {
        this.gameboard[1][1] = new Stone(1, 1, 1);
        this.gameboard[4][2] = new Stone(4, 2, 1);

        this.gameboard[3][2] = new Stone(3, 2, 2);
        this.gameboard[3][3] = new Stone(3, 3, 2);
        this.gameboard[3][4] = new Stone(3, 4, 2);
    }

    public void generateFig4(){
        this.gameboard[0][1] = new Stone(0, 1, 1);
        this.gameboard[2][2] = new Stone(2, 2, 1);

        this.gameboard[2][5] = new Stone(2, 5, 1);
        this.gameboard[4][1] = new Stone(4, 1, 2);
        this.gameboard[5][1] = new Stone(5, 1, 2);
        this.gameboard[5][2] = new Stone(5, 2, 2);
    }

    public void generateFig5 () {

    }
    public void generateFig6 () {
        this.gameboard[1][2] = new Stone(1, 2, 1);
        this.gameboard[2][2] = new Stone(2, 2, 1);
        this.gameboard[3][2] = new Stone(3, 2, 1);
        this.gameboard[4][2] = new Stone(4, 2, 1);

        this.gameboard[3][1] = new Stone(3, 1, 2);
        this.gameboard[3][3] = new Stone(3, 3, 2);

        this.gameboard[4][3] = new Stone(4, 3, 2);
        this.gameboard[4][4] = new Stone(4, 4, 2);
    }

    public void generateFig7 () {
        this.gameboard[1][1] = new Stone(1, 1, 1);
        this.gameboard[2][2] = new Stone(2, 2, 1);

        this.gameboard[3][3] = new Stone(3, 3, 1);
        this.gameboard[4][4] = new Stone(4, 4, 1);
    }

    public void generateFigRealGame () {
        this.gameboard[1][5] = new Stone(1, 5, 1);
        this.gameboard[6][1] = new Stone(6, 1, 2);
    }
    public void printGameboard() {
        for (int i = 0 ; i < gameboard.length; i++) {
            for (int j = 0 ; j < gameboard[0].length; j++){
                if (gameboard[i][j].getPlayer() != -1) {
                    if (gameboard[i][j].getPlayer() == 1) {
                        System.out.print("|_O_");
                    }
                    else if(gameboard[i][j].getPlayer() == 2) {
                        System.out.print("|_X_");
                    }
                }
                else {
                    System.out.print("|___");
                }
            }
            System.out.println("|");
        }
    }
}
