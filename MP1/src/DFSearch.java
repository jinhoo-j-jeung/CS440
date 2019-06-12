import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

public class DFSearch {
    // global variables
    Stack DFStack = new Stack();
    public void solve(Graph graph,char[][] maze)
    {
        Graph.Vertex S_point = graph.getStartVertex();
        Map<Graph.Vertex, Set<Graph.Vertex>> adjList = graph.getAdjList();
        DFStack.push(S_point);
        boolean found = false;
        S_point.visited = true;
        S_point.prev = null;
        Vector<Graph.Vertex> destinations = graph.getDestinations();
        Graph.Vertex destination = null;
        if(destinations == null)
        {
            System.out.println("No destinations");
            return;
        }
        while(!DFStack.isEmpty())
        {
            Graph.Vertex curr = (Graph.Vertex) DFStack.pop();
            Set<Graph.Vertex> adj = adjList.get(curr);

            for (Graph.Vertex vertex : adj)
            {
                if(!vertex.visited)
                {
                    if(destinations.contains(vertex))
                    {
                        vertex.prev = curr;
                        destination = vertex; // found the destination!
                        break;
                    }

                    vertex.visited = true;
                    DFStack.push(vertex);
                    vertex.prev = curr;

                }
            }
            if(found)
            {
                break;
            }
        }
        if(destination == null)
        {
            System.out.println("cannot reach");
            return;
        }

        while (destination.prev != null) {
            Graph.Pair pair = destination.pair;
            maze[pair.getKey()][pair.getValue()] = '.';
            destination = destination.prev;
        }


        for (int i = 0 ; i < maze.length; i ++) {
            for (int j = 0 ; j < maze[0].length; j ++) {
                System.out.print(maze[i][j]);
            }
            System.out.println("");
        }

    }
}
