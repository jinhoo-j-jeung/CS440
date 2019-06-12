import java.util.Random;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class GameManager extends JFrame implements ActionListener, MouseListener{
    Board gameboard;
    Gamelogic logic;
    JPanel boardPanel;
    public boolean isPlaying = false;
    private Cell[][] piecePanel;
    private int player = 1;
    private MinimaxLogic minimaxLogic = new MinimaxLogic();

    public GameManager(Board board)
    {
        gameboard = board;
        //gameboard = new Board();
        logic = new Gamelogic();
    }

    public class Cell extends JPanel {
        public int x;
        public int y;

        public Cell (BorderLayout b) {
            super(b);
        }
    };

    private JPanel initializePanel() {
        JPanel panel = new JPanel(new GridLayout(7, 7));
        panel.setSize(new Dimension(500,500));
        return panel;
    }

    private void drawBoard() {
        piecePanel = new Cell[7][7];
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                piecePanel[i][j] = new Cell(new BorderLayout());
                piecePanel[i][j].x = i;
                piecePanel[i][j].y = j;
                boardPanel.add(piecePanel[i][j]);
                piecePanel[i][j].setBackground(Color.WHITE);
                piecePanel[i][j].setBorder(BorderFactory.createLineBorder(Color.black));
                piecePanel[i][j].addMouseListener(this);
            }
        }
    }

    public GameManager() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            //silently ignore
        }
        setTitle("Chess Game");
        setSize(500, 500);
        boardPanel = initializePanel();
        this.setUpMenu(this);
        this.setContentPane(boardPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.drawBoard();
        gameboard = new Board();
        gameboard.generateFig5();
        logic = new Gamelogic();
    }

    public void showMessage (String s) {
        JOptionPane.showMessageDialog(null, s, "Chess Game Information", JOptionPane.INFORMATION_MESSAGE);
    }

    ActionListener emptyAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            showMessage("This is woring");
        }
    };


    private ActionListener startAction = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (!isPlaying) {
                isPlaying = !isPlaying;

                showMessage("Start !");
            }
        }
    };



    private void setUpMenu(JFrame window) {
        JMenuBar menubar = new JMenuBar();

        JMenu menu = new JMenu("Menu");

        JMenuItem start = new JMenuItem("Start");
        JMenuItem forfeit = new JMenuItem("Forfeit");
        JMenuItem restart = new JMenuItem("Restart");
        JMenuItem score = new JMenuItem("Score");


        score.addActionListener(emptyAction);
        start.addActionListener(startAction);
        forfeit.addActionListener(emptyAction);
        restart.addActionListener(emptyAction);
        menu.add(start);
        menu.add(forfeit);
        menu.add(restart);
        menu.add(score);

        menubar.add(menu);
        menu.addSeparator();
        JMenu modeMenu = new JMenu("Mode");
        JMenuItem standard = new JMenuItem("Standard");
        JMenuItem custom = new JMenuItem("Custom");

        custom.addActionListener(emptyAction);
        standard.addActionListener(emptyAction);

        modeMenu.add(standard);
        modeMenu.add(custom);
        menu.add(modeMenu);

        window.setJMenuBar(menubar);
    }

    public Board play()
    {
        int count = 0;
        int player = 1;
        int opponent = 2;
        while(true)
        {
            count ++;
            if (count ==50){
                System.out.println("Draw");
                return gameboard;
            }
            if (reflexMove(player, opponent)) return gameboard;

            if(player == 1)
            {
                opponent = 1;
                player = 2;
            }
            else
            {
                player = 1;
                opponent = 2;
            }
            gameboard.printGameboard();
            System.out.println();

        }
    }

    public Board alphabetareflex(int startPlayer){
        int count = 0;
        int player = startPlayer;
        int opponent = 2;
        int step_cost = 0;
        boolean first = true;
        while(true) {
            count++;
            if (count == 50) {
                System.out.println("Draw");
                return gameboard;
            }
            if (player == 1) {
                AlphaBeta alphaBeta = new AlphaBeta(gameboard);
                alphaBeta.treeTraversal(alphaBeta.root,0);
                step_cost += alphaBeta.expanded_node;
                Pair sol = alphaBeta.sol;
                MinimaxLogic logic_mini = new MinimaxLogic();
                Stone stone = new Stone(sol.getRow(), sol.getCol(), player);
                gameboard.setStone(stone);
                if(logic_mini.winningCase(gameboard,1) == 10000)
                {
                    System.out.println("Winner is : " + player);
                    System.out.println("Step Cost is :" + step_cost);
                    return gameboard;
                }
                opponent = 1;
                player = 2;

            } else {
                if (first && startPlayer == 2) {
                    Random rand = new Random(System.currentTimeMillis());
                    int r = rand.nextInt(7);
                    int c = rand.nextInt(7);
                    Pair pos = new Pair(r, c);

                    Stone stone = new Stone(pos.getRow(), pos.getCol(), player);
                    gameboard.setStone(stone);
                    first = !first;
                }
                else if (reflexMove(player, opponent)) return gameboard;
                player = 1;
                opponent = 2;

            }
            gameboard.printGameboard();
            System.out.println();
        }
    }




    public Board minimaxreflexplay(int startPlayer){
        int count = 0;
        int player = startPlayer;
        int opponent = 2;
        int step_cost = 0;
        boolean first = true;
        while(true) {
            count++;
            if (count == 50) {
                System.out.println("Draw");
                return gameboard;
            }
            if (player == 1) {
                Minimax minimax = new Minimax(gameboard);
                minimax.treeTraversal(minimax.root);
                step_cost += minimax.expanded_node;
                Pair sol = minimax.sol;
                MinimaxLogic logic_mini = new MinimaxLogic();
                Stone stone = new Stone(sol.getRow(), sol.getCol(), player);
                gameboard.setStone(stone);
                if(logic_mini.winningCase(gameboard,1) == 10000)
                {
                    System.out.println("Winner is : " + player);
                    System.out.println("Step cost is :" + step_cost);
                    return gameboard;
                }
                opponent = 1;
                player = 2;

            } else {
                if (first && startPlayer == 2) {
                    Random rand = new Random(System.currentTimeMillis());
                    int r = rand.nextInt(7);
                    int c = rand.nextInt(7);
                    Pair pos = new Pair(r, c);

                    Stone stone = new Stone(pos.getRow(), pos.getCol(), player);
                    gameboard.setStone(stone);
                    first = !first;
                }
                else if (reflexMove(player, opponent)) return gameboard;
                player = 1;
                opponent = 2;

            }
            gameboard.printGameboard();
            System.out.println();
        }
    }


    private boolean reflexMove(int player, int opponent) {
        Pair possible = logic.winngCheck(gameboard, player);
        if(possible != null)
        {
            Stone stone = new Stone(possible.getRow(), possible.getCol(), player);
            gameboard.setStone(stone);
            System.out.println("Winner is : " + player);
            return true;
        }
        possible = logic.blockWinner(gameboard,player);
        if(possible != null)
        {
            Stone stone = new Stone(possible.getRow(), possible.getCol(), player);
            gameboard.setStone(stone);
        }

        else if((possible = logic.threeCheck(gameboard,opponent)) != null)
        {
            Stone stone = new Stone(possible.getRow(), possible.getCol(), player);
            gameboard.setStone(stone);
        }
        else if((possible = logic.winningBlock(gameboard,player)) != null)
        {
            Stone stone = new Stone(possible.getRow(), possible.getCol(), player);
            gameboard.setStone(stone);
        }
        else
        {
            possible = logic.firstMove(gameboard,player);
            if(possible == null)
            {

            }
            Stone stone = new Stone(possible.getRow(), possible.getCol(), player);
            gameboard.setStone(stone);
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    private void reDraw() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                piecePanel[i][j].add(getIcon(gameboard.gameboard[i][j]), BorderLayout.CENTER);
                piecePanel[i][j].validate();
            }
        }
    }

    public void initGame() {
        gameboard = new Board();
        gameboard.generateFig5();
        boardPanel = initializePanel();
        this.setUpMenu(this);
        this.setContentPane(boardPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.drawBoard();
    }


    @Override
    public void mouseClicked(MouseEvent e) {
        if (isPlaying) {
            Object source = e.getSource();
            if (source instanceof Cell) {
                Cell cell = (Cell) source;

                int row = cell.x;
                int col = cell.y;

                if (gameboard.gameboard[row][col].getPlayer() == -1) {
                    gameboard.setStone(new Stone(row, col, 1));
                    reDraw();
                    if (minimaxLogic.winningCase(gameboard, 1) == 10000) {
                        showMessage("Winner is Player 1");
                        initGame();
                        isPlaying = !isPlaying;
                        return;
                    }
                    if (reflexMove(2, 1)) {
                        showMessage("Player 2 won");
                        initGame();
                        isPlaying = !isPlaying;
                        return;
                    }
                    reDraw();
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    private JLabel getIcon(Stone stone) {
        if (stone == null) {
            return new JLabel();
        }
        if (stone.getPlayer() == 1) {
            ImageIcon blackStone = new ImageIcon(System.getProperty("user.dir") + "/resources/blackStone.png");
            return new JLabel(blackStone);
        }
        else if (stone.getPlayer() == 2){
            ImageIcon whiteStone = new ImageIcon(System.getProperty("user.dir") + "/resources/whiteStone.png");
            return new JLabel(whiteStone);
        }
        return new JLabel();
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame game = new GameManager();
                game.setVisible(true);
            }
        });
    }



}
