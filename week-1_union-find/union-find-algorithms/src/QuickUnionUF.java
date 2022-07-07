public class QuickUnionUF extends UF {

    public QuickUnionUF(int N) {
        super(N);
    }

    public void union(int p, int q) {
        int pRoot = root(p);
        int qRoot = root(q);
        id[qRoot] = id[pRoot];
    }

    public boolean connected(int p, int q) {
        return root(p) == root(q);
    }

    private int root(int p) {
        int root = p;
        while (root != id[root]) {
            root = id[root];
        }
        return root;
    }
}
