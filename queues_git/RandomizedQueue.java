import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] array;
  private int size;

    // construct an empty randomized queue
  public RandomizedQueue() {
    array = (Item[])new Object[2];
    size = 0;
  }

    // is the randomized queue empty?
  public boolean isEmpty(){
    return size == 0; 
  }

    // return the number of items on the randomized queue
  public int size() {
    return array.length;
 }

  private void resize(int capacity) {
     Item[] newChunk = (Item[]) new Object[capacity];

     for (int i = 0; i < size; i++) {
       newChunk[i] = array[i];
     }
     array = newChunk;
  }

    // add the item
  public void enqueue(Item item) {
    if (item == null) throw new  IllegalArgumentException("Cannot add null item.");
    if (size == array.length) resize((2 * array.length));
    array[size++] = item;
  }

    // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) throw new NoSuchElementException("Queue is empty.");

    // generate random index
    int randInd = StdRandom.uniformInt(size);
    // assign it to another variable and set null
    Item it = array[randInd];

    array[randInd] = array[size - 1];
    array[size - 1] = null;
    size--;
    // if size is smaller than quarter length of array then resize it
    if (size > 0 && size <= array.length/4) resize(array.length / 2);
    return it;
  }

    // return a random item (but do not remove it)
  public Item sample() {
     if (isEmpty()) throw new NoSuchElementException("Queue is empty.");
     int randInd = StdRandom.uniformInt(size);    
     return array[randInd];
  }

    // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomizedQueueIter();
  }

  private class RandomizedQueueIter implements Iterator<Item>{
    private int current;
    private Item[] shuffled;

    public RandomizedQueueIter() {
      shuffled = (Item[]) new Object[size];
      for (int i = 0; i < size; i++) {
        shuffled[i] = array[i];
      }
      shuffle(shuffled);
      current = 0;
    }

    private void shuffle(Item[] shuffle) {
      for (int i = shuffle.length - 1; i > 0; i--) {
        int randInd = StdRandom.uniformInt(i + 1);      
        Item tmp = shuffle[i];
        shuffle[i] = shuffle[randInd];
        shuffle[randInd] = tmp;
      }
    } 

    @Override
    public boolean hasNext() {
      return current < shuffled.length;
    }

    @Override
    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      return shuffled[current++];
    }
    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
 
  }

    // unit testing (required)
  public static void main(String[] args) {
    RandomizedQueue <Integer> queue = new RandomizedQueue<>();

    queue.enqueue(17);
    
    queue.enqueue(18);
    queue.enqueue(19);
    queue.enqueue(12);
    queue.enqueue(11);
    queue.enqueue(15);
    
    for (int x : queue) {
      StdOut.printf("Queue elemets` %d\n", x);
    }

    StdOut.printf("Random item removed! %d\n", queue.dequeue());
    
  }

}

