import java.util.Queue;
import java.util.Arrays;
import java.util.LinkedList;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;


// this class is not implemented yet as it should 
public class FastCollinearPoints {
    private int segm_count;
    private LineSegment[] line;
       public FastCollinearPoints(Point[] points) { // finds all line segments containing 4 points

           if (points == null) {
               throw new IllegalArgumentException("Illegal argument ");
           }

           int arr_len = points.length;
           for (int i = 0; i < arr_len; i++) {
               if (points[i] == null) {
                   throw new IllegalArgumentException("Illegal array argument");
               }
           }

           Point dup[] = points.clone();
           Arrays.sort(dup);

           for (int i = 0; i < arr_len - 1; i++) {
               if (dup[i].slopeTo(dup[i + 1]) == Double.NEGATIVE_INFINITY) {
                   throw new IllegalArgumentException("array contains a repeated point");
               }
           }

           segm_count = 0;
           Queue<LineSegment> line_q = new LinkedList<LineSegment>();
           for (int i = 0; i < arr_len ; i++) {
               Arrays.sort(dup, points[i].slopeOrder());
               int orig_head = 1;
               int tail = orig_head + 1;
               while (tail < arr_len) {
                   double headSlope = points[i].slopeTo(dup[orig_head]);
                   while(tail < arr_len && headSlope == points[i].slopeTo(dup[tail])) {
                        tail++;             
                   }

                   if (tail - orig_head >= 3) {
                       boolean greathFl = true;
                       Point max = new Point(-32768, -32768);
                       for (int j = orig_head; j < tail; j++ ) {
                           if (points[i].compareTo(dup[j]) > 0) {
                               greathFl = false;
                               break;
                           }
                           if (dup[j].compareTo(max) > 0) {
                               max = dup[j];
                           }
                       }
                       if (greathFl) {
                           line_q.add(new LineSegment(points[i], max));
                           segm_count++;
                       }
                   }
                   orig_head = tail;
                   tail++;
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
            FastCollinearPoints collinear = new FastCollinearPoints(points);
            for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                segment.draw();
            }
            StdDraw.show();
        
    }
}
