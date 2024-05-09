import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    /*
     * Algorítmos feitos:
     * - BubbleSort
     * - CountingSort
     * - MergeSort
     * - QuickSort
     */
    public static void main(String[] args) {
        getData(new BubbleSort());
        getData(new CountingSort());
        getData(new MergeSort());
        getData(new QuickSort());
    }

    @SuppressWarnings("static-access")
    static void getData(Sort sort) {
        ArrayList<String> resultsSerial = new ArrayList<>();
        ArrayList<String> resultsParallel = new ArrayList<>();
        resultsSerial.add("Tamanho,Tempo");
        resultsParallel.add("Tamanho,Threads,Tempo");

        for (int i = 10; i < 1000000000; i *= 10) {
            int[] v = randomV(i);
            long startSerial = System.nanoTime();
            sort.sort(Arrays.copyOf(v, i));
            long endSerial = System.nanoTime();
            long time = endSerial - startSerial;
            resultsSerial.add(i+","+time);

            for (int j = 1; j <= 100; j++) {
                if (i < j)
                    continue;
                long startParallel = System.nanoTime();
                sort.parallelSort(Arrays.copyOf(v, i), j);
                long endParallel = System.nanoTime();
                resultsParallel.add(i+","+j+","+(endParallel - startParallel));
            }
        }

        make_csv(resultsSerial, sort, false);
        make_csv(resultsParallel, sort, true);
    }

    static int[] randomV(int size){
        int[] array = new int[size];
        for (int i = 0; i < size; i++){
            array[i] = (int) (Math.random() * 1000);
        }
        return array;
    }

    static void make_csv(ArrayList<String> results, Sort sort, boolean parallel) {
        try (FileWriter writer = new FileWriter(
                sort.getClass().getName() + "_" + (parallel ? "Parallel" : "Serial") + ".csv")) {
            for (String result : results) {
                writer.append(result);
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print the hole Array
     */
    static void printV(int[] a) {
        System.out.print("{ ");
        for (int i : a) {
            System.out.print(i + ", ");
        }
        System.out.println("}");
    }
}
