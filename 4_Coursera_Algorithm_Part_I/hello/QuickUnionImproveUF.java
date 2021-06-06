public class QuickUnionImproveUF {

    int n = 0;
    int[] arr = new int[n];
    int[] sz = new int[n];


    QuickUnionImproveUF(Integer n) {
        System.out.println("Constructed UnionFind(" + n + ")");
        this.n = n;
        this.arr = new int[n];
        this.sz = new int[n];
        for (Integer i = 0; i < n; i++) {
            arr[i] = i;
            sz[i] = 1;
        }
    }

    public boolean allconnected() {
        boolean allconnected = true;
        for (int i = 0; i < this.n - 1; i++) {
            if (this.arr[i] != this.arr[i + 1])
                allconnected = false;
        }
        return allconnected;
    }

    public void union(Integer p, Integer q) {
        int p_root = this.root(p);
        int q_root = this.root(q);
        if (p_root == q_root) return;
        if (this.sz[p_root] >= this.sz[q_root]) {
            this.arr[q_root] = p_root;
            this.sz[p_root] += this.sz[q_root];
        }
        else {
            this.arr[p_root] = q_root;
            this.sz[q_root] += this.sz[p_root];
        }
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
