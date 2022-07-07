public class Percolation {
    private boolean[][] grid;
    private int[] id;
    private int[] treeSize;
    private int numberOfOpenSites;
    private int virtualTopSite;
    private int virtualBottomSite;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be at least 1");
        }

        grid = new boolean[n][n];
        for (boolean[] row : grid) {
            for (int i = 0; i < n; i++) {
                row[i] = false;
            }
        }

        int nSquared = n * n;

        id = new int[nSquared + 2];
        for (int i = 0; i < nSquared + 2; i++) {
            id[i] = i;
        }

        treeSize = new int[nSquared + 2];
        for (int i = 0; i < nSquared + 2; i++) {
            treeSize[i] = 1;
        }

        virtualTopSite = nSquared;
        virtualBottomSite = nSquared + 1;
        // Connect all top sites to virtual site at top
        for (int topSite = 0; topSite < n; topSite++) {
            union(topSite, virtualTopSite);
        }
        // Connect all bottom sites to virtual site at bottom
        for (int bottomSite = nSquared - n; bottomSite < nSquared; bottomSite++) {
            union(bottomSite, virtualBottomSite);
        }

        numberOfOpenSites = 0;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > grid.length) {
            throw new IllegalArgumentException("Row is out of range");
        }
        if (col < 1 || col > grid.length) {
            throw new IllegalArgumentException("Column is out of range");
        }

        int currentSite = getSite(row, col);
        if (!isOpen(row, col)) {
            int rowIndex = row - 1;
            int colIndex = col - 1;
            grid[rowIndex][colIndex] = true;
            numberOfOpenSites++;
            if (siteExists(row, col - 1) && isOpen(row, col - 1)) {
                int leftSite = getSite(row, col - 1);
                union(currentSite, leftSite);
            }
            if (siteExists(row, col + 1) && isOpen(row, col + 1)) {
                int rightSite = getSite(row, col + 1);
                union(currentSite, rightSite);
            }
            if (siteExists(row - 1, col) && isOpen(row - 1, col)) {
                int aboveSite = getSite(row - 1, col);
                union(currentSite, aboveSite);
            }
            if (siteExists(row + 1, col) && isOpen(row + 1, col)) {
                int belowSite = getSite(row + 1, col);
                union(currentSite, belowSite);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > grid.length) {
            throw new IllegalArgumentException("Row is out of range");
        }
        if (col < 1 || col > grid.length) {
            throw new IllegalArgumentException("Column is out of range");
        }
        int rowIndex = row - 1;
        int colIndex = col - 1;
        boolean currentSite = grid[rowIndex][colIndex];
        return currentSite;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > grid.length) {
            throw new IllegalArgumentException("Row is out of range");
        }
        if (col < 1 || col > grid.length) {
            throw new IllegalArgumentException("Column is out of range");
        }
        int rowIndex = row - 1;
        int colIndex = col - 1;
        int site = getSite(row, col);
        return isOpen(row, col) && connected(site, virtualTopSite);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return connected(virtualBottomSite, virtualTopSite);
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        percolation.open(2, 1);
        percolation.open(2, 2);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(3, 1);
        System.out.println("Percolates: " + percolation.percolates());
    }

    // helper methods
    // weighted QU
    private void union(int site1, int site2) {
        int root1 = root(site1);
        int root2 = root(site2);
        if (root1 != root2) {
            int size1 = treeSize[root1];
            int size2 = treeSize[root2];
            if (size1 > size2) {
                id[root2] = root1;
                treeSize[root1] += treeSize[root2];
            }
            else {
                id[root1] = root2;
                treeSize[root2] += treeSize[root1];
            }
        }
    }

    private boolean connected(int site1, int site2) {
        int root1 = root(site1);
        int root2 = root(site2);
        return root1 == root2;
    }

    private int root(int site) {
        while (site != id[site]) {
            site = id[site];
        }
        return site;
    }

    private int getSite(int row, int col) {
        int rowIndex = row - 1;
        int colIndex = col - 1;
        return grid.length * rowIndex + colIndex;
    }

    private boolean siteExists(int row, int col) {
        int rowIndex = row - 1;
        int colIndex = col - 1;
        boolean validRow = rowIndex >= 0 && rowIndex < grid.length;
        boolean validCol = colIndex >= 0 && colIndex < grid.length;
        return validRow && validCol;
    }

    // private void printID() {
    //     for (int i = 0; i < id.length; i++) {
    //         if (i == virtualTopSite) {
    //             System.out.println("Virtual top site: " + id[virtualTopSite]);
    //         }
    //         else if (i == virtualBottomSite) {
    //             System.out.println("Virtual bottom site: " + id[virtualBottomSite]);
    //         }
    //         else {
    //             System.out.println("Site " + i + ": " + id[i]);
    //         }
    //     }
    // }
}
