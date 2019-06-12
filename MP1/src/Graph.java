import java.util.*;

public class Graph {

    public Vertex getStartVertex() {
        return startVertex;
    }

    /*
        classes
     */
    public static class Edge {
        Vertex source;
        Vertex dest;
        int cost = 1;
        public Edge (Vertex source, Vertex dest) {
            this.source = source;
            this.dest = dest;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Edge)) return false;

            Edge _obj = (Edge) obj;
            return (_obj.source.equals(source) && _obj.dest.equals(dest)) || ((_obj.source.equals(dest) && _obj.dest.equals(source)));// && _obj.cost == cost;
        }

    }

    public static class Vertex {
        boolean visited = false;
        Vertex prev;
        Pair pair;
        int heuristic = 0;
        int pathcost = 0;
        public Vertex (Pair pair) {
            this.pair = pair;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Vertex)) return false;

            Vertex _obj = (Vertex) obj;
            return _obj.pair.getKey() == pair.getKey() && _obj.pair.getValue() == pair.getValue();
        }

        @Override
        public int hashCode() {
            String s = "" + pair.getKey() + pair.getValue();
            return Integer.parseInt(s);
        }

    }

    public static class Pair {
        private int key;
        private int value;

        public Pair(int key, int value){
            this.key = key;
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public int getKey() {
            return key;
        }

        public String printPair() {
            return ("(" + key + ", " + value + ")");
        }
    }

    /*
        global variables
     */
    private char[][] maze;

    public Vector<Edge> getEdges() {
        return edges;
    }

    private Vector<Edge> edges;

    public Vector<Vertex> getVertices() {
        return vertices;
    }


    private Vector<Vertex> vertices;
    private Map<Vertex, Set<Vertex>> adjList = new HashMap<>();
    private Vertex startVertex;

    public void setDestinations(Vector<Vertex> destinations) {
        this.destinations = destinations;
    }

    private Vector<Vertex> destinations = new Vector<>();

    /*
        Getter
     */
    public Map<Vertex, Set<Vertex>> getAdjList() {
        return adjList;
    }

    public Vector<Vertex> getDestinations() {
        return destinations;
    }

    /*
        Constructor
     */
    public Graph (char[][] maze) {
        this.edges = new Vector<>();
        this.vertices = new Vector<>();
        this.maze = maze;
        createGraph();
    }

    public Graph () {
        this.edges = new Vector<>();
        this.vertices = new Vector<>();
    }
    public void addVertex(Vertex vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
        }
    }

    public Vector<Edge> findAllEdge(Vertex vertex) {
        Vector<Edge> ret = new Vector<>();
        for (Edge edge : edges) {
            if (edge.source.equals(vertex) || edge.dest.equals(vertex)){
                ret.add(edge);
            }
        }
        return ret;
    }

    public void addEdge(Edge edge) {
        if (!edges.contains(edge)){
            edges.add(edge);
        }
    }

    public Vertex findVertex(int i, int j){
        Vertex v = new Vertex(new Pair(i, j));
        int index = vertices.indexOf(v);
        if (index == -1){
            return null;
        }
        else {
            return vertices.elementAt(index);
        }
    }

    public Edge findEdge(Vertex source, Vertex dest){
        Edge e = new Edge(source, dest);
        for(Edge edge:edges){
            if(edge.equals(e)){
                return edge;
            }
        }
        return null;
    }

    public int getEdgeCost (Vertex source, Vertex dest){
        Edge edge = findEdge(source, dest);
        return edge.cost;
    }
    public void createGraph() {
        int height = maze.length;
        int width = maze[0].length;

        for (int i = 0; i < height; i ++){
            for (int j = 0 ; j < width ; j ++){
                char cur = maze[i][j];
                if (cur == 'P' || cur == ' ' || cur == '.'){
                    Pair curPair = new Pair(i,j);
                    Vertex curVertex = new Vertex(curPair);
                    if (cur == 'P') {
                        startVertex = curVertex;
                    }
                    if (cur == '.') {
                        destinations.add(curVertex);
                    }
                    addVertex(curVertex);

                    if (i - 1 >= 0 && maze[i-1][j] != '%') {
                        makeAdj(i-1, j, curVertex);
                    }
                    if (j - 1 >= 0 && maze[i][j-1] != '%') {
                        makeAdj(i, j-1, curVertex);
                    }
                    if ((i + 1) <= (height -1) && maze[i+1][j] != '%') {
                        makeAdj(i+1, j, curVertex);
                    }
                    if ((j + 1) <= (width -1) && maze[i][j+1] != '%') {
                        makeAdj(i, j+1, curVertex);
                    }
                }
            }
        }
    }

    private void makeAdj(int i, int j, Vertex curVertex) {
        Vertex prevVertex = findVertex(i, j);
        if (prevVertex == null) {
            prevVertex = new Vertex(new Pair(i,j));
            vertices.add(prevVertex);
        }

        Edge newEdge = new Edge(curVertex, prevVertex);
        addEdge(newEdge);
        if (adjList.containsKey(curVertex)) {
            Set<Vertex> val = adjList.get(curVertex);
            val.add(prevVertex);
            adjList.put(curVertex, val);
        }
        else {
            Set<Vertex> vertexSet = new HashSet<>();
            vertexSet.add(prevVertex);
            adjList.put(curVertex, vertexSet);
        }
    }

    public void printGraph() {
        for (Vertex v: vertices) {
            System.out.println("Vertex = (" + v.pair.getKey() + ", " + v.pair.getValue() + ")");
        }
        System.out.println("Number of vertices = " + vertices.size() + "\n");
        for (Edge e: edges) {
            System.out.print("Edge = (" + e.source.pair.getKey() + ", " + e.source.pair.getValue() + "), (" + e.dest.pair.getKey() + ", " + e.dest.pair.getValue() + ")");
            System.out.println("cost = " + e.cost);
        }
        System.out.println("Number of Edges = " + edges.size());

        System.out.println("\n\nAdjacent List");
        for (Map.Entry<Vertex, Set<Vertex>> entry : adjList.entrySet()) {
            System.out.print("Vertex = " + entry.getKey().pair.printPair() + ", ");
            System.out.print("Adjacent vertices = ");
            Set<Vertex> tempVertices = entry.getValue();
            for (Vertex t: tempVertices) {
                System.out.print(t.pair.printPair() + ", ");
            }
            System.out.println("");

        }

        System.out.println("Size of adjList = " + adjList.size());
    }
}
