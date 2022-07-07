public abstract class UF {
    int[] id;

    public UF(int N) {
        id = new int[N];
        for (int i = 0; i < N; i++) {
            id[i] = i;
        }
    }

    abstract void union(int p, int q);
    abstract boolean connected(int p, int q);
}
