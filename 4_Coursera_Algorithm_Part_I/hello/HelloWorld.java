/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.ST;

import java.util.Arrays;

public class HelloWorld {
    public static void main(String[] args) {
        int[] a = { 1, 2, 3 };
        int[] b = { 1, 2, 3 };
        System.out.println(Arrays.deepEquals(new int[][] { a }, new int[][] { b }));

        ST<Integer, Integer> st = new ST<>();
        st.put(1, 2);
        System.out.println(st);

    }
}
