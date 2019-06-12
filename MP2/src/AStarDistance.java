import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Vector;

public class AStarDistance {

    public String sol = new String();
    public int stepCost = 0;
    public int totalCost = 0;

    public AStarDistance(WidgetSet widgets, int[][] cost) {

        //Create Graph
        CreateGraph cg = new CreateGraph();
        //Graph graph = cg.getDistanceGraph(cost, widgets.widgets);
        Graph graph = cg.convertGraph(cost, widgets.widgets);
        //graph.printGraph();

        MST mst = new MST();
        PriorityQueue<FactorySequence> frontier = new PriorityQueue<>(new Heuristic());
        for(int i = 0; i < graph.getVertices().size(); i++) {
            char c = graph.getVertices().elementAt(i).c;
            WidgetSet w = widgets.cut(c);
            Vector<Graph.Vertex> un = w.univistedFactory(c);
            FactorySequence temp1 = new FactorySequence(c, w, "", mst.getCost(graph, un), 0, un.size());
            frontier.add(temp1);
        }

        while(true) {
            FactorySequence cur = frontier.poll();
            if (cur.size == 0) {
                sol = cur.sequence;
                totalCost = cur.pathCost;
                break;
            }

            for (int i = 0; i < graph.getVertices().size(); i++) {
                char child = graph.getVertices().elementAt(i).c;
                if (cur.getLastElement() != child) {
                    WidgetSet w = cur.widgets.cut(child);
                    Vector<Graph.Vertex> unvisited = w.univistedFactory(child);
                    int mstCost = mst.getCost(graph, unvisited);

                    Graph.Vertex source = graph.findVertex(child);
                    Graph.Vertex dest = graph.findVertex(cur.getLastElement());
                    int pathCost = cur.pathCost + graph.getEdgeCost(source, dest);

                    FactorySequence temp = new FactorySequence(child, w, cur.sequence, mstCost, pathCost, unvisited.size());
                    frontier.add(temp);
                }
            }
            stepCost++;
        }
    }

    public static class Heuristic implements Comparator<FactorySequence> {

        @Override
        public int compare(FactorySequence t1, FactorySequence t2) {
            if(t1.mstWeight + t1.pathCost< t2.mstWeight + t2.pathCost) {
                return -1;
            }
            else if (t1.mstWeight + t1.pathCost > t2.mstWeight + t2.pathCost)
                return 1;
            else{
                if (t1.size < t2.size) {
                    return -1;
                }
                else{
                    return 1;
                }
            }

        }
    }

    public class FactorySequence {
        private String sequence = new String();
        private int pathCost;
        public int mstWeight;
        public WidgetSet widgets;
        public int size;

        public FactorySequence(char input, WidgetSet widgets, String seq, int mstWeight, int pathCost, int size) {
            setSequence(seq+input);
            this.mstWeight = mstWeight;
            this.pathCost = pathCost;
            this.widgets = widgets;
            this.size = size;
        }

        public String getSequence() {
            return this.sequence;
        }
        public void setSequence(String seq){
            this.sequence = seq;
        }
        public char getLastElement(){
            return sequence.charAt(sequence.length()-1);
        }
    }
}
