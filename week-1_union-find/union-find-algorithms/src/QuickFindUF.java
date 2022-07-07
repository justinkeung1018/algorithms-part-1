public class QuickFindUF extends UF {

    public QuickFindUF(int N) {
        super(N);
    }

    public void union(int p, int q) {
        int pID = id[p];
        int qID = id[q];
        for (int i = 0; i < id.length; i++) {
            if (id[i] == qID) {
                id[i] = id[p];
            }
        }
        // Bugged version
//        for (int i = 0; i < id.length; i++) {
//            if (id[i] == id[q]) {
//                id[i] = pID;
//            }
//        }
        System.out.println("union(" + p + ", " + q + ")");
        printID();
    }

    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }

    private void printID() {
        for (int i = 0; i < id.length; i++) {
            System.out.println(i + ": " + id[i]);
        }
    }

    public static void main(String[] args) {
        QuickFindUF quickFindUF = new QuickFindUF(10);
        quickFindUF.union(1, 2);
        quickFindUF.union(3, 4);
        quickFindUF.union(5, 4);
        quickFindUF.union(3, 7);
        quickFindUF.union(8, 2);
        quickFindUF.union(3, 6);
    }
}
