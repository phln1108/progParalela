public class CountingSort {

    public static void parallelSort(int[] v, int maxValue) {

        final int numThreads = v.length/10;
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
        System.out.println(end - start);
        System.out.println(sortedBlock.length);
        
        for (int i = start; i < end; i++) {
            v[start + i] = sortedBlock[i];
        }


        // System.arraycopy(sortedBlock, 0, v, start, end - start);
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

        System.arraycopy(sortedV, 0, v, start, end - start);
    }

}
