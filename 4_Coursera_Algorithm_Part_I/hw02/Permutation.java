/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */


import edu.princeton.cs.algs4.StdIn;

import java.util.Iterator;

public class Permutation {
    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int k = Integer.parseInt(args[0]);
        for (int idx = 0; idx < k; idx++) {
            String strIn = StdIn.readString();
            rq.enqueue(strIn);
        }
        Iterator<String> itr = rq.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }
}
