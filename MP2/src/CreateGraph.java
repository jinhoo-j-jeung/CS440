import java.util.*;

public class CreateGraph {

    public Graph getGraph(Vector widgets){
        Graph graph = new Graph();
        for (int i = 0; i < widgets.size(); i++){
            Widget widget = (Widget)widgets.elementAt(i);
            String sequence = widget.getSequence();
            for (int j = 0 ; j < sequence.length(); j++){
                char cur = sequence.charAt(j);
                Graph.Vertex newVertex = new Graph.Vertex(cur);
                if (graph.findVertex(cur) == null){
                    graph.addVertex(newVertex);
                    for (Graph.Vertex vertex : graph.getVertices()){
                        if (!newVertex.equals(vertex)){
                            Graph.Edge edge = new Graph.Edge(newVertex, vertex);
                            graph.addEdge(edge);
                            edge = new Graph.Edge(vertex, newVertex);
                            graph.addEdge(edge);
                        }
                    }
                }
            }
        }
        return graph;
    }

    public Graph getDistanceGraph(int[][] cost, Vector widgets){
        Graph graph = new Graph();
        for (int i = 0; i < widgets.size(); i++){
            Widget widget = (Widget)widgets.elementAt(i);
            String sequence = widget.getSequence();
            for (int j = 0 ; j < sequence.length(); j++){
                char cur = sequence.charAt(j);
                Graph.Vertex newVertex = new Graph.Vertex(cur);
                if (graph.findVertex(cur) == null){
                    graph.addVertex(newVertex);
                    for (Graph.Vertex vertex : graph.getVertices()){
                        if (!newVertex.equals(vertex)){
                            Graph.Edge edge = new Graph.Edge(newVertex, vertex);
//                            System.out.println(cost[newVertex.c-65][vertex.c-65]);
                            edge.cost = cost[newVertex.c-65][vertex.c-65];
                            graph.addEdge(edge);
                            edge = new Graph.Edge(vertex, newVertex);
                            edge.cost = cost[newVertex.c-65][vertex.c-65];
                            graph.addEdge(edge);
                        }
                    }
                }
            }
        }
        return graph;
    }

    public class Dijkstra {
        Map<Character, Vector<Graph.Vertex>> path = new HashMap<>();
        Map<Character, Integer> cost = new HashMap<>();
    }

    public Dijkstra getShortestPath(Graph graph, Graph.Vertex start){
        Map<Character, Integer> dist = new HashMap<>();
        Map<Character, Graph.Vertex> previous = new HashMap<>();

        for (Graph.Vertex vertex : graph.getVertices()){
            char key = vertex.c;
            dist.put(key, Integer.MAX_VALUE);
            previous.put(key, null);
        }

        dist.put(start.c, 0);
        Vector<Graph.Vertex> q = graph.getVertices();
        while (!q.isEmpty()){
            char smallestChar = getSmallestDist(dist, q);
            Graph.Vertex smallestVertex = graph.findVertex(smallestChar);
            q.remove(smallestVertex);
            Vector<Graph.Vertex> neighbors = getNeighbors(q, smallestVertex);
            for (Graph.Vertex neigh : neighbors){
                int alt = dist.get(smallestChar) + graph.getEdgeCost(smallestVertex, neigh);
                if (alt < dist.get(neigh.c)){
                    dist.put(neigh.c, alt);
                    previous.put(neigh.c, smallestVertex);
                }

            }

        }

        Dijkstra dijkstra = new Dijkstra();
        Set<Character> keys = previous.keySet();
        for (char key : keys){
            Vector<Graph.Vertex> path = new Vector<>();
            int totalCost = 0;
            if (previous.get(key) == null) {
                continue;
            }
            char tempKey = key;
            while (!previous.get(key).equals(start)){
                path.add(previous.get(key));
                Graph.Vertex source = new Graph.Vertex(key);
                Graph.Vertex dest   = new Graph.Vertex(previous.get(key).c);
                totalCost += graph.getEdgeCost(source, dest);
                key = previous.get(key).c;
            }

            Graph.Vertex source = new Graph.Vertex(key);
            totalCost += graph.getEdgeCost(source, start);


            dijkstra.path.put(tempKey, path);
            dijkstra.cost.put(tempKey, totalCost);
        }

        return dijkstra;
    }

    public char getSmallestDist(Map<Character, Integer> dist, Vector<Graph.Vertex> q){
        Set<Character> set = dist.keySet();
        int minVal = Integer.MAX_VALUE;
        char minC = 0;
        for (char c : set){
            Graph.Vertex v = new Graph.Vertex(c);
            if (q.contains(v)){
                int curVal = dist.get(c);
                if (curVal < minVal){
                    minVal = curVal;
                    minC = c;
                }
            }
        }
        return minC;
    }

    public Vector<Graph.Vertex> getNeighbors (Vector<Graph.Vertex> q, Graph.Vertex vertex){
        Vector<Graph.Vertex> neighbors = (Vector<Graph.Vertex>) q.clone();
        neighbors.remove(vertex);
        return neighbors;
    }

    public Graph convertGraph (int[][] cost, Vector widgets) {
        Graph graphOriginal = getDistanceGraph(cost, widgets);
        Graph retGRaph = getDistanceGraph(cost, widgets);

        Vector<Graph.Vertex> vertices = retGRaph.getVertices();
        for (Graph.Vertex vertex : vertices){
            Dijkstra dijkstra = getShortestPath(graphOriginal, vertex);
            Vector<Graph.Edge> edges = retGRaph.getEdges();
            Vector<Graph.Edge> validEdge = new Vector<>();
            for (Graph.Edge edge : edges){
                if (edge.source.equals(vertex) || edge.dest.equals(vertex)){
                    validEdge.add(edge);
                }
            }

            for (Graph.Edge edge : validEdge){
                char c = edge.dest.c;

                if (dijkstra.path.containsKey(c)){
                    edge.lookUp = (dijkstra.path.get(c));
                    edge.cost = (dijkstra.cost.get(c));
                    retGRaph.addEdge(edge);
                }
                c = edge.source.c;
                if (dijkstra.path.containsKey(c)) {
                    edge.lookUp = (dijkstra.path.get(c));
                    edge.cost = (dijkstra.cost.get(c));
                    retGRaph.addEdge(edge);
                }
            }
        }

        return retGRaph;
    }
}
