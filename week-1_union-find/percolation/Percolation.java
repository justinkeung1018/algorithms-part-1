public class Percolation {
    private int[][] grid;
    private int[] id;
    private int[] treeSize;
    private int numberOfOpenSites;
    private boolean percolates;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be at least 1");
        }

        // Store data about each site using three bits:
        // First (most significant) bit represents whether site is open (1) or closed (0)
        // Second bit represents whether site is connected to a site in the top row (1) or not (0)
        // Third (least significant) bit represents whether site is connected to site in the bottom row (1) or not (0)
        // (From https://stackoverflow.com/questions/61396690/how-to-handle-the-backwash-problem-in-percolation-without-creating-an-extra-wuf)

        // Since sites can only be opened but not closed, bitwise OR (|) is used.
        // To open a site:
        // Status of a site | 100 in binary = 4 in decimal

        // To show that site is connected to a site in the top row:
        // Status of a site | 010 in binary = 2 in decimal

        // To show that site is connected to a site in the bottom row:
        // Status of a site | 001 in binary = 1 in decimal

        grid = new int[n][n];
        for (int[] row : grid) {
            for (int i = 0; i < n; i++) {
                row[i] = 0;
            }
        }

        // All sites in the top row have second bit = 1
        int[] topRow = grid[0];
        for (int i = 0; i < n; i++) {
            topRow[i] = topRow[i] | 2;
        }

        // All sites in the bottom row have third bit = 1
        int[] bottomRow = grid[n - 1];
        for (int i = 0; i < n; i++) {
            bottomRow[i] = bottomRow[i] | 1;
        }

        int nSquared = n * n;
        id = new int[nSquared];
        for (int i = 0; i < nSquared; i++) {
            id[i] = i;
        }

        treeSize = new int[nSquared];
        for (int i = 0; i < nSquared; i++) {
            treeSize[i] = 1;
        }

        numberOfOpenSites = 0;

        percolates = false;
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
            grid[rowIndex][colIndex] = grid[rowIndex][colIndex] | 4;
            numberOfOpenSites++;
            // Edge case when n = 1
            if (grid.length == 1) {
                percolates = true;
            }
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
        int siteStatus = grid[rowIndex][colIndex];
        return (siteStatus & 4) >= 4;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > grid.length) {
            throw new IllegalArgumentException("Row is out of range");
        }
        if (col < 1 || col > grid.length) {
            throw new IllegalArgumentException("Column is out of range");
        }
        int site = getSite(row, col);
        int root = root(site);
        int rootRowIndex = getRow(root);
        int rootColIndex = getCol(root);
        int rootStatus = grid[rootRowIndex][rootColIndex];
        return isOpen(row, col) && ((rootStatus & 2) >= 2);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        percolation.open(1, 1);
        // percolation.printGrid();
        // percolation.printID();
        System.out.println("Percolates: " + percolation.percolates());
        System.out.println();
        percolation.open(2, 1);
        // percolation.printGrid();
        // percolation.printID();
        System.out.println("Percolates: " + percolation.percolates());
        System.out.println();
        percolation.open(3, 1);
        // percolation.printGrid();
        // percolation.printID();
        System.out.println("Percolates: " + percolation.percolates());
        System.out.println();
        // Backwashing check
        percolation.open(3, 3);
        // percolation.printGrid();
        System.out.println(percolation.isFull(3, 3)); // false
        System.out.println(percolation.isFull(3, 1)); // true
    }

    // helper methods
    // weighted QU
    private void union(int site1, int site2) {
        int root1 = root(site1);
        int root2 = root(site2);
        if (root1 != root2) {
            int root1RowIndex = getRow(root1);
            int root1ColIndex = getCol(root1);
            int root2RowIndex = getRow(root2);
            int root2ColIndex = getCol(root2);
            int root1Status = grid[root1RowIndex][root1ColIndex];
            int root2Status = grid[root2RowIndex][root2ColIndex];
            int combinedStatus = root1Status | root2Status;
            if (combinedStatus == 7) {
                percolates = true;
            }
            int size1 = treeSize[root1];
            int size2 = treeSize[root2];
            if (size1 > size2) {
                grid[root1RowIndex][root1ColIndex] = combinedStatus;
                id[root2] = root1;
                treeSize[root1] += treeSize[root2];
            }
            else {
                grid[root2RowIndex][root2ColIndex] = combinedStatus;
                id[root1] = root2;
                treeSize[root2] += treeSize[root1];
            }
        }
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

    private int getRow(int site) {
        int n = grid.length;
        return site / n;
    }

    private int getCol(int site) {
        int n = grid.length;
        return site % n;
    }

    // Prints grid for debugging
    // public void printGrid() {
    //     for (int[] row : grid) {
    //         for (int siteStatus : row) {
    //             System.out.print(siteStatus + " ");
    //         }
    //         System.out.print("\n");
    //     }
    // }

    // Prints parent node of each site for debugging
    // public void printID() {
    //     for (int site : id) {
    //         System.out.print(site + " ");
    //     }
    //     System.out.print("\n");
    // }
}
