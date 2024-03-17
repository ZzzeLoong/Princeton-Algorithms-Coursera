import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double mean;
	private double stddev;
	private double confidenceLo;
	private double confidenceHi;
	private final static double confidence_95 = 1.96;

	private void validate(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}
	}

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		validate(n, trials);
		double[] fraction = new double[trials];
		for (int i = 0; i < trials; i++) {
			Percolation p = new Percolation(n);
			while (!p.percolates()) {
				int row = StdRandom.uniformInt(1, n + 1);
				int col = StdRandom.uniformInt(1, n + 1);
				p.open(row, col);
			}
			fraction[i] = (double) p.numberOfOpenSites() / (n * n);
		}
		mean = StdStats.mean(fraction);
		stddev = StdStats.stddev(fraction);
		confidenceLo = mean - confidence_95 *stddev/ Math.sqrt(trials);
		confidenceHi = mean + confidence_95 *stddev/ Math.sqrt(trials);
	}

	// sample mean of percolation threshold
	public double mean() {
		return this.mean;
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return this.stddev;
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return this.confidenceLo;
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return this.confidenceHi;
	}

	// test client (see below)
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int trials = Integer.parseInt(args[1]);
		PercolationStats ps = new PercolationStats(n, trials);
		
		StdOut.println("mean                    = "+ps.mean);
		StdOut.println("stddev                  = "+ps.stddev);
		StdOut.println("95% confidence interval = "+"["+ps.confidenceLo+", "+ps.confidenceHi+"]");
	}
}