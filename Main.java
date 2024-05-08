import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    /*
     * Algorítmos feitos:
     *  - BubbleSort
     *  - CountingSort
     *  - MergeSort
     *  - QuickSort
     */
    public static void main(String[] args) {
        int size = 20;
        List<Integer> expected = new ArrayList<Integer>();

        for (int i = 1; i <= size; i++) {
            expected.add(i);
        }
        Collections.shuffle(expected);

        int[] serial = new int[size];
        int[] parallel = new int[size];

        for (int i = 0; i < size; i++) {
            serial[i] = expected.get(i);
            parallel[i] = expected.get(i);
        }

        BubbleSort.sort(serial);
        CountingSort.parallelSort(parallel,2);

        printV(serial);
        printV(parallel);
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
