import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Vector;

public class UCSDistance {
    public String sol = new String();
    public int stepCost = 0;
    public int totalCost = 0;

    public UCSDistance(WidgetSet widgets, int[][] cost) {
        //Create Graph
        CreateGraph cg = new CreateGraph();
        Graph graph = cg.getDistanceGraph(cost, widgets.widgets);

        PriorityQueue<UCSDistance.FactorySequence> frontier = new PriorityQueue<>(new UCSDistance.Heuristic());
        for(int i = 0; i < graph.getVertices().size(); i++) {
            char c = graph.getVertices().elementAt(i).c;
            WidgetSet w = widgets.cut(c);
            Vector<Graph.Vertex> un = w.univistedFactory(c);
            UCSDistance.FactorySequence temp1 = new UCSDistance.FactorySequence(c, w, "", 0, un.size());
            frontier.add(temp1);
        }

        while(true) {
            UCSDistance.FactorySequence cur = frontier.poll();
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

                    Graph.Vertex source = graph.findVertex(child);
                    Graph.Vertex dest = graph.findVertex(cur.getLastElement());
                    int pathCost = cur.pathCost + graph.getEdgeCost(source, dest);

                    UCSDistance.FactorySequence temp = new UCSDistance.FactorySequence(child, w, cur.sequence, pathCost, unvisited.size());
                    frontier.add(temp);
                }
            }
            stepCost++;
        }
    }

    public static class Heuristic implements Comparator<UCSDistance.FactorySequence> {

        @Override
        public int compare(UCSDistance.FactorySequence t1, UCSDistance.FactorySequence t2) {
            if(t1.pathCost< t2.pathCost) {
                return -1;
            }
            else
                return 1;
        }
    }

    public class FactorySequence {
        private String sequence = new String();
        private int pathCost;
        public WidgetSet widgets;
        public int size;

        public FactorySequence(char input, WidgetSet widgets, String seq, int pathCost, int size) {
            setSequence(seq+input);
            this.pathCost = pathCost;
            this.widgets = widgets;
            this.size = size;
        }

        public void setSequence(String seq){
            this.sequence = seq;
        }
        public char getLastElement(){
            return sequence.charAt(sequence.length()-1);
        }
    }

}