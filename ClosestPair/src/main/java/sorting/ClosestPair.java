package sorting;

import java.util.Arrays;
import java.util.Collections;

/**
 * Let a be an array of integers. In this exercise we are interested in finding
 * the two entries i and j such that a[i] + a[j] is the closest from a target x.
 * In other words, there are no entries k,l such that |x - (a[i] + a[j])| > |x - (a[k] + a[l])|.
 * Note that we can have i = j.
 * Your program must return the values a[i] and a[j].
 *
 * For example let a = [5, 10, 1, 75, 150, 151, 155, 18, 75, 50, 30]
 *
 * then for x = 20, your program must return the array [10, 10],
 *      for x = 153 you must return [1, 151] and
 *      for x = 170 you must return [18, 151]
 */
public class ClosestPair {

    /**
      * Finds the pair of integers which sums up to the closest value of x
      *
      * @param a the array in which the value are looked for
      * @param x the target value for the sum
      */
    public static int[] closestPair(int [] a, int x) {
        if (a==null) return null;
        Arrays.sort(a);
        int i = 0;
        int j = a.length - 1;
        int t1 = 0; int t2 = a.length - 1;
        while (i <= j) {
            int res = a[i] + a[j];
            if (res == x) return new int[]{a[i],a[j]};
            //System.out.println(a[i]+a[j]);
            if (Math.abs(x-a[t1]-a[t2]) > Math.abs(x-a[i]-a[j])) {
                t1 = i;
                t2 = j;
            }
            if (res > x) {
                j--;
            }
            else {
                i++;
            }
        }
        return new int[]{a[t1],a[t2]};

    }
}
