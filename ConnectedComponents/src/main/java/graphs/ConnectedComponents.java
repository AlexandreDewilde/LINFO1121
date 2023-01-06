package graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * You are asked to implement the ConnectedComponent class given a graph.
 * The Graph class available in the code is the one of the Java class API.
 * <p>
 * public class ConnectedComponents {
 * <p>
 * public static int numberOfConnectedComponents(Graph g){
 * // TODO
 * return 0;
 * }
 * }
 * Hint: Feel free to add methods or even inner-class (private static class) to help you but don't change the class name or the signature of the numberOfConnectedComponents method.
 * Don't forget to add the imports at the beginning of your code if you use objects from the Java API.
 *
 *
 */
public class ConnectedComponents {


    /**
     * @return the number of connected components in g
     */
    public static int numberOfConnectedComponents(Graph g) {
        boolean [] vis = new boolean[g.V()];
        int ans = 0;
        ArrayList<Integer> s = new ArrayList<>();
        for (int x = 0; x < g.V(); x++) {
            if (vis[x]) continue;
            dfs2(x, vis, g);
            ++ans;
        }

        return ans;
    }

    private static void dfs2(int x, boolean [] visited, Graph g) {
        Stack<Integer> s = new Stack<>();
        s.add(x);
        while (s.size() > 0) {
            int y = s.pop();
            visited[y] = true;
            for (int adj: g.adj(y)) {
                if (visited[adj]) continue;
                s.add(adj);
            }
        }
    }

    static class Graph {

        private List<Integer>[] edges;

        public Graph(int nbNodes)
        {
            this.edges = (ArrayList<Integer>[]) new ArrayList[nbNodes];
            for (int i = 0;i < edges.length;i++)
            {
                edges[i] = new ArrayList<>();
            }
        }

        /**
         * @return the number of vertices
         */
        public int V() {
            return edges.length;
        }

        /**
         * @return the number of edges
         */
        public int E() {
            int count = 0;
            for (List<Integer> bag : edges) {
                count += bag.size();
            }

            return count/2;
        }

        /**
         * Add edge v-w to this graph
         */
        public void addEdge(int v, int w) {
            edges[v].add(w);
            edges[w].add(v);
        }

        /**
         * @return the vertices adjacent to v
         */
        public Iterable<Integer> adj(int v) {
            return edges[v];
        }
    }

}
