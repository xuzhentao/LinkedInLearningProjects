import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Board {

    private final int[][] tiles;
    private final int n;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = arrayCopy(tiles);
        n = tiles.length;
    }

    private int[][] arrayCopy(int[][] arr) {
        int[][] arrCopy = new int[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++)
            for (int j = 0; j < arr[0].length; j++)
                arrCopy[i][j] = arr[i][j];
        return arrCopy;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // int[][] arr = { { 4, 1, 3 }, { 0, 2, 5 }, { 7, 8, 6 } };
        int[][] arr = { { 1, 2, 3 }, { 4, 6, 5 }, { 7, 8, 0 } };
        Board bd = new Board(arr);
        // System.out.println(bd.hamming());
        // System.out.println(bd.manhattan());
        System.out.println(bd);
        System.out.println(bd.neighbors());
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int zeroI = 0, zeroJ = 0;
        List<Board> res = new ArrayList<>();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (tiles[i][j] == 0) {
                    zeroI = i;
                    zeroJ = j;
                    break;
                }
        if (zeroI > 0) {
            int[][] arrayCopy = arrayCopy(tiles);
            arraySwitch(arrayCopy, zeroI, zeroJ, zeroI - 1, zeroJ);
            res.add(new Board(arrayCopy));
        }

        if (zeroI < n - 1) {
            int[][] arrayCopy = arrayCopy(tiles);
            arraySwitch(arrayCopy, zeroI, zeroJ, zeroI + 1, zeroJ);
            res.add(new Board(arrayCopy));
        }

        if (zeroJ > 0) {
            int[][] arrayCopy = arrayCopy(tiles);
            arraySwitch(arrayCopy, zeroI, zeroJ, zeroI, zeroJ - 1);
            res.add(new Board(arrayCopy));
        }

        if (zeroJ < n - 1) {
            int[][] arrayCopy = arrayCopy(tiles);
            arraySwitch(arrayCopy, zeroI, zeroJ, zeroI, zeroJ + 1);
            res.add(new Board(arrayCopy));
        }

        return res;


    }

    private void arraySwitch(int[][] arr, int i1, int j1, int i2, int j2) {
        int temp = arr[i1][j1];
        arr[i1][j1] = arr[i2][j2];
        arr[i2][j2] = temp;
    }

    // number of tiles out of place
    public int hamming() {
        int dist = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (tiles[i][j] != i * n + j + 1 && !(i == n - 1 && j == n - 1))
                    dist++;
        return dist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int dist = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                if (tiles[i][j] != 0) {
                    int iExpected = (tiles[i][j] - 1) / n;
                    int jExpected = (tiles[i][j] - 1) % n;
                    dist += abs(i - iExpected) + abs(j - jExpected);
                }
        return dist;
    }

    private int abs(int i) {
        return i > 0 ? i : -i;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        Board yy = (Board) y;
        if (y == null) {
            throw new IllegalArgumentException("y is null");
        }
        if (dimension() != yy.dimension())
            return false;
        for (int i = 0; i < n; i++) {
            if (!Arrays.equals(tiles[i], yy.tiles[i])) {
                return false;
            }
        }
        return true;
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // string representation of this board
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(n);
        sb.append("\n");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sb.append(tiles[i][j]);
                sb.append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[][] tilesCopy = arrayCopy(tiles);
        if (tiles[0][0] * tiles[0][1] != 0)
            arraySwitch(tilesCopy, 0, 0, 0, 1);
        else
            arraySwitch(tilesCopy, 1, 0, 1, 1);
        return new Board(tilesCopy);
    }

}
