package sorting;

import java.util.Arrays;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,1,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Each entry in the matrix represents an altitude point at the corresponding grid coordinate.
 * The goal is to implement a GlobalWarmingImpl class that extends the GlobalWarming abstract class described below.
 *
 * Given a global water level, all positions in the matrix with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3, all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4), 1 ,(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is nbSafePoints
 * - calculating the number of safe points for a given water level
 *
 * Complete the code below. Pay attention to the expected time complexity of each method.
 * To meet this time complexity, you need to do some pre-computation in the constructor.
 * Feel free to create internal classes in GlobalWarmingImpl to make your implementation easier.
 * Feel free to use any method or data structure available in the Java language and API.
 */

abstract class GlobalWarming {


    protected final int[][] altitude;

    /**
     * @param altitude is a n x n matrix of int values representing altitudes (positive or negative)
     */
    public GlobalWarming(int[][] altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @param waterLevel
     * @return the number of entries in altitude matrix that would be above
     *         the specified waterLevel.
     *         Warning: this is not the waterLevel given in the constructor/
     */
    public abstract int nbSafePoints(int waterLevel);

}


public class GlobalWarmingImpl extends GlobalWarming {

    private int[] array;
    public GlobalWarmingImpl(int[][] altitude) {
        super(altitude);
        array = new int[altitude.length*altitude.length];
        for (int i = 0; i < altitude.length; i++) {
            for (int j=0; j < altitude.length; j++) {
                array[i*altitude.length+j] = altitude[i][j];
            }
        }
        Arrays.sort(array);
    }

    /**
     * Returns the number of safe points given a water level
     *
     * @param waterLevel the level of water
     */
    public int nbSafePoints(int waterLevel) {
        int start = 0; int end = array.length - 1;
        while (end - start > 0) {
            int mid = start + (end-start)/2;
            if (array[mid] <= waterLevel) {
                start = mid + 1;
            }
            else {
                end = mid;
            }
        }
        //if (array[start] > waterLevel) end = Math.min(start, end);
        return array.length - end - (array[end] <= waterLevel ? 1 : 0);
    }



}
