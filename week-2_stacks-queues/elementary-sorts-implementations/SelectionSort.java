public class SelectionSort {
    public static <T extends Comparable<? super T>> void sort(T[] array) {
        for (int i = 0; i < array.length; i++) {
            int minIndex = i;
            for (int j = i; j < array.length; j++) {
                if (array[j].compareTo(array[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            swap(array, i, minIndex);
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
        Integer[] array = {3, 5, 4, 1, 2, 8, 9, 7};
        SelectionSort.sort(array);
    }
}
