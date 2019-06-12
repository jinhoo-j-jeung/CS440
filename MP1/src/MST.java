import java.util.*;

public class MST {
    public MST(){}

    public int getCost (Graph graph, Vector<Graph.Vertex> unvisited){
        Vector<Graph.Edge> edges = new Vector<Graph.Edge>();
        int totalCost = 0;
        getValidEdges(graph, unvisited, edges);

        HashMap<String, Graph.Vertex> map = new HashMap<>();
        for (int i = 0 ; i < unvisited.size(); i++){
            String key = Integer.toString(unvisited.elementAt(i).pair.getKey());
            key += unvisited.elementAt(i).pair.getValue();
            map.put(key, unvisited.elementAt(i));
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

    public Graph.Vertex find (HashMap<String, Graph.Vertex> map, Graph.Vertex vertex){
        String key = Integer.toString(vertex.pair.getKey());
        key += vertex.pair.getValue();

        Graph.Vertex parent = map.get(key);
        return parent;
    }

    public void Union (HashMap<String, Graph.Vertex> map, Graph.Vertex v1, Graph.Vertex v2){
        String key = Integer.toString(v1.pair.getKey());
        key += v1.pair.getValue();

        Graph.Vertex parent = map.get(key);

        String v2Key = Integer.toString(v2.pair.getKey());
        v2Key += v2.pair.getValue();

        map.put(v2Key, parent);
    }

    public boolean isCycle (HashMap<String, Graph.Vertex> map, Graph.Vertex v1, Graph.Vertex v2){
        return (find(map, v1).equals(find(map,v2)));
    }

    public void getValidEdges(Graph graph, Vector<Graph.Vertex> unvisited, Vector<Graph.Edge> edges) {
        for (int i = 0 ; i < unvisited.size(); i ++){
            for (int j = i+1; j < unvisited.size(); j ++) {
                Graph.Edge edge = graph.findEdge(unvisited.elementAt(i), unvisited.elementAt(j));
                Graph.Vertex debug1 = unvisited.elementAt(i);
                Graph.Vertex debug2 = unvisited.elementAt(j);
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
