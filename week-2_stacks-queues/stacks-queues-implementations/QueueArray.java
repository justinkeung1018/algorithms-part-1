public class QueueArray<Item> {
    private Item[] queue;
    private int size;
    private int first; // Index of first element in queue
    private int last; // Index of last element in queue

    public QueueArray() {
        queue = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return first == 0 && last == 0;
    }

    public void enqueue(Item item) {
        if (size == queue.length) {
            resize(2 * queue.length);
        }
        last = (last + 1) % queue.length;
        queue[last] = item;
        size++;
    }

    public Item dequeue() {
        Item item = queue[first];
        queue[first] = null;
        first = (first + 1) % queue.length;
        if (size > 0 && size == queue.length / 4) {
            resize(queue.length / 2);
        }
        size--;
        return item;
    }

    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < queue.length; i++) {
            copy[i] = queue[(first + i) % queue.length]; // Unwraps the queue
        }
        queue = copy;
        // Resets the first and last pointers since the queue does not wrap around now
        first = 0;
        last = size - 1;
    }

    private void printQueue() {
        for (Item item : queue) {
            System.out.print(item + " ");
        }
    }

    public static void main(String[] args) {
        QueueArray<String> queue = new QueueArray<>();
        queue.enqueue("A");
        queue.enqueue("B");
        queue.enqueue("C");
        queue.enqueue("D");
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.enqueue("E");
        queue.enqueue("F");
        queue.enqueue("G");
        queue.enqueue("H");
        queue.dequeue();
    }
}
