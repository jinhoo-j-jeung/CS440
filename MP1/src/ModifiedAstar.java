import java.util.*;
import java.util.concurrent.TimeUnit;

public class ModifiedAstar {

    public static class Heuristic implements Comparator<MultDot.Sequence> {
        @Override
        public int compare(MultDot.Sequence v1, MultDot.Sequence v2) {
            if (v1.cost + v1.mstCost  < v2.cost + v2.mstCost ) {
                return -1;
            } else if ( v1.cost + v1.mstCost > v2.cost + v2.mstCost) {
                return 1;
            }
            else if(v1.cost + v1.mstCost == v2.cost + v2.mstCost)
            {
                if(v1.seq.length() > v2.seq.length())
                {
                    return 1;
                }
                else
                    return -1;
            }
            return 0;
        }
    }
/*


    public Vector<Graph.Vertex> findUnvisited (Vector<Graph.Vertex> verticies, Vector<Graph.Vertex> visited){
        Vector<Graph.Vertex> ret = new Vector<>();
        for (Graph.Vertex v : verticies) {
            if (!visited.contains(v)) {
                ret.add(v);
            }
        }
        return ret;
    }
*/


    public ModifiedAstar(Graph.Vertex start, Graph graph) {
        //Declare and initialize variables
        int max = Integer.MAX_VALUE;
        //Declare and initialize variables
        Graph.Vertex startVertex = start;
        Vector<Graph.Vertex> prev_s = new Vector<>();
        String first_str = "";
        for(Graph.Vertex v : graph.getDestinations())
        {
            String key = Integer.toString(v.pair.getKey());
            key += "," + v.pair.getValue();
            first_str += (key + " ");
        }
        MultDot.Sequence start_seq = new MultDot.Sequence(0, 0,first_str, startVertex, null);
        Comparator<MultDot.Sequence> comp = new Heuristic();
        PriorityQueue frontier = new PriorityQueue(10, comp);
        MST mst = new MST();
        long startTime = System.nanoTime();
        // Stare Greedy Best First Search
        frontier.add(start_seq);

        MultDot.Sequence sequence = null;

        boolean found = false;
        while (!found) {
            MultDot.Sequence cur = (MultDot.Sequence) frontier.poll();
            sequence = cur;
            if (cur.seq.isEmpty()) {
                found = true;
                break;
            }
            //Vector<Graph.Vertex> unvisited = findUnvisited(graph.getVertices(), cur.seq);
            String [] current_sequence = cur.seq.split(" ");
            for (int i = 0; i < current_sequence.length; i++) {
                if (!current_sequence[i].equals("")){
                    String[] splited = current_sequence[i].split(",");
                    int first = Integer.parseInt(splited[0]);
                    int second = Integer.parseInt(splited[1]);
                    Graph.Vertex vertex = graph.findVertex(first,second);
                    int tempCost = cur.cost + graph.getEdgeCost(cur.curVertex, vertex);
                    Vector<Graph.Vertex> temp_vector = new Vector<>();
                    for(int j = 0; j < current_sequence.length; j++)
                    {
                        if (!current_sequence[j].equals(""))
                        {
                            String[] splited1 = current_sequence[j].split(",");
                            int first1 = Integer.parseInt(splited1[0]);
                            int second1 = Integer.parseInt(splited1[1]);
                            Graph.Vertex vertex1 = graph.findVertex(first1,second1);
                            temp_vector.add(vertex1);
                        }
                    }
                    int mstCost = mst.getCost(graph, temp_vector);
                    MultDot.Sequence new_seq = new MultDot.Sequence(mstCost, tempCost, cur.seq, vertex, cur);
                    frontier.add(new_seq);
                }
                //System.out.println(cur.seq);
            }
            /*if (cur.seq.length()/2 < max) {
                max = cur.seq.length();
                System.out.println("Current depth is " + max);
            }*/
            //System.out.println(cur.seq.size());
        }
        int finalCost = sequence.cost;
        while (sequence.prev != null) {
            Graph.Vertex p = sequence.curVertex;
            System.out.println(p.pair.printPair());
            sequence = sequence.prev;
        }
        System.out.println(finalCost);
        long endTime = System.nanoTime();
        long totalTime = TimeUnit.MINUTES.convert((endTime - startTime), TimeUnit.NANOSECONDS);
        System.out.println("Total time is " + totalTime);


    }

}
