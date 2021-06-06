/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private final int nBlocks;
    private final int idxPseudoHead;
    private final int idxPseudoTail;
    private int nOpen;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        this.n = n;
        nOpen = 0;
        if (n <= 0) throw new IllegalArgumentException();
        nBlocks = n * n;
        idxPseudoHead = nBlocks;
        idxPseudoTail = nBlocks + 1;

        // initialize UnionFind, the last two element are psaudo head and tail.
        uf = new WeightedQuickUnionUF(nBlocks + 2);


        // initialize grid for track status.
        grid = new boolean[n][n];

        // initialize grid.
        for (int rowIdx = 0; rowIdx < n; rowIdx++) {
            for (int colIdx = 0; colIdx < n; colIdx++) {
                grid[rowIdx][colIdx] = false;
            }
        }
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation p = new Percolation(5);

        System.out.println("Expect false" + p.isOpen(1, 1));
        System.out.println("Expect false" + p.isFull(1, 1));

        p.open(3, 1);
        System.out.println("Expect true" + p.isOpen(3, 1));
        System.out.println("Expect false" + p.isFull(3, 1));
        p.open(1, 1);
        p.open(2, 1);
        System.out.println("Expect true" + p.isOpen(3, 1));
        System.out.println("Expect true" + p.isFull(3, 1));


    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isValidRowCol(row, col)) {
            throw new IllegalArgumentException(
                    "The input " + row + " and " + col + " is illegal, given that this.n = "
                            + n);
        }
        int rowIdx = row - 1;
        int colIdx = col - 1;

        return grid[rowIdx][colIdx];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isValidRowCol(row, col)) {
            throw new IllegalArgumentException();
        }
        int idx = getIdx(row, col);
        return uf.find(idx) == uf.find(nBlocks);
    }

    // opens the site (row, col) if it is not open already TODO
    public void open(int row, int col) {

        int rowIdx = row - 1;
        int colIdx = col - 1;

        if (!isValidRowCol(row, col)) {
            throw new IllegalArgumentException();
        }


        if (!isOpen(row, col)) {
            if (isValidRowCol(row - 1, col) && isOpen(row - 1, col)) {
                uf.union(getIdx(row - 1, col), getIdx(row, col));
            }
            if (isValidRowCol(row + 1, col) && isOpen(row + 1, col)) {
                uf.union(getIdx(row + 1, col), getIdx(row, col));
            }
            if (isValidRowCol(row, col - 1) && isOpen(row, col - 1)) {
                uf.union(getIdx(row, col - 1), getIdx(row, col));
            }
            if (isValidRowCol(row, col + 1) && isOpen(row, col + 1)) {
                uf.union(getIdx(row, col + 1), getIdx(row, col));
            }
            // special case for first or last row item
            if (row == 1) uf.union(idxPseudoHead, getIdx(row, col));
            if (row == n) uf.union(idxPseudoTail, getIdx(row, col));
            grid[rowIdx][colIdx] = true;
            nOpen += 1;
        }


    }

    private boolean isValidRowCol(int row, int col) {
        return 0 < row && row <= n && 0 < col && col <= n;
    }

    private int getIdx(int row, int col) {
        return (row - 1) * n + col - 1;
    }

    // does the system percolate? TODO
    public boolean percolates() {
        return uf.find(nBlocks) == uf.find(nBlocks + 1);

    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return nOpen;
    }

}
