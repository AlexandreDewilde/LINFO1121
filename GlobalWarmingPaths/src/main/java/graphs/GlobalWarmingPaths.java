package graphs;

import java.util.*;

public class GlobalWarmingPaths {

    int waterLevel;
    int [][] altitude;

    public GlobalWarmingPaths(int[][] altitude, int waterLevel) {
        this.waterLevel = waterLevel;
        this.altitude = altitude;
        // TODO
    }


    public List<Point> shortestPath(Point p1, Point p2) {
        if (altitude[p1.x][p1.y] <= waterLevel) return new ArrayList<Point>();
        int [] dx = {0,0,1,-1};
        int [] dy = {-1,1,0,0};
        int [][] dst = new int[altitude.length][altitude[0].length];
        Point [][] parent = new Point[altitude.length][altitude.length];
        for (int i = 0; i < altitude.length; i++) {
            for (int j=0; j< altitude[i].length; j++) {
                dst[i][j] = -1;
            }
        }
        parent[p1.x][p1.y] = p1;
        dst[p1.x][p1.y] = 0;
        Queue<Point> q = new LinkedList<>();
        q.add(p1);
        while (q.size() > 0) {
            Point p = q.poll();
            for (int i = 0; i <4; i++) {
                Point to = new Point(p.x+dx[i], p.y+dy[i]);
                if (to.x >= 0 && to.x < altitude.length && to.y >= 0 && to.y < altitude[0].length && dst[to.x][to.y] == -1 && altitude[to.x][to.y] > waterLevel) {
                    dst[to.x][to.y] = dst[p.x][p.y] + 1;
                    q.add(to);
                    parent[to.x][to.y] = p;
                }
            }
        }
        Point current = p2;
        if (dst[p2.x][p2.y] == -1) return new ArrayList<Point>();
        List<Point> path = new ArrayList<Point>();
        while (current != null && !current.equals(p1)) {
            path.add(current);
            current = parent[current.x][current.y];
        }
        path.add(p1);
        Collections.reverse(path);
        return path;
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

