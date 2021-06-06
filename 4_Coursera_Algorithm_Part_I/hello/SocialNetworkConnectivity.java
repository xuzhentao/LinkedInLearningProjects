/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.Arrays;

public class SocialNetworkConnectivity {
    public static void main(String[] args) {
        int[][] logs = {
                { 1, 2, 3 },
                { 2, 3, 4 },
                { 3, 1, 2 },
                { 4, 2, 4 },
                { 5, 4, 5 },
                { 6, 0, 1 },
                { 7, 2, 3 }
        };
        System.out.println(Arrays.deepToString(logs));

        QuickUnionImproveUF uf = new QuickUnionImproveUF(6);

        for (int[] log : logs) {
            System.out.println(Arrays.toString(log));
            uf.union(log[1], log[2]);
            System.out.println("Not all connected? " + uf.allconnected());
        }


    }
}
