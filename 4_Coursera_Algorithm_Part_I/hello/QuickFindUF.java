public class QuickFindUF {

    Integer n = 0;
    Integer[] arr = new Integer[n];

    QuickFindUF(Integer n) {
        System.out.println("Constructed UnionFind(" + n + ")");
        this.n = n;
        this.arr = new Integer[n];
        for (Integer i = 0; i < n; i++) {
            arr[i] = i;
        }
    }

    public void union(Integer p, Integer q) {
        int p_id = arr[p];
        int q_id = arr[q];
        for (int i = 0; i < n; i++) {
            if (arr[i] == p_id) arr[i] = q_id;
        }
    }

    public boolean connected(int p, int q) {
        return this.arr[p] == this.arr[q];
    }


}
