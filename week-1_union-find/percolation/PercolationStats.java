import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] results;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be at least 1");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("There should be at least 1 trial");
        }
        results = new double[trials];
        int totalNumberOfSites = n * n;
        for (int trial = 0; trial < trials; trial++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int row = StdRandom.uniform(1, n + 1);
                int col = StdRandom.uniform(1, n + 1);
                if (!percolation.isOpen(row, col)) {
                    percolation.open(row, col);
                }
            }
            int numberOfOpenSites = percolation.numberOfOpenSites();
            double fractionOfOpenSites = 1.0 * numberOfOpenSites / totalNumberOfSites;
            results[trial] = fractionOfOpenSites;
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(results);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(results);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double stddev = stddev();
        int trials = results.length;
        return mean - 1.96 * stddev / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double stddev = stddev();
        int trials = results.length;
        return mean + 1.96 * stddev / Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats percolationStats = new PercolationStats(n, trials);
        System.out.println("mean\t = " + percolationStats.mean());
        System.out.println("stddev\t = " + percolationStats.stddev());
        System.out.println(
                "95% confidence interval\t = [" + percolationStats.confidenceLo() + ", "
                        + percolationStats.confidenceHi() + "]");
    }
}
