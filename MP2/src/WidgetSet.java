import java.util.List;
import java.util.Vector;

public class WidgetSet {
    Vector<Widget> widgets;

    public WidgetSet()
    {
        this.widgets = new Vector<>();
    }

    public void append(Widget newWidget)
    {
        this.widgets.add(newWidget);
    }

    public WidgetSet cut(char factory)
    {
        WidgetSet ret = new WidgetSet();
        for(int i = 0; i < widgets.size(); i++)
        {
            ret.append(widgets.elementAt(i).cut(factory));
        }

        return ret;

    }

    /*
    without operating cut returns the total length of widgets.
     */
    public int totalLength()
    {
        int sum =0;
        for (int i = 0; i < widgets.size(); i++)
        {
            sum += widgets.elementAt(i).length();
        }
        return sum;
    }

    /*
    returns length of whole widget after cut by the input
     */
    public int remainingLength(char factory)
    {
        WidgetSet temp = cut(factory);
        String unique = new String();
        //int sum = 0;
        for(int i = 0; i < temp.widgets.size();i++)
        {

            for(int j = 0; j < temp.widgets.elementAt(i).length(); j++){
                String s = new String();
                s += temp.widgets.elementAt(i).getSequence().charAt(j);
                if (!unique.contains(s)){
                    unique+=s;
                }
            }

            //sum += temp.widgets.elementAt(i).length();
        }
        return unique.length();
        //return sum;
    }

    public Vector<Graph.Vertex> univistedFactory (char factory){
        WidgetSet temp = this;
        Vector<Character> unique = new Vector<>();
        //int sum = 0;
        for(int i = 0; i < temp.widgets.size();i++)
        {
            for(int j = 0; j < temp.widgets.elementAt(i).length(); j++){
                char c = temp.widgets.elementAt(i).getSequence().charAt(j);
                if (!unique.contains(c)){
                    unique.add(c);
                }
            }
        }
        Vector<Graph.Vertex> unvisited = new Vector<>();
        for (char t : unique){
            Graph.Vertex vertex = new Graph.Vertex(t);
            unvisited.add(vertex);
        }

        return unvisited;
        //return sum;
    }
}
