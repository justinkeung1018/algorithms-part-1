import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF id;
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
        id = new WeightedQuickUnionUF(nSquared + 2);

        virtualTopSite = nSquared;
        virtualBottomSite = nSquared + 1;
        // Connect all top sites to virtual site at top
        for (int topSite = 0; topSite < n; topSite++) {
            id.union(topSite, virtualTopSite);
        }
        // Connect all bottom sites to virtual site at bottom
        for (int bottomSite = nSquared - n; bottomSite < nSquared; bottomSite++) {
            id.union(bottomSite, virtualBottomSite);
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
                id.union(currentSite, leftSite);
            }
            if (siteExists(row, col + 1) && isOpen(row, col + 1)) {
                int rightSite = getSite(row, col + 1);
                id.union(currentSite, rightSite);
            }
            if (siteExists(row - 1, col) && isOpen(row - 1, col)) {
                int aboveSite = getSite(row - 1, col);
                id.union(currentSite, aboveSite);
            }
            if (siteExists(row + 1, col) && isOpen(row + 1, col)) {
                int belowSite = getSite(row + 1, col);
                id.union(currentSite, belowSite);
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
        return isOpen(row, col) && id.find(site) == id.find(virtualTopSite);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return id.find(virtualBottomSite) == id.find(virtualBottomSite);
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
}
