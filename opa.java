import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class opa {
    static int numThreads = 10;
    static int threadsUsed = 0;

    public static void main(String[] args) {
        int size = 10;
        List<Integer> expected = new ArrayList<Integer>();

        for (int i = 1; i <= size; i++) {
            expected.add(i);
        }
        Collections.shuffle(expected);

        int[] serial = new int[size];
        int[] parallel = new int[size];
        int[] bubble = new int[size];
        int[] bubbleParallel = new int[size];

        for (int i = 0; i < size; i++) {
            serial[i] = expected.get(i);
            parallel[i] = expected.get(i);
            bubble[i] = expected.get(i);
            bubbleParallel[i] = expected.get(i);
        }

        long start = System.currentTimeMillis();
        mergeSort(serial);
        long end = System.currentTimeMillis();
        System.out.println("tempo merge serial: " + (end - start));

        start = System.currentTimeMillis();
        parallelMergeSort(parallel);
        end = System.currentTimeMillis();
        System.out.println("tempo merge paralelo: " + (end - start));

        start = System.currentTimeMillis();
        bubbleSort(bubble,0,bubble.length);
        end = System.currentTimeMillis();
        System.out.println("tempo bubble serial: " + (end - start));

        start = System.currentTimeMillis();
        parallelBubbleSort(bubbleParallel,10);
        end = System.currentTimeMillis();
        System.out.println("tempo bubble paralelo: " + (end - start));


        
        // printV(serial);
        // printV(parallel);
        printV(bubble);
    }

    static void parallelMergeSort(int[] v) {
        if (v.length < 2) {
            return;
        }

        int mid = v.length / 2;
        int[] l = new int[mid];
        int[] r = new int[v.length - mid];

        for (int i = 0, j = mid; j < v.length; i++, j++) {
            if (i < mid)
                l[i] = v[i];
            // if (j < v.length)
            r[i] = v[j];
        }

        ExecutorService executor = Executors.newFixedThreadPool(2);

        executor.submit(() -> parallelMergeSort(l));
        executor.submit(() -> parallelMergeSort(r));

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        merge(v, l, r);
    }

    static void printV(int[] v) {
        System.out.print("{ ");
        for (int i : v) {
            System.out.print(i + ", ");
        }
        System.out.println("}");
    }

    static void mergeSort(int[] v) {
        if (v.length < 2) {
            return;
        }

        int mid = v.length / 2;
        int[] l = new int[mid];
        int[] r = new int[v.length - mid];

        for (int i = 0, j = mid; j < v.length; i++, j++) {
            if (i < mid)
                l[i] = v[i];
            // if (j < v.length)
            r[i] = v[j];
        }

        mergeSort(l);
        mergeSort(r);

        merge(v, l, r);
    }

    static void merge(int[] v, int[] l, int[] r) {
        int i = 0, j = 0, k = 0;

        while (i < l.length && j < r.length) {
            if (l[i] <= r[j]) {
                v[k++] = l[i++];
            } else {
                v[k++] = r[j++];
            }
        }

        while (i < l.length) {
            v[k++] = l[i++];
        }
        while (j < r.length) {
            v[k++] = r[j++];
        }
    }


    public static void parallelBubbleSort(int[] v, int numThreads) {
        int n = v.length;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);

        int chunkSize = (int) Math.ceil((double) n / numThreads);

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = Math.min((i + 1) * chunkSize, n);
            executor.submit(() -> bubbleSort(v, start, end));
        }

        executor.shutdown();
        try {
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bubbleSort(v, 0, n);
    }

    public static void bubbleSort(int[] v , int start, int end) {
        for (int i = start; i < end -1; i++) {
            for (int j = i+1; j < end; j++) {
                if (v[i] > v[j]) {
                    int temp = v[j];
                    v[j] = v[i];
                    v[i] = temp;
                }
            }
        }
    }
}