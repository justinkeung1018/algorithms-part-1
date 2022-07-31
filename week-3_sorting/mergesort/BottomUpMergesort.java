public class BottomUpMergesort {
    public static <T extends Comparable<? super T>> void sort(T[] array) {
        T[] aux = array.clone();
        int subarrayLength;
        for (subarrayLength = 2; subarrayLength <= array.length; subarrayLength *= 2) {
            for (int i = 0; i < array.length; i += subarrayLength) {
                int lo = i;
                int mid = i + subarrayLength / 2;
                int hi = Math.min(i + subarrayLength, array.length);
                merge(array, aux, lo, mid, hi);
            }
        }
        // Merging one last time if the length of the array is not a power of 2
        if (subarrayLength < 2 * array.length) {
            int lo = 0;
            int mid = subarrayLength / 2;
            int hi = array.length;
            merge(array, aux, lo, mid, hi);
        }
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
        Integer[] array = {5, 3, 4, 1, 2, 8, 9, 16, 12, 13, 17, 10, 11, 15, 7, 6, 14};
        BottomUpMergesort.sort(array);
    }
}
