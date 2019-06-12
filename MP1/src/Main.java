import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Main {
    public static void main(String args[]){
        /*System.out.println("DFS starts here");
        CreateMaze bigm = new CreateMaze("bigmaze.txt");
        Graph graph_Big_DFS = new Graph(bigm.maze);
        DFSearch solver1 = new DFSearch();
        long starttime = System.currentTimeMillis();
        solver1.solve(graph_Big_DFS,bigm.maze);
        long endtime = System.currentTimeMillis();
        long totaltime = endtime - starttime;
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Execution time is " + formatter.format((totaltime) / 1000d) + " seconds");

        CreateMaze medm = new CreateMaze("mediummap.txt");
        Graph graph_Med_DFS = new Graph(medm.maze);
        DFSearch solver2 = new DFSearch();
        long starttime1 = System.currentTimeMillis();
        solver2.solve(graph_Med_DFS,medm.maze);
        long endtime1 = System.currentTimeMillis();
        long totaltime1 = endtime - starttime;
        NumberFormat formatter1 = new DecimalFormat("#0.00000");
        System.out.println("Execution time is " + formatter1.format((totaltime1) / 1000d) + " seconds");
        CreateMaze openm = new CreateMaze("openmaze.txt");
        Graph graph_Open_DFS = new Graph(openm.maze);
        DFSearch solver3 = new DFSearch();
        solver3.solve(graph_Open_DFS,openm.maze);*/

        /*System.out.println("BFS starts here");
        BFS graph_Big_BFS = new BFS("bigmaze.txt");
        graph_Big_BFS.solve();
        BFS graph_Med_BFS = new BFS("mediummap.txt");
        graph_Med_BFS.solve();
        BFS graph_Open_BFS = new BFS("openmaze.txt");
        graph_Open_BFS.solve();*/

        /*CreateMaze bigm = new CreateMaze("bigmaze.txt");
        Graph graph = new Graph(bigm.maze);
        Graph.Vertex start = graph.getStartVertex();
        Graph.Vertex end = graph.getDestinations().elementAt(0);
        AStar astar = new AStar(start,end,graph);
        Graph.Vertex print_end = astar.dest;
        astar.printGraph(bigm.maze, print_end);*/

        CreateMaze bigm = new CreateMaze("OpenMaze.txt");
        Graph graph = new Graph(bigm.maze);
        Graph.Vertex start = graph.getStartVertex();
        Graph.Vertex end = graph.getDestinations().elementAt(0);
        AStar astar = new AStar(start,end,graph);
        Graph.Vertex print_end = astar.dest;
        astar.printGraph(bigm.maze, print_end);
        /*
        MultDot part2 = new MultDot(graph);
        Graph newGraph = part2.preComputation(graph);
        ModifiedAstar mastar = new ModifiedAstar(graph.getStartVertex(), newGraph);
*/

    }
}
