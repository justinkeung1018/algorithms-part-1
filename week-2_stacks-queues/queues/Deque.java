import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int size;

    private class Node {
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {

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
        if (item == null) {
            throw new IllegalArgumentException("Item added cannot be null");
        }
        Node oldFirst = first;
        first = new Node();
        first.item = item;
        if (isEmpty()) {
            last = first;
        }
        else {
            first.next = oldFirst;
            oldFirst.prev = first;
        }
        size++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item added cannot be null");
        }
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) {
            first = last;
        }
        else {
            oldLast.next = last;
            last.prev = oldLast;
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
        size--;
        if (isEmpty()) {
            last = null;
        }
        else {
            first.prev = null;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
        Item item = last.item;
        last = last.prev;
        size--;
        if (isEmpty()) {
            first = null;
        }
        else {
            last.next = null;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {

        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Deque is empty, no items to return");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<>();
        System.out.println(deque.isEmpty()); // true
        deque.addFirst("Hello");
        deque.addFirst("Bye");
        System.out.println(deque.removeFirst()); // "Bye"
        deque.addLast("Guten tag");
        deque.addLast("Tschuss");
        System.out.println(deque.removeLast()); // "Tschuss"
        System.out.println(deque.isEmpty()); // false
        System.out.println(deque.size()); // 2?
    }

}
