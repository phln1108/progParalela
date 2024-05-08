import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BubbleSort{
    public static void parallelSort(int[] v, int numThreads) {
        int n = v.length;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        int chunkSize = (int) Math.ceil((double) n / numThreads);

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min((i + 1) * chunkSize, n);
            executor.submit(() -> sort(v, start, end));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sort(v, 0, n);
    }

    /**
     * Private Function for parallel use only
     * @param v array to sort
     * @param start start index (inclusive)
     * @param end end index (exclusive)
     */
    private static void sort(int[] v, int start, int end) {
        for (int i = start; i < end - 1; i++) {
            for (int j = i + 1; j < end; j++) {
                if (v[i] > v[j]) {
                    int temp = v[j];
                    v[j] = v[i];
                    v[i] = temp;
                }
            }
        }
    }

    public static void sort(int[] v) {
        int start = 0, end = v.length;
        for (int i = start; i < end - 1; i++) {
            for (int j = i + 1; j < end; j++) {
                if (v[i] > v[j]) {
                    int temp = v[j];
                    v[j] = v[i];
                    v[i] = temp;
                }
            }
        }
    }
}
