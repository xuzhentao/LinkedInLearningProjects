/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

public class UnionFindTest {
    public static void main(String[] args) {
        QuickFindUF uf = new QuickFindUF(10);
        uf.union(1, 3);
        uf.union(3, 5);
        uf.union(2, 4);
        uf.union(2, 3);
        System.out.println("2 and 3 connection = " + uf.connected(2, 3));


        QuickUnionUF uf2 = new QuickUnionUF(10);
        uf2.union(1, 3);
        uf2.union(3, 5);
        System.out.println("2 and 3 connection = " + uf2.connected(2, 3));
        uf2.union(2, 4);
        System.out.println("2 and 3 connection = " + uf2.connected(2, 3));
        uf2.union(2, 3);
        System.out.println("2 and 3 connection = " + uf2.connected(2, 3));


        QuickUnionImproveUF uf3 = new QuickUnionImproveUF(10);
        uf3.union(1, 3);
        uf3.union(3, 5);
        System.out.println("2 and 3 connection = " + uf3.connected(2, 3));
        uf3.union(2, 4);
        System.out.println("2 and 3 connection = " + uf3.connected(2, 3));
        uf3.union(2, 3);
        System.out.println("2 and 3 connection = " + uf3.connected(2, 3));

    }
}


