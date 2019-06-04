package hw2;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

public class PercolationStats {
    private double mean, stddev, confidenceLow, confidenceHigh;
    int [] numbers;
    double[] fractions;
    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        PercolationStats a = new PercolationStats(100, 200, pf);
        System.out.print(a.mean);
    }
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (T <= 0 || N <= 0) {
            throw new java.lang.IllegalArgumentException("T or N <= 0");
        }
        numbers = new int[T];
        fractions = new double[T];
        for (int i = 0; i < T; i++) {
            Percolation newpercolation = pf.make(N);
            numbers[i] = 0;
            while (!newpercolation.percolates()) {
                int num = StdRandom.uniform(1, N * N);
                if (newpercolation.isOpen(num / N, num % N)) {
                    continue;
                }
                newpercolation.open(num / N, num % N);
                numbers[i]++;
            }
            fractions[i] = (double) numbers[i] / (N * N);
        }
        mean = StdStats.mean(fractions);
        stddev = StdStats.stddev(fractions);
        confidenceLow = mean - 1.96 * stddev / Math.sqrt(T);
        confidenceHigh = mean + 1.96 * stddev / Math.sqrt(T);

    }   // perform T independent experiments on an N-by-N grid

    public double mean() {
        return mean;
    }                                           // sample mean of percolation threshold
    public double stddev() {
        return stddev;
    }                                         // sample standard deviation of percolation threshold{
    public double confidenceLow() {
        return confidenceLow;
    }                                  // low endpoint of 95% confidence interval
    public double confidenceHigh() {
        return confidenceHigh;
    }                               // high endpoint of 95% confidence interval
}
