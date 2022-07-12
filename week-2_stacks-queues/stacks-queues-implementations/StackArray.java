public class StackArray<Item> {
    private Item[] stack;
    // N represents both the size of the stack,
    // and the next available index of the array,
    // i.e. the index where the next element would be inserted.
    private int N;


    public StackArray() {
        stack = (Item[]) new Object[1];
    }

    public void push(Item item) {
        if (N == stack.length) {
            Item[] copy = (Item[]) new Object[stack.length * 2];
            for (int i = 0; i < stack.length; i++) {
                copy[i] = stack[i];
            }
            stack = copy;
        }
        stack[N++] = item;
    }

    public Item pop() {
        // To prevent loitering
        Item item = stack[--N];
        stack[N] = null;
        if (N > 0 && N == stack.length / 4) {
            // Try figure out why N cannot be equal to 0
            // Is it because when stack.length == 0, the following line will try to create an array of size 0 and throw an error?
            resize(stack.length / 2);
        }
        return item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < stack.length; i++) {
            copy[i] = stack[i];
        }
        stack = copy;
    }
}
