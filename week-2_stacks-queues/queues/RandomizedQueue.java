/* *****************************************************************************
 *  Name: Justin Keung
 *  Date: 16 July 2022
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] queue;
    private int size;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item added cannot be null");
        }
        if (size == queue.length) {
            resize(queue.length * 2);
        }
        queue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int index = StdRandom.uniform(size);
        Item item = queue[index];
        size--;
        // Replace the removed element with the last element in the array
        if (index < size) {
            queue[index] = queue[size];
            queue[size] = null;
        }
        else {
            queue[index] = null;
        }
        if (size > 0 && size == queue.length / 4) {
            resize(queue.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int index = StdRandom.uniform(size);
        Item item = queue[index];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {

        private int[] permutation = StdRandom.permutation(size);
        private int current = 0;

        public boolean hasNext() {
            return current < permutation.length;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Queue is empty, no items to return");
            }
            int index = permutation[current++];
            return queue[index];
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<>();
        System.out.println("Queue is empty: " + queue.isEmpty()); // true
        queue.enqueue("A");
        queue.printQueue();
        queue.enqueue("B");
        queue.printQueue();
        queue.enqueue("C");
        queue.printQueue();
        queue.enqueue("D");
        queue.printQueue();
        System.out.println("Queue is empty: " + queue.isEmpty()); // fa;se
        System.out.println("Size: " + queue.size()); // 4
        for (int i = 0; i < 10; i++) {
            System.out.println(queue.sample());
        }
        Iterator<String> iterator = queue.iterator();
        for (int i = 0; i < queue.size(); i++) {
            System.out.println("Iterator: " + iterator.next());
        }
        queue.dequeue();
        queue.dequeue();
        System.out.println("Size: " + queue.size()); // 2
        queue.printQueue();
        for (int i = 0; i < 4; i++) {
            System.out.println(queue.sample());
        }
        queue.enqueue("E");
        queue.printQueue();
    }

    // resizing the queue array
    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    // printing the queue array
    private void printQueue() {
        for (Item item : queue) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}
