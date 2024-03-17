import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean[][] site;
	private final WeightedQuickUnionUF uf;
	private final WeightedQuickUnionUF uff;
	private final int size;
	private int numberOfOpenSites = 0;
	private int nSquare;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		validate(n);
		this.size = n;
		this.nSquare = n * n;
		this.uf = new WeightedQuickUnionUF(nSquare + 2);
		this.uff = new WeightedQuickUnionUF(nSquare + 1);
		this.site = new boolean[n][n];
		for (int i = 0; i < n; i++) {
			uf.union(i, nSquare);
			uf.union(nSquare - 1 - i, nSquare + 1);
			uff.union(i, nSquare);
		}
	}

	private int idx(int row, int col) {
		return (row - 1) * size + col - 1;
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		validate(row, col);
		int index = idx(row, col);
		if (!site[row - 1][col - 1]) {
			site[row - 1][col - 1] = true;
			numberOfOpenSites++;
			if (row < size && isOpen(row + 1, col)) {
				uf.union(index, index + size);
				uff.union(index, index + size);
			}
			if (row > 1 && isOpen(row - 1, col)) {
				uf.union(index, index - size);
				uff.union(index, index - size);
			}
			if (col > 1 && isOpen(row, col - 1)) {
				uf.union(index, index - 1);
				uff.union(index, index - 1);
			}
			if (col < size && isOpen(row, col + 1)) {
				uf.union(index, index + 1);
				uff.union(index, index + 1);
			}

		}
	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		validate(row, col);
		return site[row - 1][col - 1];

	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {
		validate(row, col);
		int index = idx(row, col);
		return isOpen(row, col) && (uff.find(index) == uff.find(nSquare));
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return numberOfOpenSites;
	}

	// does the system percolate?

	public boolean percolates() {
		if (size == 1) {
			return isOpen(1, 1);
		}
		return uf.find(nSquare + 1) == uf.find(nSquare);
	}

	private void validate(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
	}

	private void validate(int row, int col) {
		if (row > size || row <= 0 || col > size || col <= 0 || row == Integer.MAX_VALUE || col == Integer.MAX_VALUE
				|| row == Integer.MIN_VALUE || col == Integer.MIN_VALUE) {
			throw new IllegalArgumentException();
		}
	}

	// test client (optional)
	public static void main(String[] args) {

	}
}