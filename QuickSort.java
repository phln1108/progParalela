public class QuickSort extends Sort{
    QuickSort(){}

    public static void parallelSort(int[] v, int numThreads) {
        parallelSort(v, 0, v.length-1);
    }

    private static void parallelSort(int[] v, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(v, low, high);

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    parallelSort(v, low, pivotIndex - 1);
                }
            });

            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    parallelSort(v, pivotIndex + 1, high);
                }
            });

            t1.start();
            t2.start();

            try {
                t1.join();
                t2.join();
            } catch (InterruptedException e) {

            }
        }
    }

    public static void sort(int[] v) {
        sort(v, 0, v.length-1);
    }

    private static void sort(int[] v, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(v, low, high);

            sort(v, low, pivotIndex - 1);
            sort(v, pivotIndex + 1, high);
        }
    }

    private static int partition(int[] v, int low, int high) {
        int pivot = v[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (v[j] <= pivot) {
                i++;

                int temp = v[i];
                v[i] = v[j];
                v[j] = temp;
            }
        }

        int temp = v[i + 1];
        v[i + 1] = v[high];
        v[high] = temp;

        return i + 1;
    }
}
