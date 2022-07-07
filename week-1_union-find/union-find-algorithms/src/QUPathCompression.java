public class QUPathCompression extends UF {

    public QUPathCompression(int N) {
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

        // Simpler version
        while (root != id[root]) {
            id[root] = id[id[root]]; // Need this line to make the tree shallower
            root = id[root]; // Moves on to the new parent node, which is the original grandparent node
        }

        // Complete path compression version
        // Finds root
        while (root != id[root]) {
            root = id[root];
        }
        // Reassigns root as parent node for every node along the path
        while (p != id[p]) {
            p = id[p];
            id[p] = root;
        }

        return root;
    }
}
