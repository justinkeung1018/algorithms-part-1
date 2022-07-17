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
    private Deque<Integer> available;

    // construct an empty randomized queue
    public RandomizedQueue() {
        queue = (Item[]) new Object[1];
        available = new Deque<>();
        available.addLast(0);
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
        int nextAvailable = available.removeFirst();
        queue[nextAvailable] = item;
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
        int index = StdRandom.uniform(queue.length);
        Item item = queue[index];
        while (item == null) {
            index = StdRandom.uniform(queue.length);
            item = queue[index];
        }
        available.addLast(index);
        queue[index] = null;
        size--;
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
        int index = StdRandom.uniform(queue.length);
        Item item = queue[index];
        while (item == null) {
            index = StdRandom.uniform(queue.length);
            item = queue[index];
        }
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
        Item[] queueCopy = (Item[]) new Object[capacity];
        int queueCopyIndex = 0;
        for (int i = 0; i < queue.length; i++) {
            Item item = queue[i];
            if (item != null) {
                queueCopy[queueCopyIndex++] = item;
            }
        }
        queue = queueCopy;
        available = new Deque<>();
        for (int i = size; i < capacity; i++) {
            available.addLast(i);
        }
    }

    // printing the queue array
    private void printQueue() {
        for (Item item : queue) {
            System.out.print(item + " ");
        }
        System.out.println();
    }
}
