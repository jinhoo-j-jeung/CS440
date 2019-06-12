import java.util.*;

public class MST {

    public int getCost (Graph graph, Vector<Graph.Vertex> unvisited){
        if (unvisited.size() <= 1){
            return 0;
        }
        Vector<Graph.Edge> edges = new Vector<Graph.Edge>();
        int totalCost = 0;
        getValidEdges(graph, unvisited, edges);

        HashMap<Character, Graph.Vertex> map = new HashMap<>();
        for (int i = 0 ; i < unvisited.size(); i++){
            map.put(unvisited.elementAt(i).c, unvisited.elementAt(i));
        }

        int counter = 0;
        int edgeCount = 0;

        while (counter != unvisited.size()-1){
            Graph.Edge edge = edges.elementAt(edgeCount);
            edgeCount++;

            Graph.Vertex source = edge.source;
            Graph.Vertex dest = edge.dest;
            if (isCycle(map, source, dest)){
                continue;
            }
            Union(map, source, dest);
            counter++;
            totalCost += edge.cost;
        }

        return totalCost;
    }

    public Graph.Vertex find (HashMap<Character, Graph.Vertex> map, Graph.Vertex vertex){
        char key = vertex.c;

        Graph.Vertex parent = map.get(key);
        return parent;
    }

    public void Union (HashMap<Character, Graph.Vertex> map, Graph.Vertex v1, Graph.Vertex v2){
        char key = v1.c;

        Graph.Vertex parent = map.get(key);

        char v2Key = v2.c;

        map.put(v2Key, parent);
    }

    public boolean isCycle (HashMap<Character, Graph.Vertex> map, Graph.Vertex v1, Graph.Vertex v2){
        return (find(map, v1).equals(find(map,v2)));
    }

    public void getValidEdges(Graph graph, Vector<Graph.Vertex> unvisited, Vector<Graph.Edge> edges) {
        for (int i = 0 ; i < unvisited.size(); i ++){
            for (int j = i+1; j < unvisited.size(); j ++) {
                Graph.Edge edge = graph.findEdge(unvisited.elementAt(i), unvisited.elementAt(j));
                edges.add(edge);
                edge = graph.findEdge(unvisited.elementAt(j), unvisited.elementAt(i));
                edges.add(edge);
            }
        }

        Comparator<Graph.Edge> comparator = new Heuristic();
        Collections.sort(edges, comparator);
    }

    public static class Heuristic implements Comparator<Graph.Edge> {
        @Override
        public int compare(Graph.Edge o1, Graph.Edge o2) {
            if (o1.cost  < o2.cost) {
                return -1;
            } else if ( o1.cost > o2.cost) {
                return 1;
            }
            return 0;
        }
    }


}
