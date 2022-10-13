package graphs;


/**
 * In this exercise, we revisit the GlobalWarming class from the sorting package.
 * You are still given a matrix of altitude in parameter of the constructor, with a water level.
 * All the entries whose altitude is under, or equal to, the water level are submerged while the other constitute small islands.
 *
 * For example let us assume that the water level is 3 and the altitude matrix is the following
 *
 *      | 1 | 3 | 3 | 1 | 3 |
 *      | 4 | 2 | 2 | 4 | 5 |
 *      | 4 | 4 | 1 | 4 | 2 |
 *      | 1 | 4 | 2 | 3 | 6 |
 *      | 1 | 1 | 1 | 6 | 3 |
 * 
 * If we replace the submerged entries by _, it gives the following matrix
 *
 *      | _ | _ | _ | _ | _ |
 *      | 4 | _ | _ | 4 | 5 |
 *      | 4 | 4 | _ | 4 | _ |
 *      | _ | 4 | _ | _ | 6 |
 *      | _ | _ | _ | 6 | _ |
 *
 * The goal is to implmets two methods that can answer the following questions:
 *      1) Are to entries on the same island?
 *      2) How many island is there
 *
 * Two entries above the water level are connected if they are next to each other on
 * the same row or the same column. They are **not** connected **in diagonal**.
 * Beware that the methods must run in O(1) time complexity, at the cost of a pre-processing in the constructor.
 * To help you, you'll find a `Point` class in the utils package which identified an entry of the grid.
 * Carefully read the expected time complexity of the different methods.
 */
public class GlobalWarming {


    /**
     * Constructor. The run time of this method is expected to be in 
     * O(n x log(n)) with n the number of entry in the altitude matrix.
     *
     * @param altitude the matrix of altitude
     * @param waterLevel the water level under which the entries are submerged
     */
    int [] parents;
    int [] ranks;
    int nbIsle = 0;
    int n; int m;
    public GlobalWarming(int [][] altitude, int waterLevel) {
        n = altitude.length; m = altitude[0].length;
        parents = new int[n*m];
        ranks = new int[n*m];
        boolean [] visited = new boolean[altitude.length * altitude[0].length];
        for (int i = 0; i < altitude.length; i++) {
            for (int j = 0; j < altitude.length; j++) {
                parents[i+j* altitude.length] = i + j* altitude.length;
                ranks[i+j*altitude.length] = 0;
                visited[i+j* altitude.length] = false;
            }
        }
        for (int i = 0; i < altitude.length; i++) {
            for (int j = 0; j < altitude[0].length; j++) {
                if (visited[i + j *altitude.length] || altitude[i][j] <= waterLevel) continue;
                nbIsle++;
                dfs(i, j, i, j, waterLevel, altitude, visited);
            }
        }
        for (int i = 0; i < altitude.length; i++) {
            for (int j = 0; j < altitude[0].length; j++) {
                parents[i + j * n] = find(i + j * n);
            }
        }
    }

    public void dfs(int x, int y, int jx, int jy, int waterLevel, int [][] altitude, boolean [] visited) {
        int [] dx = {-1,1,0,0};
        int [] dy = {0,0,1,-1};
        for (int i = 0; i < 4; i++) {
            int xx = x + dx[i];
            int yy = y + dy[i];
            if (xx < 0 || xx >= n || yy < 0 || yy >= n) {
                continue;
            }
            if (visited[xx + yy*altitude.length] || altitude[xx][yy] <= waterLevel){
                continue;
            }
            visited[xx + yy* altitude.length] = true;
            union(xx+yy*altitude.length, jx + jy* altitude.length);
            dfs(xx, yy, jx, jy, waterLevel, altitude, visited);
        }
    }

    public int find(int x) {
        if (parents[x] == x) return x;
        return find(parents[x]);
    }

    public void union(int x, int y) {
        x = find(x);
        y = find(y);
        if (x != y) {
            if (ranks[x] > ranks[y]) {
                parents[y] = x;
            }
            else if (ranks[x] < ranks[y]) {
                parents[x] = y;
            }
            else {
                parents[y] = x;
                ranks[x]++;
            }
        }
    }

    /**
     * Returns the number of island
     *
     * Expected time complexity O(1)
     */
    public int nbIslands() {
         return nbIsle;
    }

    /**
     * Return true if p1 is on the same island than p2, false otherwise
     *
     * Expected time complexity: O(1)
     *
     * @param p1 the first point to compare
     * @param p2 the second point to compare
     */
    public boolean onSameIsland(Point p1, Point p2) {
        return parents[(p2.x + p2.y*n)] == parents[(p1.x + p1.y*n)];
    }


    /**
     * This class represent a point in a 2-dimension discrete plane. This is used, for instance, to
     * identified cells of a grid
     */
    static class Point {

        private final int x;
        private final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof Point) {
                Point p = (Point) o;
                return p.x == this.x && p.y == this.y;
            }
            return false;
        }
    }
}
