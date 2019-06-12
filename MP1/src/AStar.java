import java.util.*;

public class AStar {
    int cost = 0;
    int extendedNode = 0;
    int totalCost = 0;
    Graph.Vertex dest;
    public static class Heuristic implements Comparator<Graph.Vertex> {
        @Override
        public int compare(Graph.Vertex v1, Graph.Vertex v2) {
            if (v1.heuristic + v1.pathcost  < v2.heuristic + v2.pathcost) {
                return -1;
            } else if (v1.heuristic + v1.pathcost > v2.heuristic + v2.pathcost) {
                return 1;
            }
            else if (v1.heuristic + v1.pathcost == v2.heuristic + v2.pathcost){
                if (v1.pathcost < v2.pathcost){
                    return -1;
                }
                else {
                    return 1;
                }
            }
            return 0;
        }
    }

    public int getCost(){
        return cost;
    }
    public AStar(Graph.Vertex start, Graph.Vertex end, Graph graph) {
        //Declare and initialize variables
        Graph.Vertex startVertex = start;
        Map<Graph.Vertex, Set<Graph.Vertex>> adjList = graph.getAdjList();
        startVertex.visited = true;
        startVertex.prev = null;
        Graph.Vertex goal = end;
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
                    int now_x = vertex.pair.getKey();
                    int now_y = vertex.pair.getValue();
                    if (vertex.equals(goal)) {
                        vertex.prev = cur;
                        found = true;
                        destination = vertex;
                        break;
                    }
                    vertex.visited = true;
                    vertex.heuristic = Math.abs(goal_x - now_x) + Math.abs(goal_y - now_y);
                    vertex.pathcost = cur.pathcost + 1;
                    vertex.prev = cur;
                    frontier.add(vertex);
                    extendedNode++;
                }
            }
            if (found) {
                break;
            }
        }
        this.dest = destination;

        while (destination.prev != null) {
            cost++;
            destination = destination.prev;
        }
        setVisitedFalse(graph);
    }

    public void printGraph(char[][] maze, Graph.Vertex destination) {
        while (destination.prev != null) {
            Graph.Pair pair = destination.pair;
            maze[pair.getKey()][pair.getValue()] = '.';
            destination = destination.prev;
            totalCost++;
        }


        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[0].length; j++) {
                System.out.print(maze[i][j]);
            }
            System.out.println("");
        }
        System.out.println("Total cost is " + cost);
        System.out.println("Number of extended node is " + extendedNode);
    }

    public void setVisitedFalse(Graph graph) {
        Vector<Graph.Vertex> vertices = graph.getVertices();
        for (Graph.Vertex vertex : vertices){
            vertex.visited = false;
        }
    }
}
