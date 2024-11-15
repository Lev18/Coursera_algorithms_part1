import java.util.Queue;
import java.util.Arrays;
import java.util.LinkedList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


public class BruteCollinearPoints {
    private int segm_count;
    private LineSegment[] line;
       public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points

           if (points == null) {
               throw new IllegalArgumentException("Illegal argument ");
           }

           int Arr_len = points.length;
           for (int i = 0; i < Arr_len; i++) {
               if (points[i] == null) {
                   throw new IllegalArgumentException("Illegal array argument");
               }
           }

           Point dup[] = points.clone();
           Arrays.sort(dup);

           for (int i = 0; i < Arr_len - 1; i++) {
               if (dup[i].slopeTo(dup[i + 1]) == Double.NEGATIVE_INFINITY) {
                   throw new IllegalArgumentException("array contains a repeated point");
               }
           }

           segm_count = 0;
           Queue<LineSegment> line_q = new LinkedList<LineSegment>();
           for (int i = 0; i < (Arr_len - 3); i++) {
               for (int j = i + 1; j < (Arr_len - 2); j++) {
                   for (int m = j + 1; m < (Arr_len - 1); m++) {
                       if (dup[i].slopeTo(dup[j]) != dup[i].slopeTo(dup[m])) {
                           continue;
                       }
                       for (int n = m + 1; n < Arr_len; n++) {
                           if (dup[i].slopeTo(dup[j]) == dup[i].slopeTo(dup[n])) {
                               segm_count++;
                               LineSegment tmp = new LineSegment(dup[i], dup[n]); 
                               line_q.add(tmp);
                           }
                       }
                   }
               }
           }
           line = new LineSegment[segm_count];
           for (int i = 0; i < segm_count; i++) {
               line[i] = line_q.remove();
           }
       }          
       public int numberOfSegments() { 
           // the number of line segments
           return segm_count;
       }              
       public LineSegment[] segments() {
            return line.clone();
       }

       public static void main(String[] args) {

               // read the n points from a file
            In in = new In(args[0]);
            int n = in.readInt();
            Point[] points = new Point[n];
            
            for (int i = 0; i < n; i++) {
                int x = in.readInt();
                int y = in.readInt();
                points[i] = new Point(x, y);
            }
            // draw the points
            StdDraw.enableDoubleBuffering();
            StdDraw.setXscale(0, 32768);
            StdDraw.setYscale(0, 32768);
            for (Point p : points) {
                p.draw();
            }
            
            StdDraw.show();
            
            // print and draw the line segments
            BruteCollinearPoints collinear = new BruteCollinearPoints(points);
            for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                segment.draw();
            }
            StdDraw.show();
        
    }
}
