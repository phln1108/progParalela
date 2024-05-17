import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BubbleSort extends Sort{

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

        sort(v);
    }

    // utilizado para paralelo
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

    static void getData(int[] dataAmount, int[] threadAmount, int turns) {
        // resgatar os dados de cada rodada
        ArrayList<String> resultsSerial = new ArrayList<>();
        ArrayList<String> resultsParallel = new ArrayList<>();

        // setando os titulas de cada coluna no csv
        resultsSerial.add("Size,Time");
        resultsParallel.add("Size,Threads,Time");

        // iterando as quantidades de dados
        for (int i : dataAmount) {
            // lógica de marcação de tempo e ordenação do array
            int duration_mean = 0;
            for (int turn = 0; turn < turns; turn++) {
                // copiando o array criado
                int[] vs = randomV(i);

                long start = System.currentTimeMillis();
                sort(vs);
                long end = System.currentTimeMillis();
                long time = (end - start);
                duration_mean += time;
            }
            duration_mean /= turns;

            // add a informação no csv
            resultsSerial.add(i + "," + duration_mean);

            // iterando a quantidade de threads
            for (int j : threadAmount) {
                int suration_mena_p = 0;
                for (int turn = 0; turn < turns; turn++) {
                    int[] vp = randomV(i);
                    long start = System.currentTimeMillis();
                    parallelSort(vp, j);
                    long end = System.currentTimeMillis();
                    long time = (end - start);
                    suration_mena_p += time;
                }

                suration_mena_p /= turns;
                resultsParallel.add(i + "," + j + "," + suration_mena_p);
            }
        }

        make_csv(resultsSerial, "BubbleSort", false);
        make_csv(resultsParallel, "BubbleSort", true);
    }
    
}
