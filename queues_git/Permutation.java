import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;


public class Permutation {
  public static void main(String[] args) {
    int num = Integer.parseInt(args[0]);

    RandomizedQueue<String> queue = new RandomizedQueue<>();

    while(!StdIn.isEmpty()) {
      String str = StdIn.readString();
      queue.enqueue(str);
    }

    while (num > 0) {
        StdOut.println(queue.dequeue());
        num--;
    }
  }
}

