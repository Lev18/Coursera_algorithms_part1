import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
  private int virtualTop;
  private int virtualBottom;

  private int openSite;
  private int grid_size;
  private boolean[][] grid;
  private WeightedQuickUnionUF ufGrid;
  private WeightedQuickUnionUF ufFull;

  // creates n-by-n grid, with all sites initially blocked
  public Percolation(int n) {
   if (n <= 0 ) throw new IllegalArgumentException();
    grid_size = n;
    openSite = 0;
    grid = new boolean[grid_size][grid_size];
    virtualTop = n * n;
    virtualBottom = n * n - 1;
    ufFull = new WeightedQuickUnionUF(n * n + 2);
    ufGrid = new WeightedQuickUnionUF(n * n + 1);
  } 
  
    // opens the site (row, col) if it is not open already
  public void open(int row, int col) {
    validateCoord(row, col);
   int oneDimInd = toOneDimIndex(row, col);
    // if it  opened already then return
   if (isOpen(row, col)) {
     return;
   }

   //set grid to true;
   grid[row - 1][col - 1] = true;
   openSite++;

   if (row == 1 ) {
     ufGrid.union(virtualTop, oneDimInd);
     ufFull.union(virtualTop, oneDimInd);
   }

   if (row == grid_size) {
     ufGrid.union(virtualBottom, oneDimInd);
   }

   if (isOnGrid(row - 1, col) && isOpen(row - 1, col)) {
     ufGrid.union(oneDimInd, toOneDimIndex(row - 1, col));
     ufFull.union(oneDimInd, toOneDimIndex(row - 1, col));
   }

    if (isOnGrid(row + 1, col) && isOpen(row + 1, col)) {
     ufGrid.union(oneDimInd, toOneDimIndex(row + 1, col));
     ufFull.union(oneDimInd, toOneDimIndex(row + 1, col));
    }

    if (isOnGrid(row, col - 1) && isOpen(row, col - 1)) {
     ufGrid.union(oneDimInd, toOneDimIndex(row, col - 1));
     ufFull.union(oneDimInd, toOneDimIndex(row, col - 1));
    }

    if (isOnGrid(row, col + 1) && isOpen(row, col + 1)) {
     ufGrid.union(oneDimInd, toOneDimIndex(row, col + 1));
     ufFull.union(oneDimInd, toOneDimIndex(row, col + 1));
    }
  }

    // is the site (row, col) open?
  public boolean isOpen(int row, int col) {
    validateCoord(row, col);
    return grid[row - 1][col - 1];
  }

    // is the site (row, col) full?
  public boolean isFull(int row, int col) {
    validateCoord(row, col);
    if (!isOpen(row, col)) {
      return false;
    }

    return ufFull.find(virtualTop) == ufFull.find(toOneDimIndex(row, col));
  }

    // returns the number of open sites
  public int numberOfOpenSites() {
    return openSite;
  }

    // does the system percolate?
  public boolean percolates() {
    return ufGrid.find(virtualTop) == ufGrid.find(virtualBottom);
  }
    // test client (optional)
   public static void main(String[] args) {
    int grid_size = Integer.parseInt(args[0]);

    Percolation perc = new Percolation(grid_size);

    perc.open(1, 2);
    perc.percolates();
    StdOut.print(grid_size);

  }

  private int toOneDimIndex(int row, int col ) {
    validateCoord(row, col);
    return (row - 1) * grid_size + (col - 1);
  }

  private void validateCoord(int row, int col) {
    if (!isOnGrid(row, col)) {
     throw new IndexOutOfBoundsException("Index out of bounds, that");
    }
  }

  private boolean isOnGrid(int row, int col) {
    int shiftCol = col - 1;
    int shiftRow = row - 1;
    return (shiftRow >= 0 && shiftCol >= 0 && shiftRow < grid_size && shiftCol < grid_size);
  }
}
