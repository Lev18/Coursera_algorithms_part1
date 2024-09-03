import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
  public static void  main(String [] args) {
    int index = 0;
    String word = "";
    
      while (!StdIn.isEmpty()) { 
       String per_word = StdIn.readString();
        boolean accept = StdRandom.bernoulli(1 / (index + 1.0));

         if (accept) {
           word = per_word;
         }
         index++;
    }
    StdOut.println(word);
  }
}
