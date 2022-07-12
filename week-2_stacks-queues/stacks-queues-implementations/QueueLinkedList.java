public class QueueLinkedList<Item> {
    private Node first;
    private Node last;

    private class Node {
        Item item;
        Node next;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public void enqueue(Item item) {
        Node oldLast = last;
        last = new Node();
        last.item = item;
        if (isEmpty()) {
            first = last;
        } else {
            // If the queue is empty, oldLast is null, so oldLast.next is undefined
            oldLast.next = last;
        }
    }

    public Item dequeue() {
        Item item = first.item;
        first = first.next;
        return item;
    }
}
