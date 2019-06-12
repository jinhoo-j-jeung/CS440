import java.util.*;

public class Graph {
    /*
        classes
     */
    public static class Edge {
        Vector<Graph.Vertex> lookUp;
        Vertex source;
        Vertex dest;
        int cost = 1;
        public Edge (Vertex source, Vertex dest) {
            this.source = source;
            this.dest = dest;
            lookUp = new Vector<>();
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Edge)) return false;

            Edge _obj = (Edge) obj;
            return (_obj.source.equals(source) && _obj.dest.equals(dest));
        }

    }

    public static class Vertex {
        char c;
        int cost = Integer.MAX_VALUE;
        public Vertex (char c) {
            this.c = c;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (!(obj instanceof Vertex)) return false;

            Vertex _obj = (Vertex) obj;
            return _obj.c == c;
        }
    }

    /*
        global variables
     */

    public Vector<Edge> getEdges() {
        return edges;
    }

    private Vector<Edge> edges;

    public Vector<Vertex> getVertices() {
        return vertices;
    }


    private Vector<Vertex> vertices;

    /*
        Constructor
     */
    public Graph () {
        this.edges = new Vector<>();
        this.vertices = new Vector<>();
    }
    public void addVertex(Vertex newVertex) {
        if (!vertices.contains(newVertex)) {
            vertices.add(newVertex);
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

    public Vertex findVertex(char c){
        Vertex v = new Vertex(c);
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

    public void printGraph() {
        System.out.println("Print vertices");
        for (Vertex vertex: this.vertices){
            System.out.println(vertex.c);
        }

        System.out.println("");
        System.out.println("Print edges");
        for (Edge edge : this.edges){
            System.out.println("("+edge.source.c +" , " +edge.dest.c+")" + " = " + edge.cost);
        }


    }
}
