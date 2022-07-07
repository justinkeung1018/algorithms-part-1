public class Solution {
    public static void main(String[] args) {
        Solution solution = new Solution(10);
        int[][] logFile = {
                {0, 1},
                {2, 3},
                {3, 4},
                {4, 5},
                {5, 6},
                {6, 7},
                {7, 8},
                {8, 9},
                {3, 5},
                {1, 2},
                {3, 5}
        };
        System.out.println(
                "Earliest time is " +
                solution.findEarliestTime(logFile));
    }

    private int[] id;
    private int[] treeSize;
    private int earliestTime;

    public Solution(int N) {
        id = new int[N];
        treeSize = new int[N];
        earliestTime = 0;
        for (int friend = 0; friend < N; friend++) {
            id[friend] = friend;
            treeSize[friend] = 1;
        }
    }

    public boolean formFriends(int friend1, int friend2) {
        earliestTime++;
        int root1 = root(friend1);
        int root2 = root(friend2);
        int size1 = size(root1);
        int size2 = size(root2);
        if (root1 != root2) {
            if (size1 > size2) {
                id[root2] = root1;
                treeSize[root1] += treeSize[root2];
            } else {
                id[root1] = root2;
                treeSize[root2] += treeSize[root1];
            }
        }
        return size1 + size2 == id.length;
    }

    public int findEarliestTime(int[][] logFile) {
        for (int[] friends : logFile) {
            int friend1 = friends[0];
            int friend2 = friends[1];
            boolean allConnected = formFriends(friend1, friend2);
            if (allConnected) {
                return earliestTime;
            }
        }
        return 0;
    }

    private int root(int friend) {
        while (friend != id[friend]) {
            friend = id[friend];
        }
        return friend;
    }

    private int size(int friend) {
        return treeSize[friend];
    }
}
