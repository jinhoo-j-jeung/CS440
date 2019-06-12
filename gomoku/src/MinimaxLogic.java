import java.util.*;

public class MinimaxLogic {

    public int case1(Board board, int player) {
        Stone[][] game_board = board.getGameboard();
        Vector<Pair> pair_vector = new Vector<>();
        for(int row=0; row < 7; row++) // row check
        {
            for(int j = 0; j < 3; j++)
            {
                Stone[] fives = Arrays.copyOfRange(game_board[row],j,j+5);
                addValidPair(player, pair_vector, row, j, fives);
            }
        }
        for(int col=0; col < 7; col++) // col check
        {
            for(int j = 0; j < 3; j++)
            {
                Stone[] fives = getFives(game_board,col,j,"col", player);
                addValidPair(player, pair_vector, j, col, fives);
            }

        }

        //CHECK DIAGONAL
        for(int row = 4; row < 7; row ++) {
            for (int col = 0; col < 7; col++) {
                Stone[] fives;
                if (col == 3) {
                    continue;
                }
                else if (col >= 0 && col < 3) {
                    fives = getFives(game_board, col, row,"diagPos", player);

                }
                else {
                    fives = getFives(game_board, col, row,"diagNeg", player);
                }
                addValidPair(player, pair_vector, row, col, fives);
            }
        }

        return pair_vector.size() * 1500;
    }

    public int winningCase(Board board, int player) {
        boolean found = false;
        Stone[][] game_board = board.getGameboard();
        Vector<Pair> pair_vector = new Vector<>();
        for(int row=0; row < 7; row++) // row check
        {
            for(int j = 0; j < 3; j++)
            {
                Stone[] fives = Arrays.copyOfRange(game_board[row],j,j+5);
                if(addWinning(player, row, j, fives))
                {
                    found = true;
                    break;
                }
            }

        }
        for(int col=0; col < 7; col++) // col check
        {
            for(int j = 0; j < 3; j++)
            {
                Stone[] fives = getFives(game_board,col,j,"col", player);
                if(addWinning(player, j, col, fives))
                {
                    found= true;
                    break;
                }
            }

        }

        //CHECK DIAGONAL
        for(int row = 4; row < 7; row ++) {
            for (int col = 0; col < 7; col++) {
                Stone[] fives;
                if (col == 3) {
                    continue;
                }
                else if (col >= 0 && col < 3) {
                    fives = getFives(game_board, col, row,"diagPos", player);

                }
                else {
                    fives = getFives(game_board, col, row,"diagNeg", player);
                }
                if(addWinning(player, row, col, fives))
                {
                    found = true;
                    break;
                }
            }
        }

        if(found == true)
            return 10000;
        else
            return 0;
    }

    private boolean addWinning(int player, int row, int j, Stone[] fives) {
        for(int i = 0 ; i < 5 ; i++)
        {
            if(fives[i].getPlayer() != player)
            {
                return false;
            }
        }
        return true;
    }

    public int case4(Board board, int player) {
        Stone[][] game_board = board.getGameboard();
        Vector<Stone[]> arr_vector = new Vector<>();
        for(int row=0; row < 7; row++) // row check
        {
            for(int j = 0; j < 3; j++)
            {
                Stone[] fives = Arrays.copyOfRange(game_board[row],j,j+5);
                addWinningBlock(player, arr_vector, row, j, fives);
            }

        }
        for(int col=0; col < 7; col++) // col check
        {
            for(int j = 0; j < 3; j++)
            {
                Stone[] fives = getFives(game_board,col,j,"col", player);
                addWinningBlock(player, arr_vector, col, j, fives);
            }

        }

        //CHECK DIAGONAL
        for(int row = 4; row < 7; row ++) {
            for (int col = 0; col < 7; col++) {
                Stone[] fives;
                if (col == 3) {
                    continue;
                }
                else if (col >= 0 && col < 3) {
                    fives = getFives(game_board, col, row,"diagPos", player);

                }
                else {
                    fives = getFives(game_board, col, row,"diagNeg", player);
                }
                addWinningBlock(player, arr_vector, row, col, fives);
            }
        }

        Comparator<Stone[]> comparator = new arrayComparator();
        Collections.sort(arr_vector, comparator);

        Vector<Pair> result = new Vector<>();
        if (arr_vector.size() > 0) {
            int max_length =0;
            for(int k = 0; k < arr_vector.elementAt(0).length; k++)
            {
                if(arr_vector.elementAt(0)[k].getPlayer() != -1)
                    max_length++;
            }
            for(int i = 0; i < arr_vector.size() ; i++)
            {
                int temp= 0;
                Vector<Pair> temp_vector = new Vector<>();
                for(int j =0; j < 5; j++)
                {
                    if(arr_vector.elementAt(i)[j].getPlayer() != -1)
                    {
                        temp++;

                    }
                    else
                    {
                        temp_vector.add(arr_vector.elementAt(i)[j].getPosition());
                    }

                }
                if(temp != max_length)
                    break;
                result.addAll(temp_vector);
            }
            return result.size();
        }
        return 0;
    }

