public class InsertionSort {
    public static <T extends Comparable<? super T>> void sort(T[] array) {
        for (int i = 0; i < array.length; i++) {
            int j = i;
            while (j > 0 && array[j].compareTo(array[j - 1]) < 0) {
                swap(array, j, j-1);
                j--;
            }
            // For loop implementation
            /* for (int j = i; j > 0; j--) {
                if (array[j].compareTo(array[j - 1]) < 0) {
                    swap(array, j, j-1);
                } else {
                    break;
                }
            } */
            printArray(array); // For testing
        }
    }

    private static void swap(Object[] array, int index1, int index2) {
        Object item1 = array[index1];
        array[index1] = array[index2];
        array[index2] = item1;
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
        Integer[] array = {3, 1, 4, 5, 2, 8, 2, 7};
        InsertionSort.sort(array);
    }
}
