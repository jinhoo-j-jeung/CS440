public class tester {

    public static void main(String[] args)
    {

        /*
            Test createGraph
         */
        WidgetSet widgets = new WidgetSet();
        /*
        widgets.append(new Widget("ABC"));
        widgets.append(new Widget("ABC"));
        widgets.append(new Widget("ABC"));
        widgets.append(new Widget("ABC"));
        widgets.append(new Widget("ABC"));*/
        /*
        widgets.append(new Widget("AEDCA"));
        widgets.append(new Widget("BEACD"));
        widgets.append(new Widget("BABCE"));
        widgets.append(new Widget("DADBD"));
        widgets.append(new Widget("BECBD"));
        */
        ProblemGenerator p1 = new ProblemGenerator(6);
        System.out.println("A new set of widgets is " + p1.widgets.elementAt(0).getSequence() + ", " + p1.widgets.elementAt(1).getSequence()
        + ", " + p1.widgets.elementAt(2).getSequence() + ", " + p1.widgets.elementAt(3).getSequence()+ ", " + p1.widgets.elementAt(4).getSequence());
        int[][] cost = { {   0, 1064,  673, 1401, 277},
                         {1064, 0   ,  958, 1934, 337},
                         {673 , 958 ,    0, 1001, 399},
                         {1401, 1934, 1001,    0, 387},
                         {277 , 337 ,  399,  387,   0}};
/*
        int[][] cost = { {0, 200, 400},
                         {200, 0, 300},
                         {400, 300, 0}};
*/      widgets.widgets = p1.widgets;
/*
        AStar astar_simple = new AStar(widgets);
        System.out.println("simple astar sol : " + astar_simple.sol);
        System.out.println("Length is " + astar_simple.sol.length());
        System.out.println("Astar without distance Step Cost is " + astar_simple.stepCost);
*/
        AStarDistance astar = new AStarDistance(widgets, cost);

        System.out.println("dist astar sol : " + astar.sol);
        System.out.println("Length is " + astar.sol.length());
        System.out.println("Astar distance Step Cost is " + astar.stepCost);
        System.out.println("Total Cost is " + astar.totalCost);

        /*CreateGraph cg = new CreateGraph();
        Graph graph = cg.getGraph(widgets.widgets);
        graph.printGraph();
        System.out.println("startLength = " + widgets.totalLength());
        int remain = widgets.remainingLength('B');
        System.out.println("remain Length = " + remain);
        int remain2 = widgets.remainingLength('C');
        System.out.println("remain Length = " + remain2);*/

        //ProblemGenerator p1 = new ProblemGenerator(8);
        //System.out.println("A new set of widgets is " + p1.widgets.elementAt(0).getSequence() + ", " + p1.widgets.elementAt(1).getSequence()
        //+ ", " + p1.widgets.elementAt(2).getSequence() + ", " + p1.widgets.elementAt(3).getSequence()+ ", " + p1.widgets.elementAt(4).getSequence());

//        CreateGraph cg = new CreateGraph();
//
//        Graph graph = cg.getGraph(p1.widgets);
//        graph.printGraph();
//        WidgetSet widgets_random = new WidgetSet();
//        for(int i = 0; i < p1.widgets.size();i++)
//        {
//            widgets_random.append(p1.widgets.elementAt(i));
//        }
//
//        System.out.println("A new set of widgets is " + widgets_random.widgets.elementAt(0).getSequence() + ", " + widgets_random.widgets.elementAt(1).getSequence()
//                + ", " + widgets_random.widgets.elementAt(2).getSequence() + ", " + widgets_random.widgets.elementAt(3).getSequence()+ ", " + widgets_random.widgets.elementAt(4).getSequence());
//
//        AStar solver = new AStar(widgets_random);
//        System.out.println(solver.sol);
//        System.out.println("Length is " + solver.sol.length());
//        System.out.println("random generate Step Cost is " + solver.stepCost);
        //UCS ucs = new UCS(widgets);
        //System.out.println("UCS simple : " + ucs.sol + " and stepCost is " + ucs.stepCost);
        //UCSDistance ucs_dist = new UCSDistance(widgets,cost);
        //System.out.println("UCS dist : " + ucs_dist.sol + " and stepCost is " + ucs_dist.stepCost);
        //System.out.println("UCS dist Length : " + ucs_dist.totalCost);
    }
}
