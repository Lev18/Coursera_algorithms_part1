import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class PercolationStats {
  private int Trails;
  private static final double CONFIDENCE_96 = 1.96;
  private double[] fractions;
    // perform independent trials on an n-by-n grid
  public PercolationStats(int n, int trials) {
    if (n <= 0 || trials <= 0) {
      throw new IllegalArgumentException("Illegal argument");
    }
       Trails = trials;
       fractions = new double[Trails];

        for (int i = 0; i < Trails; i++) {
            Percolation perc = new Percolation(n);

            int is_perc = 0;
            while(!perc.percolates()) {
                int row = StdRandom.uniformInt(1, n + 1);
                int col = StdRandom.uniformInt(1, n + 1);
                if(!perc.isOpen(row, col)) {
                  perc.open(row, col);
                    is_perc++;
                }
            }
            fractions[i] = (double) is_perc / Math.pow(n, 2);
       }
  }

    // sample mean of percolation threshold
  public double mean() {
    return StdStats.mean(fractions);
  }

    // sample standard deviation of percolation threshold
  public double stddev() {
    return StdStats.stddev(fractions);
  }

    // low endpoint of 95% confidence interval
  public double confidenceLo() {
    return mean() - ((CONFIDENCE_96 * stddev()) / Math.sqrt(Trails));
  }

    // high endpoint of 95% confidence interval
  public double confidenceHi() {
    return mean() + (CONFIDENCE_96 * stddev()) / Math.sqrt(Trails);
  }

   // test client (see below)
  public static void main(String[] args) {

    int n = Integer.parseInt(args[0]);
    int trials = Integer.parseInt(args[1]);
    PercolationStats percSt = new PercolationStats(n, trials);

    String confidInter = "[" + percSt.confidenceLo() + "," + percSt.confidenceHi() + "]";
    StdOut.println("mean                    = " + percSt.mean());
    StdOut.println("stddev                  = " + percSt.stddev());
    StdOut.println("95% confidence interval = " + confidInter);
  }
}
