import java.util.*;

public class GBFS {
    public static class Heuristic implements Comparator<Graph.Vertex> {
        @Override
        public int compare(Graph.Vertex v1, Graph.Vertex v2) {
            if (v1.heuristic  < v2.heuristic ) {
                return -1;
            } else if ( v1.heuristic > v2.heuristic) {
                return 1;
            }
            return 0;
        }
    }

    public static void main(String args[]) {
        //Declare and initialize variables
        CreateMaze createMaze = new CreateMaze("OpenMaze.txt");
        char[][] maze = createMaze.maze;
        Graph graph = new Graph(maze);
        Graph.Vertex startVertex = graph.getStartVertex();
        Map<Graph.Vertex, Set<Graph.Vertex>> adjList = graph.getAdjList();
        startVertex.visited = true;
        startVertex.prev = null;
        Vector<Graph.Vertex> destinations = graph.getDestinations();
        Graph.Vertex goal = destinations.firstElement();
        goal.heuristic = 0;
        int goal_x = goal.pair.getKey();
        int goal_y = goal.pair.getValue();
        Graph.Vertex destination = null;
        startVertex.heuristic = Math.abs((goal_x - startVertex.pair.getKey())) + Math.abs((goal_y - startVertex.pair.getValue()));
        int pathCost = 0;
        Comparator<Graph.Vertex> comp = new Heuristic();
        PriorityQueue frontier = new PriorityQueue(10,comp);

        // Stare Greedy Best First Search
        frontier.add(startVertex);
        boolean found = false;
        while (!found) {
            Graph.Vertex cur = (Graph.Vertex) frontier.poll();
            cur.visited = true;
            //System.out.println(cur);

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
                    vertex.heuristic = Math.abs(goal_x - vertex.pair.getKey()) + Math.abs(goal_y - vertex.pair.getValue());
                    vertex.prev = cur;
                    frontier.add(vertex);
                }
            }
            if (found) {
                break;
            }

            pathCost++;
        };

        while (destination.prev != null) {
            System.out.println(destination);
            Graph.Pair pair = destination.pair;
            maze[pair.getKey()][pair.getValue()] = '.';
            destination = destination.prev;
        }


        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println("");
        }

    }
}