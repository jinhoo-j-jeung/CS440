import java.util.Vector;


public class MultDot {

    private static Vector<Graph.Vertex> destinations;
    private Graph seqGraph;


    public static class Sequence {
        String seq = "";
        int cost;
        int mstCost;
        Sequence prev = null;
        Graph.Vertex curVertex = null;
        /*public Sequence (int cost, Vector<Graph.Vertex> sequence){
            this.cost = cost;
            this.seq = sequence;
        }*/

        public Sequence(int mstCost, int cost, String sequence, Graph.Vertex remove, Sequence prev)
        {
            this.mstCost = mstCost;
            this.cost = cost;
            //Vector<Graph.Vertex> temp_s = (Vector< Graph.Vertex>)sequence.clone();
            String temp_s = "";
            curVertex = remove;
            this.prev = prev;
            String key = Integer.toString(remove.pair.getKey());
            key += "," + remove.pair.getValue();
            String[] array = sequence.split(" ");
            int index = java.util.Arrays.asList(array).indexOf(key);
            String[] new_array = remove(array,index);
            for(int i = 0 ; i < new_array.length; i++)
            {
                temp_s = temp_s.concat(new_array[i] + " ");
            }
            this.seq = temp_s;

        }

    }

    public static String[] remove(String[] src, int idx) {
        int length = src.length;
        String[] result = new String[length-1];
        System.arraycopy(src, 0, result, 0, idx);
        System.arraycopy(src, idx + 1, result, idx, length - idx - 1);
        return result;
    }

    public MultDot(Graph graph)
    {
        this.destinations = graph.getDestinations();
        this.destinations.add(graph.getStartVertex());
        seqGraph = new Graph();
        // p at the end of destinations
    }

    public Graph preComputation(Graph graph)
    {
        for (int i = 0; i < destinations.size(); i++){
            seqGraph.addVertex(destinations.elementAt(i));
        }
        for(int start = 0; start < destinations.size(); start++)
        {
            for(int end = start+1; end < destinations.size(); end++)
            {
                Graph.Edge edge = new Graph.Edge(destinations.elementAt(start), destinations.elementAt(end));
                Graph.Vertex start_v = this.destinations.elementAt(start);
                Graph.Vertex end_v = this.destinations.elementAt(end);
                AStar astar = new AStar(start_v, end_v, graph);
                edge.cost = astar.getCost();
                seqGraph.addEdge(edge);
                System.out.print(edge.cost + "  ,");
            }
            System.out.println("");
        }
        seqGraph.setDestinations(destinations);
        return seqGraph;
    }

    /*public static boolean contain_all(Sequence sequence){
        if(sequence.seq.containsAll(destinations)) return true;
        else return false;
    }*/

}
