import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Vector;

public class AStar {

    public String sol = new String();
    public int stepCost = 0;

    public AStar(WidgetSet widgets) {

        //Create Graph
        CreateGraph cg = new CreateGraph();
        Graph graph = cg.getGraph(widgets.widgets);

        PriorityQueue<FactorySequence> frontier = new PriorityQueue<>(new Heuristic());
        for(int i = 0; i < graph.getVertices().size(); i++) {
            char c = graph.getVertices().elementAt(i).c;
            WidgetSet w = widgets.cut(c);
            FactorySequence temp = new FactorySequence(c, w, "");
            frontier.add(temp);
        }


        boolean found = false;
        while(!found) {
            FactorySequence cur = frontier.poll();

            if (cur.remainingLength == 0) {
                found = true;
                sol = cur.sequence;
                break;
            }

            for (int i = 0; i < graph.getVertices().size(); i++) {
                char child = graph.getVertices().elementAt(i).c;
                if (cur.getLastElement() != child) {
                    WidgetSet w = cur.widgets.cut(child);
                    FactorySequence temp = new FactorySequence(child, w, cur.sequence);
                    frontier.add(temp);
                }
            }
            stepCost++;
        }

    }

    public static class Heuristic implements Comparator<FactorySequence> {

        @Override
        public int compare(FactorySequence t1, FactorySequence t2) {
            if(t1.remainingLength + t1.sequence.length()< t2.remainingLength + t2.sequence.length()) {
                return -1;
            }
            else
                return 1;
        }
    }

    public class FactorySequence {
        private String sequence = new String();
        public int remainingLength;
        public WidgetSet widgets;

        public FactorySequence(char input, WidgetSet widgets, String seq) {
            setSequence(seq+input);
            remainingLength = widgets.remainingLength(input);
            this.widgets = widgets;
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
