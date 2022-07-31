public class Mergesort {
    public static <T extends Comparable<? super T>> void sort(T[] array) {
        T[] aux = array.clone();
        int hi = array.length;
        int mid = hi / 2;
        sort(array, aux, 0, mid);
        sort(array, aux, mid, hi);
        merge(array, aux, 0, mid, hi);
    }

    // Sorts the subarray from lo (inclusive) to hi (exclusive)
    private static <T extends Comparable<? super T>> void sort(T[] array, T[] aux, int lo, int hi) {
        if (hi <= lo + 1) {
            return;
        }
        int mid = (lo + hi) / 2;
        sort(array, aux, lo, mid);
        sort(array, aux, mid, hi);
        merge(array, aux, lo, mid, hi);
    }

    // Merges the two subarrays:
    // One from lo (inclusive) to mid (exclusive)
    // Another from mid (inclusive) to hi (exclusive)
    private static <T extends Comparable<? super T>> void merge(T[] array, T[] aux, int lo, int mid, int hi) {
        for (int i = 0; i < array.length; i++) {
            aux[i] = array[i];
        }
        
        int pointer1 = lo;
        int pointer2 = mid;

        for (int i = lo; i < hi; i++) {
            if (pointer1 >= mid) {
                array[i] = aux[pointer2++];
            } else if (pointer2 >= hi) {
                array[i] = aux[pointer1++];
            } else if (aux[pointer1].compareTo(aux[pointer2]) < 0) {
                array[i] = aux[pointer1++];
            } else {
                array[i] = aux[pointer2++];
            }
        }

        printArray(array); // For testing
    }

    // For testing
    private static void printArray(Object[] array) {
        for (Object object : array) {
            System.out.print(object + " ");
        }
        System.out.println();
    }

    // For testing
    public static void main(String[] args) {
        Integer[] array = {5, 3, 4, 1, 2, 8, 9, 1000, 10, 8};
        Mergesort.sort(array);
    }
}
