import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

public class Minimax {

    public Node root;
    public Board tree_board;
    public Pair sol;
    public int expanded_node =0;

    public Minimax(Board board_)
    {
        root = new Node(board_, 0, -1, -1);
        tree_board = board_;
    }

    public void appendChildren(Node node)
    {
        for(int i =0; i < 7 ; i++)
        {
            for(int j =0; j < 7; j++)
            {
                if(node.board.gameboard[i][j].getPlayer() == -1)
                {
                    node.children.add(new Node(node.board,node.depth+1, i,j));
                }
            }
        }
    }

    public void treeTraversal(Node node)
    {
        expanded_node++;
        if(node.depth == 3)
        {
            eval_func(node);
            return;
        }
        else
        {
            appendChildren(node);
            for(int i = 0; i < node.children.size(); i ++)
            {
                treeTraversal(node.children.elementAt(i));
            }
            Comparator<Node> comparator = new nodeComparator();
            Collections.sort(node.children, comparator);
            if(node.depth % 2 == 0)
            {
                node.score = (node.children.lastElement().score) * (10-node.depth);
                if(node.depth == 0)
                {
                    sol = node.children.lastElement().pos;
                    return;
                }
            }
            else
                node.score = node.children.firstElement().score * 5;
                return;
        }

    }




    public class Node
    {
        Vector<Node> children;
        int score;
        Board board;
        int depth;
        Pair pos;

        public Node(Board board_, int depth_, int x, int y)
        {
            children = new Vector<>();
            depth = depth_;
            pos = new Pair(x,y);
            board = new Board();
            for(int i =0; i < 7; i++)
            {
                for(int j =0; j<7 ; j++)
                {
                    board.gameboard[i][j] = board_.gameboard[i][j];
                }
            }
            if(x != -1 && y != -1)
            {
                if(depth_ == 2)
                    board.setStone(new Stone(x,y,2));
                else
                    board.setStone(new Stone(x,y,1));
            }
        }
    }

    public void eval_func(Node node)
    {
        MinimaxLogic logic = new MinimaxLogic();
        node.score = logic.case1(node.board,1) + logic.case2(node.board, 1) +logic.case3(node.board,2) + logic.case4(node.board,1) + logic.winningCase(node.board,1) - ((int)((logic.winningCase(node.board,2))*1.3));
    }








    private static class nodeComparator implements Comparator<Node> {

        @Override
        public int compare(Node o1, Node o2) {
            if (o1.score < o2.score) {
                return -1;
            }
            else if (o1.score > o2.score){
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }

}
