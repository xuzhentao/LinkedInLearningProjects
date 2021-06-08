import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size;
    private int arraySize;
    private Item[] resizeableArray;


    // construct an empty randomized queue
    public RandomizedQueue() {
        size = 0;
        arraySize = 1;
        resizeableArray = (Item[]) new Object[arraySize];
    }

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<Integer> queue = new RandomizedQueue<>();
        queue.enqueue(489);
        queue.size();
        queue.dequeue();
        queue.isEmpty();
        queue.enqueue(127);


    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("enqueue item can't be null");
        }
        if (whetherDoubleSize()) {
            doubleArraySize();
        }
        resizeableArray[size++] = item;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // remove and return a random item
    public Item dequeue() {

        if (isEmpty())
            throw new NoSuchElementException("dequeue() on empty deque is illegal.");

        int randomIdx = StdRandom.uniform(0, size);
        Item temp = resizeableArray[randomIdx];
        resizeableArray[randomIdx] = resizeableArray[size - 1];
        resizeableArray[size - 1] = null;

        size--;
        if (whetherShrinkSize()) {
            shrinkArraySize();
        }

        return temp;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    private boolean whetherDoubleSize() {
        return isArrayFull();
    }

    private void doubleArraySize() {
        arraySize *= 2;
        Item[] oldResizeableArray = resizeableArray;
        resizeableArray = (Item[]) new Object[arraySize];
        for (int idx = 0; idx < size; idx++) {
            resizeableArray[idx] = oldResizeableArray[idx];
        }
    }

    private boolean whetherShrinkSize() {
        return arraySize / 2 >= size && arraySize >= 2;
    }

    private void shrinkArraySize() {
        arraySize /= 2;
        if (arraySize < size) {
            throw new IllegalArgumentException(
                    "The shrinked size " + arraySize + " is smaller than the number of elements "
                            + size);
        }
        Item[] oldResizeableArray = resizeableArray;
        resizeableArray = (Item[]) new Object[arraySize];
        for (int idx = 0; idx < size; idx++) {
            resizeableArray[idx] = oldResizeableArray[idx];
        }
    }

    // resizableArray utilities
    private boolean isArrayFull() {
        return size == arraySize;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new LinkedIterator();
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new NoSuchElementException("sample() on empty deque is illegal.");
        int randomIdx = StdRandom.uniform(0, size);
        return resizeableArray[randomIdx];
    }

    private class LinkedIterator implements Iterator<Item> {

        final int n;
        final private Item[] arr;
        int currIdx = 0;


        public LinkedIterator() {
            arr = (Item[]) new Object[size];


            for (int idx = 0; idx < size; idx++) {
                arr[idx] = resizeableArray[idx];
            }
            for (int idx = 0; idx < size; idx++) {
                int randomIdx = StdRandom.uniform(idx, size);
                Item tmp = arr[idx];
                arr[idx] = arr[randomIdx];
                arr[randomIdx] = tmp;
            }
            currIdx = 0;
            n = size;
        }

        public boolean hasNext() {
            return currIdx < n;
        }

        public Item next() {
            if (!hasNext())
                throw new NoSuchElementException("next() on empty iterator is illegal");
            return arr[currIdx++];
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
        }
    }

}
