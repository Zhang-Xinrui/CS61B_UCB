package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private int [][] sites; //0:blocked; 1: open
    private int numberOfOpenSites;
    private final int n;
    private WeightedQuickUnionUF sets;
    private WeightedQuickUnionUF newset;
    //注意，为了效率考虑添加到两个虚拟节点需要单独考虑

    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException("N <= 0");
        }
        sites = new int[N][N];
        n = N;
        for (int i = 0; i < N; i++) {
            sites[i] = new int[N];
            for (int j = 0; j < N; j++) {
                sites[i][j] = 0;
            }
        }
        numberOfOpenSites = 0;
        sets = new WeightedQuickUnionUF(n * n + 2);
        newset = new WeightedQuickUnionUF(n * n + 1);
    }                // create N-by-N grid, with all sites initially blocked

    private int xyTo1D(int r, int c) {
        return r * n + c + 1;
    }

    public void validate(int row, int col) {
        if (validat(row, col)) {
            return;
        }
        throw new IndexOutOfBoundsException("out of bounds");
    }
    public boolean validat(int row, int col) {
        if (row >= 0 && row < n && col >= 0 && col < n) {
            return true;
        }
        return false;
    }
    public void set(int row1, int col1, int row2, int col2) {
        if (validat(row1, col1) && sites[row1][col1] == 1) {
            sets.union(xyTo1D(row1, col1), xyTo1D(row2, col2));
            newset.union(xyTo1D(row1, col1), xyTo1D(row2, col2));
        }
    }
    public void open(int row, int col) {
        validate(row, col);
        if (sites[row][col] == 0) { //not open already
            numberOfOpenSites++;
            sites[row][col] = 1;
            set(row - 1, col, row, col);
            set(row + 1, col, row, col);
            set(row, col - 1, row, col);
            set(row, col + 1, row, col);
            if (row == 0) {
                sets.union(0, xyTo1D(row, col));
                newset.union(0, xyTo1D(row, col));
            } else if (row == n - 1) {
                sets.union(n * n + 1, xyTo1D(row, col));
            }
        }
    }      // open the site (row, col) if it is not open already
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return sites[row][col] == 1;
    }  // is the site (row, col) open?
    public boolean isFull(int row, int col) {
        validate(row, col);
        return newset.connected(0, xyTo1D(row, col));
    }  // is the site (row, col) full?

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }           // number of open sites

    public boolean percolates() {
        return sets.connected(0, n * n + 1);
    }              // does the system percolate?
    public static void main(String[] args) {

    }   // use for unit testing (not required, but keep this here for the autograder)

}

