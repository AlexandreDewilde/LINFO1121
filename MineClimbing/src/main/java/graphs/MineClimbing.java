package graphs;

//feel free to import anything here


import java.util.PriorityQueue;

/**
 * You just bought yourself the latest game from the Majong™ studio (recently acquired by Macrosoft™): MineClimb™.
 * In this 3D game, the map is made up of size 1 dimensional cube blocks, aligned on a grid, forming vertical columns of cubes.
 * There are no holes in the columns, so the state of the map can be represented as a matrix M of size n⋅m
 * where the entry Mi,j is the height of the cube column located at i,j (0≤i<n, 0≤j<m).
 * You can't delete or add cubes, but you do have climbing gear that allows you to move from one column to another
 * (in the usual four directions (north, south, east, west), but not diagonally). The world of MineClimb™ is round:
 * the position north of (0,j) is (n-1,j), the position west of (i,0) is (i,m-1) , and vice versa.
 * <p>
 * The time taken to climb up or down a column depends on the difference in height between the current column and the next one.
 * Precisely, the time taken to go from column (i,j) to column (k,l) is |M_{i,j}-M_{k,l}|
 * <p>
 * <p>
 * Given the map of the mine, an initial position and an end position,
 * what is the minimum time needed to reach the end position from the initial position?
 */
public class MineClimbing {



    /**
     * Returns the minimum distance between (startX, startY) and (endX, endY), knowing that
     * you can climb from one mine block (a,b) to the other (c,d) with a cost Math.abs(map[a][b] - map[c][d])
     * <p>
     * Do not forget that the world is round: the position (map.length,i) is the same as (0, i), etc.
     * <p>
     * map is a matrix of size n times m, with n,m > 0.
     * <p>
     * 0 <= startX, endX < n
     * 0 <= startY, endY < m
     */
    public static int best_distance(int[][] map, int startX, int startY, int endX, int endY) {
        int [] dx = {-1,1,0,0};
        int [] dy = {0,0,-1,1};
        int dst[][] = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            for (int j = 0 ; j < map[0].length; j++ ) {
                dst[i][j] = Integer.MAX_VALUE;
            }
        }
        PriorityQueue<Triple> pq = new PriorityQueue<Triple>();
        Triple tt = new Triple(0, startX, startY);
        dst[startX][startY] = 0;
        pq.add(tt);
        while (pq.size() > 0) {
            int w, x, y;
            Triple tp = pq.poll();
            x = tp.y; y = tp.z; w = tp.x;
            if (dst[x][y] < w) continue;
            for (int d = 0; d < 4; d++) {
                int xx = x + dx[d]; int yy = y + dy[d];
                xx %= map.length; yy %= map[0].length;
                if (xx < 0) xx = map.length - 1;
                if (yy < 0) yy = map[0].length - 1;
                int dd = Math.abs(map[x][y] - map[xx][yy]);
                if (dst[x][y] + dd < dst[xx][yy]) {
                    dst[xx][yy] = dst[x][y] + dd;
                    pq.add(new Triple(dst[xx][yy], xx, yy));
                }
            }
        }
        return dst[endX][endY];
    }
}
class Triple implements Comparable<Triple> {
    int x, y, z;
    public Triple(int x, int y, int z) {
        this.x = x; this.y = y; this.z = z;
    }

    @Override
    public  int compareTo(Triple o) {
        if (x != o.x) {
            return x - o.x;
        }
        if (y != o.y) return y - o.y;
        return z - o.z;
    }
}
