import java.io.IOException;

public class Main {
    /*
     * Algorítmos feitos:
     * - BubbleSort
     * - CountingSort
     * - MergeSort
     * - QuickSort
     */

    // Tamanho de das amostras de cada rodada
    static int[] dataAmount = { 100000, 200000, 300000, 500000 ,1000000};
    // static int[] dataAmount = { 1000, 2000, 3000, 5000 ,10000};

    // Quantidade de threads utilizadas
    static int[] threadAmount = { 2, 4, 6 };

    // quantas veze ele irá rodar para pegar a media do tempo de ordenação
    static int turns = 1;

    // mude aqui entre python e python3 (dependendo de sua instalação)
    static String python = "python3";

    public static void main(String[] args) {
        
        // BubbleSort.getData(dataAmount, threadAmount, turns);
        QuickSort.getData(dataAmount, threadAmount, turns);
        MergeSort.getData(dataAmount, threadAmount, turns);
        CountingSort.getData(dataAmount, threadAmount, turns);

        try {
            Runtime.getRuntime().exec(python + " chart.py");
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
