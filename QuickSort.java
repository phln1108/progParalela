import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class QuickSort extends Sort {
    QuickSort() {}

    public static void parallelSort(int[] v, int numThreads) {
        ForkJoinPool pool = new ForkJoinPool(numThreads);
        pool.invoke(new QuickSortTask(v, 0, v.length - 1));
        pool.shutdown();
    }

    private static class QuickSortTask extends RecursiveAction {
        private int[] array;
        private int low;
        private int high;

        QuickSortTask(int[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            if (low < high) {
                int pivotIndex = partition(array, low, high);
                QuickSortTask leftTask = new QuickSortTask(array, low, pivotIndex - 1);
                QuickSortTask rightTask = new QuickSortTask(array, pivotIndex + 1, high);
                invokeAll(leftTask, rightTask);
            }
        }
    }

    public static void sort(int[] v) {
        sort(v, 0, v.length - 1);
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

        make_csv(resultsSerial, "QuickSort", false);
        make_csv(resultsParallel, "QuickSort", true);
    }
}
