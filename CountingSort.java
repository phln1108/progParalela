import java.util.ArrayList;
import java.util.Arrays;

public class CountingSort extends Sort {
    CountingSort() {}

    public static void parallelSort(int[] v, int numThreads) {
        int maxValue = Arrays.stream(v).max().getAsInt();
        final int blockSize = v.length / numThreads;

        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < numThreads; i++) {
            final int threadId = i;
            threads[i] = new Thread(() -> {
                int start = threadId * blockSize;
                int end = (threadId == numThreads - 1) ? v.length : start + blockSize;
                sort(v, start, end, maxValue);
            });
            threads[i].start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        sort(v, maxValue);
    }

    private static void sort(int[] v, int start, int end, int maxValue) {
        int[] counts = new int[maxValue + 1];

        for (int i = start; i < end; i++) 
            counts[v[i]]++;

        for (int i = 1; i <= maxValue; i++) 
            counts[i] += counts[i - 1];

        int[] sortedBlock = new int[end - start];
        for (int i = end - 1; i >= start; i--) {
            sortedBlock[counts[v[i]] - 1] = v[i];
            counts[v[i]]--;
        }

        System.arraycopy(sortedBlock, 0, v, start, sortedBlock.length);
    }

    public static void sort(int[] v, int maxValue) {
        int start = 0, end = v.length;
        int[] counts = new int[maxValue + 1];

        for (int i = start; i < end; i++) 
            counts[v[i]]++;

        for (int i = 1; i <= maxValue; i++) 
            counts[i] += counts[i - 1];

        int[] sortedV = new int[end - start];
        for (int i = end - 1; i >= start; i--) {
            sortedV[counts[v[i]] - 1] = v[i];
            counts[v[i]]--;
        }

        System.arraycopy(sortedV, 0, v, start, sortedV.length);
    }

    static void getData(int[] dataAmount, int[] threadAmount, int turns) {
        ArrayList<String> resultsSerial = new ArrayList<>();
        ArrayList<String> resultsParallel = new ArrayList<>();

        resultsSerial.add("Size,Time");
        resultsParallel.add("Size,Threads,Time");

        for (int i : dataAmount) {
            int duration_mean = 0;
            for (int turn = 0; turn < turns; turn++) {
                int[] vs = randomV(i);

                long start = System.currentTimeMillis();
                sort(vs, Arrays.stream(vs).max().getAsInt());
                long end = System.currentTimeMillis();
                long time = (end - start);
                duration_mean += time;
            }
            duration_mean /= turns;
            resultsSerial.add(i + "," + duration_mean);

            for (int j : threadAmount) {
                int duration_mean_p = 0;
                for (int turn = 0; turn < turns; turn++) {
                    int[] vp = randomV(i);

                    long start = System.currentTimeMillis();
                    parallelSort(vp, j);
                    long end = System.currentTimeMillis();
                    long time = (end - start);
                    duration_mean_p += time;
                }
                duration_mean_p /= turns;
                resultsParallel.add(i + "," + j + "," + duration_mean_p);
            }
        }

        make_csv(resultsSerial, "CountingSort", false);
        make_csv(resultsParallel, "CountingSort", true);
    }

    public static void sort(int[] v) {
        int maxValue = Arrays.stream(v).max().getAsInt();
        sort(v, maxValue);
    }
}