    private void addWinningBlock(int player, Vector<Stone[]> stone_vector, int row, int j, Stone[] fives) {
        for(int i = 0 ; i < fives.length; i++)
        {
            if(fives[i].getPlayer() != -1 && fives[i].getPlayer() != player)
            {
                return;
            }
        }
        stone_vector.add(fives);
    }


    public int case3(Board board, int player) {
        Stone[][] game_board = board.getGameboard();
        Vector<Pair> three_vector = new Vector<>();
        for(int row=0; row < 7; row++) // row check
        {
            for(int j = 0; j < 3; j++)
            {
                Stone[] fives = Arrays.copyOfRange(game_board[row],j,j+5);
                addThreePair(player, three_vector, row, j, fives);
            }

        }
        for(int col=0; col < 7; col++) // col check
        {
            for(int j = 0; j < 3; j++)
            {
                Stone[] fives = getFives(game_board,col,j,"col", player);
                addThreePair(player, three_vector, col, j, fives);
            }

        }

        //CHECK DIAGONAL
        for(int row = 4; row < 7; row ++) {
            for (int col = 0; col < 7; col++) {
                Stone[] fives;
                if (col == 3) {
                    continue;
                }
                else if (col >= 0 && col < 3) {
                    fives = getFives(game_board, col, row,"diagPos", player);

                }
                else {
                    fives = getFives(game_board, col, row,"diagNeg", player);
                }
                addThreePair(player, three_vector, row, col, fives);
            }
        }

        return three_vector.size();
    }

    private void addValidPair(int player, Vector<Pair> pair_vector, int row, int j, Stone[] fives) {
        Pair temp_pair = fiveChecker(fives,player,row,j);
        if(temp_pair != null)
        {
            pair_vector.add(temp_pair);
        }
    }

    private void addThreePair(int player, Vector<Pair> three_pair, int row, int j, Stone[] fives)
    {
        Vector<Pair> temp_three_pair = threeChecker(fives,player,row,j);
        if(temp_three_pair != null)
        {
            for(int i = 0; i < temp_three_pair.size(); i++)
            {
                three_pair.add(temp_three_pair.elementAt(i));
            }
        }
    }

    private Vector<Pair> threeChecker(Stone[] fives, int player, int row, int j) {
        Vector<Pair> possible = new Vector<>();

        if(fives[0].getPlayer() == -1 && fives[4].getPlayer() == -1)
        {
            if(fives[1].getPlayer() != -1 && fives[2].getPlayer() != -1 && fives[3].getPlayer() != -1)
            {
                if(fives[1].getPlayer() != player && fives[1].getPlayer() == fives[2].getPlayer() && fives[2].getPlayer() == fives[3].getPlayer())
                {

                    Pair first_pos = new Pair(fives[0].getPosition().getRow(),fives[0].getPosition().getCol());
                    Pair second_pos = new Pair(fives[4].getPosition().getRow(),fives[4].getPosition().getCol());
                    possible.add(first_pos);
                    possible.add(second_pos);
                    return possible;

                }
            }


        }

        return null;
    }

    public Stone[] getFives(Stone[][] board, int col, int rowStart, String direction, int player) {
        Stone[] ret = new Stone[5];
        if(direction.equals("col"))
        {
            for(int i = 0; i < 5 ; i++)
            {
                Stone temp = board[rowStart+i][col];
                ret[i] = temp;

            }
        }
        else if (direction.equals("diagPos")) {
            for (int i = 0; i < 5; i++) {
                Stone temp = board[rowStart-i][col+i];
                ret[i] = temp;
            }
        }
        else if (direction.equals("diagNeg")){
            for (int i = 0; i < 5; i++) {
                Stone temp = board[rowStart-i][col-i];
                ret[i] = temp;
            }
        }
        else {
            return null;
        }
        return ret;
    }

    public Pair fiveChecker(Stone[] fives, int player, int row, int col)
    {
        int count =0;
        Pair possible = null;
        for (int i =0; i < fives.length; i++) {
            if (fives[i].getPlayer() == -1) {
                possible = new Pair(fives[i].getPosition().getRow(),fives[i].getPosition().getCol());
            }
            if (fives[i].getPlayer() != -1 && fives[i].getPlayer() == player) {
                count++;
            }
        }
        if (count == 4) {
            return possible;
        }
        return null;
    }


    private static class arrayComparator implements Comparator<Stone[]> {

        @Override
        public int compare(Stone[] o1, Stone[] o2) {
            int count_1 = 0;
            int count_2 = 0;

            for(int first = 0 ; first < o1.length ; first++)
            {
                if(o1[first].getPlayer() != -1)
                {
                    count_1++;
                }
            }
            for(int second =0; second < o2.length ; second ++)
            {
                if(o2[second].getPlayer() != -1)
                {
                    count_2++;
                }
            }
            if (count_1  < count_2) {
                return 1;
            } else if(count_1 > count_2){
                return -1;
            }
            else
            {
                return 0;
            }
        }
    }

    public int case2(Board board, int player)
    {
        /*if(player == 1)
            return case1(board,2);
        else
            return case1(board,1);*/
        int val;
        if(player == 1) {
            val = case1(board,2);
        }
        else {
            val = case1(board,1);
        }
        if (val == 0) return 700;
        else return 0;
    }

}

