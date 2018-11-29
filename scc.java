package ssm170730;

/** Starter code for SP8
 *  @author
 *  Shruti Jaiswal (ssm170730)
 * Shreyash Mane (ssm170730)
 */


import ssm170730.Graph.Vertex;
import ssm170730.Graph.Edge;
import ssm170730.Graph.GraphAlgorithm;
import ssm170730.Graph.Factory;
import ssm170730.Graph.Timer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;

public class DFS extends GraphAlgorithm<DFS.DFSVertex> {

    int cno = 0; //stores the number of components in a graph
    LinkedList<Vertex> finishList;

    public static class DFSVertex implements Factory {
        int cno; //component no. of each vertex
        int seen; //used to check whether the vertex is visited or not
        Vertex parent;

        public DFSVertex(Vertex u)
        {
            cno = 0;
            seen = 0;
            parent = null;
        }

        public DFSVertex make(Vertex u)
        { return new DFSVertex(u); }
    }

    public DFS(Graph g) {
        super(g, new DFSVertex(null));
    }

    //returns the seen value for vertex u
    public  int getSeen(Vertex u) {
        return get(u).seen;
    }

    //used to set the seen value for vertex u
    public void setSeen(Vertex u, int value) {
        get(u).seen = value;
    }

    ////returns the parent for vertex u
    public Vertex getParent(Vertex u) {
        return get(u).parent;
    }

    //used to set the parent for vertex u
    public void setParent(Vertex u, Vertex p) {
        get(u).parent = p;
    }

    //used to set the component no of a vertex u
    public void setCno(Vertex u, int c) {
        get(u).cno = c;
    }

    public static DFS depthFirstSearch(Graph g) {
        DFS d = new DFS(g);
        d.dfs(g);
        return depthFirstSearch(g);
    }


    // Member function to find topological order
    public List<Vertex> topologicalOrder1() {
        dfs(g);
        List<Vertex> topoSort = finishList;
        if(!g.directed)
            return null;
        return topoSort;
    }

    // After running the onnected components algorithm, the component no of each vertex can be queried.
    public int cno(Vertex u) {
        return get(u).cno;
    }

    // Find topological oder of a DAG using DFS. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder1(Graph g) {
        DFS d = new DFS(g);
        return d.topologicalOrder1();
    }

    //performs depth first search for a given iterable object
    void dfs(Iterable<Vertex> iter)
    {
        finishList = new LinkedList<Vertex>();
        for(Vertex u:iter)
        {
            if(getSeen(u)==0)
                dfsVisit(u, ++cno);
        }
    }

    //extension of dfs implementation
    public void dfsVisit(Vertex u, int comp) {
        setSeen(u, 1);
        setCno(u, comp);
        for (Edge e : g.incident(u))
        {
            Vertex v = e.otherEnd(u);
            if (getSeen(v) == 0)
            {
                setParent(v, u);
                dfsVisit(v, comp);
            }
        }
        finishList.addFirst(u);
    }

    public static DFS stronglyConnectedComponents(Graph g)
    {
        DFS d = new DFS(g);
        d.dfs(g);
        List<Vertex> list = d.finishList;
        g.reverseGraph();
        d.dfs(list);
        g.reverseGraph();
        return d;
    }

    // Find topological oder of a DAG using the second algorithm. Returns null if g is not a DAG.
    public static List<Vertex> topologicalOrder2(Graph g) {
        return null;
    }

    public static void main(String[] args) throws Exception {
        String string = "7 8   1 2 2   1 3 3   2 4 5   3 4 4   4 5 1   5 1 7   6 7 1   7 6 1 0";
        Scanner in;
        // If there is a command line argument, use it as file from which
        // input is read, otherwise use input from string.
        in = args.length > 0 ? new Scanner(new File(args[0])) : new Scanner(string);

        // Read graph from input
        Graph g = Graph.readGraph(in);
        g.printGraph(false);

        DFS d = DFS.stronglyConnectedComponents(g);
        int numscc = d.cno;
        System.out.println("Number of strongly connected components: " + numscc + "\nu\tcno");
        for(Vertex u: g) {
            System.out.println(u + "\t" + d.cno(u));
        }
    }
}
