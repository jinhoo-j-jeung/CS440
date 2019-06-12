import java.util.*;

public class BFS {

    private Queue queue;
    private CreateMaze createMaze;
    private char[][] maze;
    private Graph graph;
    private Graph.Vertex startVertex;
    private Map<Graph.Vertex, Set<Graph.Vertex>> adjList;
    private Vector<Graph.Vertex> destinations;
    private Graph.Vertex destination = null;

    public BFS(String filename) {
        queue = new LinkedList();
        createMaze = new CreateMaze(filename);
        maze = createMaze.maze;
        graph = new Graph(maze);
        startVertex = graph.getStartVertex();
        adjList = graph.getAdjList();
        destinations = graph.getDestinations();
    }
    public void solve () {
        startVertex.visited = true;
        startVertex.prev = null;

        queue.add(startVertex);
        boolean found = false;
        int counter = 1;
        int totalCost = 0;
        while (queue.size() != 0)
        {
            // Dequeue a vertex from queue and print it
            Graph.Vertex cur = (Graph.Vertex) queue.poll();

            // Get all adjacent vertices of the dequeued vertex s
            // If a adjacent has not been visited, then mark it
            // visited and enqueue it

            Set<Graph.Vertex> adj = adjList.get(cur);
            for (Graph.Vertex vertex : adj) {
                if (!vertex.visited) {
                    if (destinations.contains(vertex)) {
                        vertex.prev = cur;
                        found = true;
                        destination = vertex;
                        break;
                    }

                    vertex.visited = true;
                    queue.add(vertex);
                    counter ++;
                    vertex.prev = cur;
                }
            }

            if (found) {
                break;
            }
        }

        if (destination == null) {
            System.out.println("BFS failed");
        }
        else {
            while (destination.prev != null) {
                Graph.Pair pair = destination.pair;
                maze[pair.getKey()][pair.getValue()] = '.';
                destination = destination.prev;
                totalCost++;
            }
        }

        for (int i = 0 ; i < maze.length; i ++) {
            for (int j = 0 ; j < maze[0].length; j ++) {
                System.out.print(maze[i][j]);
            }
            System.out.println("");
        }
        System.out.println("Number of extended node is " + counter);
        System.out.println("Total cost is " + totalCost);

    }
}

