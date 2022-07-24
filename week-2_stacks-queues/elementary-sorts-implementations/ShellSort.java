public class ShellSort {
    public static <T extends Comparable<? super T>> void sort(T[] array) {
        int h = 1;
        while (h < array.length / 3) {
            h = h * 3 + 1;
        }
        while (h > 0) {
            System.out.println(h + "-sort:"); // For testing
            for (int i = 0; i < h; i++) {
                InsertionSort.sort(array, i, h);
                printArray(array); // For testing
            }
            h = (h - 1) / 3;
        }
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
        Integer[] array = {14, 7, 6, 9, 5, 8, 4, 12, 10, 3, 11, 13, 2, 1, 14, 7, 6, 9, 5, 8, 4, 12, 10, 3, 11, 13, 2, 1};
        ShellSort.sort(array);
    }
}
