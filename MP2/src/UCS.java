import java.util.Comparator;
import java.util.PriorityQueue;

public class UCS {

    public String sol = new String();
    public int stepCost = 0;

    public UCS(WidgetSet widgets) {

        //Create Graph
        CreateGraph cg = new CreateGraph();
        Graph graph = cg.getGraph(widgets.widgets);

        PriorityQueue<UCS.FactorySequence> frontier = new PriorityQueue<>(new UCS.Heuristic());
        for(int i = 0; i < graph.getVertices().size(); i++) {
            char c = graph.getVertices().elementAt(i).c;
            WidgetSet w = widgets.cut(c);
            UCS.FactorySequence temp = new UCS.FactorySequence(c, w, "");
            frontier.add(temp);
        }


        boolean found = false;
        while(!found) {
            UCS.FactorySequence cur = frontier.poll();

            if (cur.remainingLength == 0) {
                sol = cur.sequence;
                break;
            }

            for (int i = 0; i < graph.getVertices().size(); i++) {
                char child = graph.getVertices().elementAt(i).c;
                if (cur.getLastElement() != child) {
                    WidgetSet w = cur.widgets.cut(child);
                    UCS.FactorySequence temp = new UCS.FactorySequence(child, w, cur.sequence);
                    frontier.add(temp);
                }
            }
            stepCost++;
        }

    }

    public static class Heuristic implements Comparator<UCS.FactorySequence> {

        @Override
        public int compare(UCS.FactorySequence t1, UCS.FactorySequence t2) {
            if(t1.sequence.length() < t2.sequence.length()) {
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

        public void setSequence(String seq){
            this.sequence = seq;
        }
        public char getLastElement(){
            return sequence.charAt(sequence.length()-1);
        }
    }

}
