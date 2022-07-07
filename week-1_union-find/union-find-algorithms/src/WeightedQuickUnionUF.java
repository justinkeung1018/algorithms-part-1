public class WeightedQuickUnionUF extends UF {
    private int[] treeSize;

    public WeightedQuickUnionUF(int N) {
        super(N);
        treeSize = new int[N];
        for (int i : treeSize) {
            i = 1;
        }
    }

    public void union(int p, int q) {
        // If the two nodes are already connected, do nothing
        if (connected(p, q)) {
            return;
        }
        int pRoot = root(p);
        int qRoot = root(q);
        int pSize = treeSize[pRoot];
        int qSize = treeSize[qRoot];
        if (pSize < qSize) {
            id[pRoot] = qRoot;
            treeSize[qRoot] = pSize + qSize;
        } else {
            id[qRoot] = pRoot;
            treeSize[pRoot] = pSize + qSize;
        }
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
