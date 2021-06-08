import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // public class Deque<Item> {

    private Deque.Node<Item> first;    // beginning of queue
    private Deque.Node<Item> last;     // end of queue
    private int n;               // number of elements on queue

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        n = 0;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        deque.isEmpty();
        deque.isEmpty();
        deque.addFirst(3);
        deque.removeLast();
        
    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException("The item = " + null + " is not acceptable!");

        Deque.Node<Item> oldFirst = first;
        first = new Deque.Node<Item>(item);
        if (isEmpty()) {
            last = first;
        }
        else {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        n++;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException("empty deque doesn't support removeFirst()");
        Item lastItem = last.item;
        last = last.prev;
        if (last == null) {
            first = null;
        }
        else {
            last.next = null;
        }
        n--;
        return lastItem;

    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException("empty deque doesn't support removeFirst()");
        Item firstItem = first.item;
        first = first.next;
        if (first == null) {
            last = null;
        }
        else {
            first.prev = null;
        }
        n--;
        return firstItem;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("The item = " + null + " is not acceptable!");
        Deque.Node<Item> oldLast = last;
        last = new Deque.Node<Item>(item);
        if (isEmpty()) {
            first = last;
        }
        else {
            last.prev = oldLast;
            oldLast.next = last;
        }
        n++;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new LinkedIterator(first);
    }

    // public String toString() {
    //     if (isEmpty()) return "[Empty Deque]";
    //
    //     Node<Item> current = first;
    //     String str = "[Deque of size = " + n + "]";
    //     while (current != null) {
    //         str += " -> " + current.item;
    //         current = current.next;
    //     }
    //     return str;
    // }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // helper linked list class
    private static class Node<Item> {
        private Item item;
        private Node<Item> next = null;
        private Node<Item> prev = null;

        private Node() {
        }

        private Node(Item item) {
            this.item = item;
        }


    }


    private class LinkedIterator implements Iterator<Item> {
        Node<Item> current;

        public LinkedIterator(Deque.Node<Item> first) {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("next() is not valid when hasNext() == false;");
            Item firstItem = current.item;
            current = current.next;
            return firstItem;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

    }


}
