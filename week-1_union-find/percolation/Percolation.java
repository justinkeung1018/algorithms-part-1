import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] grid;
    private WeightedQuickUnionUF id;
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
        id = new WeightedQuickUnionUF(nSquared);

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
        if (!isOpen(row, col)) {
            int rowIndex = row - 1;
            int colIndex = col - 1;
            grid[rowIndex][colIndex] = grid[rowIndex][colIndex] | 4;
            numberOfOpenSites++;
            // Edge case when n = 1
            if (grid.length == 1) {
                percolates = true;
            }
            int currentSite = getSite(row, col);
            if (siteExists(row, col - 1) && isOpen(row, col - 1)) {
                int currentRoot = id.find(currentSite);
                int leftSite = getSite(row, col - 1);
                int leftRoot = id.find(leftSite);
                id.union(currentRoot, leftRoot);
                updateStatus(currentRoot, leftRoot);
                int newRoot = id.find(currentSite);
                updateStatus(newRoot, currentRoot);
            }
            if (siteExists(row, col + 1) && isOpen(row, col + 1)) {
                int currentRoot = id.find(currentSite);
                int rightSite = getSite(row, col + 1);
                int rightRoot = id.find(rightSite);
                id.union(currentRoot, rightRoot);
                updateStatus(currentRoot, rightRoot);
                int newRoot = id.find(currentSite);
                updateStatus(newRoot, currentRoot);
            }
            if (siteExists(row - 1, col) && isOpen(row - 1, col)) {
                int currentRoot = id.find(currentSite);
                int aboveSite = getSite(row - 1, col);
                int aboveRoot = id.find(aboveSite);
                id.union(currentRoot, aboveRoot);
                updateStatus(currentRoot, aboveRoot);
                int newRoot = id.find(currentSite);
                updateStatus(newRoot, currentRoot);
            }
            if (siteExists(row + 1, col) && isOpen(row + 1, col)) {
                int currentRoot = id.find(currentSite);
                int belowSite = getSite(row + 1, col);
                int belowRoot = id.find(belowSite);
                id.union(currentRoot, belowRoot);
                updateStatus(currentRoot, belowRoot);
                int newRoot = id.find(currentSite);
                updateStatus(newRoot, currentRoot);
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
        int site = getSite(row, col);
        int siteStatus = getStatus(site);
        return (siteStatus & 4) == 4;
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
        int root = id.find(site);
        int rootStatus = getStatus(root);
        return isOpen(row, col) && ((rootStatus & 2) == 2);
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
        Percolation heart25 = new Percolation(25);
        heart25.open(1, 7);
        heart25.printGrid();
        System.out.println();
        heart25.open(1, 8);
        heart25.printGrid();
        System.out.println();
        heart25.open(1, 9);
        heart25.printGrid();
        System.out.println();
        heart25.open(1, 17);
        heart25.printGrid();
        System.out.println();
        heart25.open(1, 18);
        heart25.printGrid();
        System.out.println();
        heart25.open(1, 19);
        heart25.printGrid();
        System.out.println();
        heart25.open(2, 5);
        heart25.printGrid();
        System.out.println();
        heart25.open(2, 6);
        heart25.printGrid();
        System.out.println();
        heart25.open(2, 7);
        System.out.println();
        heart25.printGrid();
        System.out.println(heart25.isFull(1, 7)); // true

        // Edge case: n = 1
        System.out.println("Edge case: n = 1");
        Percolation percolation1 = new Percolation(1);
        // percolation1.printGrid();
        System.out.println("Percolates: " + percolation1.percolates());
        percolation1.open(1, 1);
        // percolation1.printGrid();
        System.out.println("Percolates: " + percolation1.percolates());
        System.out.println(percolation1.isFull(1, 1));
    }

    // helper methods
    private void updateStatus(int root1, int root2) {
        int combinedStatus = combinedStatus(root1, root2);
        if (combinedStatus == 7) {
            percolates = true;
        }
        setStatus(root1, combinedStatus);
        setStatus(root2, combinedStatus);
    }

    private int combinedStatus(int site1, int site2) {
        int site1Status = getStatus(site1);
        int site2Status = getStatus(site2);
        return site1Status | site2Status;
    }

    private int getStatus(int site) {
        int rowIndex = getRowIndex(site);
        int colIndex = getColIndex(site);
        return grid[rowIndex][colIndex];
    }

    private void setStatus(int site, int status) {
        int rowIndex = getRowIndex(site);
        int colIndex = getColIndex(site);
        grid[rowIndex][colIndex] = status;
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

    private int getRowIndex(int site) {
        int n = grid.length;
        return site / n;
    }

    private int getColIndex(int site) {
        int n = grid.length;
        return site % n;
    }

    // Prints grid for debugging
    private void printGrid() {
        for (int[] row : grid) {
            for (int siteStatus : row) {
                System.out.print(siteStatus + " ");
            }
            System.out.print("\n");
        }
    }
}
