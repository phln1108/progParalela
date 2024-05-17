import java.util.ArrayList;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class MergeSort extends Sort{
    MergeSort(){}

    public static void parallelSort(int[] v, int numThreads) {
        ForkJoinPool pool = new ForkJoinPool(numThreads);
        pool.invoke(new MergeSortTask(v));
        pool.shutdown();
    }

    private static class MergeSortTask extends RecursiveAction {
        private int[] v;

        MergeSortTask(int[] v) {
            this.v = v;
        }

        @Override
        protected void compute(){
            if (v.length < 2) {
                return;
            }

            int mid = v.length / 2;
            int[] l = new int[mid];
            int[] r = new int[v.length - mid];

            invokeAll(new MergeSortTask(l),
                      new MergeSortTask(r));
            merge(v,l,r);
        }
    }

    public static void sort(int[] v) {
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

        sort(l);
        sort(r);

        merge(v, l, r);
    }

    private static void merge(int[] v, int[] l, int[] r) {
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

        make_csv(resultsSerial, "MergeSort", false);
        make_csv(resultsParallel, "MergeSort", true);
    }
}
