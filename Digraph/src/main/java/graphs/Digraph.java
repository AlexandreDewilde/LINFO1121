package graphs;
import java.util.ArrayList;
import java.util.List;

/**
 *Implement the Digraph.java interface in the Digraph.java class using an adjacency list
 * data structure to represent directed graphs.
 */
public class Digraph {

    private List<Integer>[] g;
    private int n_edges = 0;
    public Digraph(int V) {
        g = (ArrayList<Integer> []) new ArrayList[V];
        for (int i = 0; i < V; i++) {
            g[i] = new ArrayList<>();
        }
    }

    /**
     * The number of vertices
     */
    public int V() {
        return g.length;
    }

    /**
     * The number of edges
     */
    public int E() {
         return n_edges;
    }

    /**
     * Add the edge v->w
     */
    public void addEdge(int v, int w) {
        g[v].add(w);
        n_edges++;
    }

    /**
     * The nodes adjacent to node v
     * that is the nodes w such that there is an edge v->w
     */
    public Iterable<Integer> adj(int v) {
        // TODO
         return g[v];
    }

    /**
     * A copy of the digraph with all edges reversed
     */
    public Digraph reverse() {
        Digraph rev = new Digraph(g.length);
        for (int i = 0; i < g.length; i++) {
            for (int j : g[i]) {
                rev.addEdge(j, i);
            }
        }
        return rev;
    }

}
