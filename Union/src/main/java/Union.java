package sorting;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Author Pierre Schaus
 *
 * Given an array of (closed) intervals, you are asked to implement the union operation.
 * This operation will return the minimal array of sorted intervals covering exactly the union
 * of the points covered by the input intervals.
 * For example, the union of intervals [7,9],[5,8],[2,4] is [2,4],[5,9].
 * The Interval class allowing to store the intervals is provided
 * to you.
 *
 */
public class Union {

    /**
     * A class representing an interval with two integers. Hence the interval is
     * [min, max].
     */
    public static class Interval implements Comparable<Union.Interval> {

        public final int min;
        public final int max;

        public Interval(int min, int max) {
            assert(min <= max);
            this.min = min;
            this.max = max;
        }

        @Override
        public boolean equals(Object obj) {
            return ((Union.Interval) obj).min == min && ((Union.Interval) obj).max == max;
        }

        @Override
        public String toString() {
            return "["+min+","+max+"]";
        }

        @Override
        public int compareTo(Union.Interval o) {
            if (min < o.min) return -1;
            else if (min == o.min) return max - o.max;
            else return 1;
        }
    }

    /**
     * Returns the union of the intervals given in parameters.
     * This is the minimal array of (sorted) intervals covering
     * exactly the same points than the intervals in parameter.
     * 
     * @param intervals the intervals to unite.
     */
    public static Interval[] union(Interval[] intervals) {

        ArrayList<Interval> lst = new ArrayList<>();
        Arrays.sort(intervals);
        int c_min = intervals[0].min;
        int c_max = intervals[0].max;
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i].min <= c_max) {
                c_max = Math.max(intervals[i].max, c_max);
            }
            else {
                lst.add(new Interval(c_min, c_max));
                c_min = intervals[i].min;
                c_max = intervals[i].max;
            }
        }
        lst.add(new Interval(c_min, c_max));
        Interval[] intervals1 = new Interval[lst.size()];
        for (int i = 0; i < lst.size(); i++) intervals1[i] = lst.get(i);
        return intervals1;

    }

}
