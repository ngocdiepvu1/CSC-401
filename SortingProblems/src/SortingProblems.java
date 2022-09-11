import java.io.*;
import java.util.Random;

public class SortingProblems {
    static int counter = 0;
    static int inputSize[] = {10000, 20000, 40000, 80000, 100000, 200000, 400000, 800000};

    static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j <= high - 1; j++) {
            if (arr[j] < pivot) {
                counter++;
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partition(arr, low, high);

            quickSort(arr, low, pivot - 1);
            quickSort(arr, pivot + 1, high);
        }
    }

    static void merge(int arr[], int l, int m, int r) {
        int n1 = m - l + 1;
        int n2 = r - m;

        int L[] = new int[n1];
        int R[] = new int[n2];

        for (int i = 0; i < n1; ++i)
            L[i] = arr[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = arr[m + 1 + j];

        int i = 0, j = 0;
        int k = l;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                arr[k] = L[i];
                i++;
            } else {
                arr[k] = R[j];
                j++;
            }
            counter++;
            k++;
        }

        while (i < n1) {
            arr[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    static void mergeSort(int arr[], int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;

            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);

            merge(arr, l, m, r);
        }
    }

    static void heapify(int arr[], int n, int i) {
        int largest = i;
        int l = 2 * i + 1;
        int r = 2 *i + 2;

        if (l < n && arr[l] > arr[largest]) {
            largest = l;
            counter++;
        }

        if (r < n && arr[r] > arr[largest]) {
            largest = r;
            counter++;
        }

        if (largest != i)
        {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;

            heapify(arr, n, largest);
        }
    }

    static void heapSort(int arr[]) {
        int n = arr.length;

        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);

        for (int i = n-1; i >= 0; i--) {
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;

            heapify(arr, i, 0);
        }
    }

    static int[] generateRandom(int size, int seed) {
        Random random = new Random(seed);
        int array[] = new int[size];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(5001);
        }
        return array;
    }

    static int partitionDecreasing(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j <= high - 1; j++) {
            if (arr[j] > pivot) {
                counter++;
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return (i + 1);
    }

    static void quickSortDecreasing(int[] arr, int low, int high) {
        if (low < high) {
            int pivot = partitionDecreasing(arr, low, high);

            quickSortDecreasing(arr, low, pivot - 1);
            quickSortDecreasing(arr, pivot + 1, high);
        }
    }

    static void comparisonSorted(String order) {
        for (int size: inputSize) {
            int[] array = generateRandom(size, 1);
            int n = array.length;

            if (order.equals("Non-Decreasing"))
                quickSort(array, 0, n - 1);
            else if (order.equals("Non-Increasing"))
                quickSortDecreasing(array, 0, n - 1);
            else
                System.err.println("Invalid order");

            System.out.print("Input size " + size + ": ");

            counter = 0;
            quickSort(array, 0, n - 1);
            System.out.print(counter + " ");

            counter = 0;
            mergeSort(array, 0, n - 1);
            System.out.print(counter + " ");

            counter = 0;
            heapSort(array);
            System.out.println(counter);
        }
    }
    static void averageTimeUnsorted(String sortType) {
        System.out.println(sortType);
        for (int size : inputSize) {
            counter = 0;

            for (int i = 0; i < 12; i++) {
                int[] array = generateRandom(size, i);
                int n = array.length;

                if (sortType.equals("Quick Sort"))
                    quickSort(array, 0, n - 1);
                else if (sortType.equals("Merge Sort"))
                    mergeSort(array, 0, n - 1);
                else if (sortType.equals("Heap Sort"))
                    heapSort(array);
                else
                    System.err.println("Invalid sorting type");
            }

            int average = counter / 12;
            System.out.print(average + " ");
        }
        System.out.println();
    }

    public static void main(String args[]) {
        System.out.println("Running time with inputs sorted in the non-decreasing order");
        comparisonSorted("Non-Decreasing");
        System.out.println();

        System.out.println("Running time with inputs sorted in the non-increasing order");
        comparisonSorted("Non-Increasing");
        System.out.println();

        averageTimeUnsorted("Quick Sort");
        averageTimeUnsorted("Merge Sort");
        averageTimeUnsorted("Heap Sort");
    }
}

