import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

  private Node<Item> first;
  private Node<Item> last;
  private int size;

    // construct an empty deque
  public Deque() {
    first = null;
    last = null;
    size = 0;
  }

  private class Node<Item> {
    private Item item;
    private Node<Item> next;
    private Node<Item> prev;
  }

    // is the deque empty?
  public boolean isEmpty() {
    return size == 0;
  }

    // return the number of items on the deque
  public int size() {
    return size;
  }

    // add the item to the front
  public void addFirst(Item item) {
    if (item == null) throw new IllegalArgumentException ("Null item not allowed");

    Node<Item> prev_node = first;
    first = new Node<>();
    first.item = item;
    first.prev = null;
    first.next = prev_node;

    if (isEmpty()) {
      last = first;
    }
    else {
      prev_node.prev = first;
    }
    size++;
  }

    // add the item to the back
  public void addLast(Item item) {
    if (item == null) throw new IllegalArgumentException ("Null item not allowed");

    Node<Item> prev_node = last;
    last = new Node<>();
    last.item = item;
    last.prev = prev_node;
    last.next = null;

    if (isEmpty()) {
      first = last;
    }
    else {
      prev_node.next = last;
    }
    size++;

  }

    // remove and return the item from the front
  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException("Deque is empty");
    }

    Item item = first.item;

    first = first.next;

    if (first != null) {
      first.prev = null;
    }
    else {
      last = null;
    }

    size--;
    return item;
  }

    // remove and return the item from the back
  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException("Deque is empty");
    }

    Item item = last.item;

    last = last.prev;

    if (last != null) {
      last.next = null;
    }
    else {
      first = null;
    }

    size--;
    return item;
  }

    // return an iterator over items in order from front to back
  public Iterator<Item> iterator() {
    return new DequeIter();
  }

  private class DequeIter implements Iterator<Item> {

    private Node<Item> currNode = first;
    @Override
    public boolean hasNext() {
      return currNode != null;
    }
    @Override
    public Item next() {
      if (!hasNext()) throw new NoSuchElementException();
      Item iter = currNode.item;
      currNode = currNode.next;

      return iter;
    }
    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }
  }
    // unit testing (required)
  public static void main(String[] args) {
    Deque<Integer> deque = new Deque<>();
    deque.addFirst(12);
    deque.addFirst(11);
    deque.addLast(14);
    deque.addLast(16);

    StdOut.printf("Deque size! %d\n", deque.size());
    for (int item : deque) {
      StdOut.printf("Items` %d\n", item);
    }

    StdOut.printf("Removed first: %d\n", deque.removeFirst());
    StdOut.printf("Removed last: %d\n", deque.removeLast());

    StdOut.printf("Deque size now! %d\n", deque.size());

  }
}

