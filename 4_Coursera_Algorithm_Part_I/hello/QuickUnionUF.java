public class QuickUnionUF {

    Integer n = 0;
    Integer[] arr = new Integer[n];

    QuickUnionUF(Integer n) {
        System.out.println("Constructed UnionFind(" + n + ")");
        this.n = n;
        this.arr = new Integer[n];
        for (Integer i = 0; i < n; i++) {
            arr[i] = i;
        }
    }

    public void union(Integer p, Integer q) {
        int p_root = this.root(p);
        int q_root = this.root(q);
        this.arr[p_root] = q_root;
    }

    public boolean connected(int p, int q) {
        return this.root(p) == this.root(q);
    }

    private int root(Integer p) {
        while (this.arr[p] != p) {
            p = this.arr[p];
        }
        return p;
    }


}
