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
    static int[] dataAmount = { 100_000, 200_000, 300_000, 500_000, 700_000 ,1_000_000};

    // Quantidade de threads utilizadas
    static int[] threadAmount = { 2, 4, 6, 8 };

    // quantas veze ele irá rodar para pegar a media do tempo de ordenação
    static int turns = 1;

    // mude aqui entre python e python3 (dependendo de sua instalação)
    static String python = "python";

    public static void main(String[] args) {
        long start =  System.currentTimeMillis();

        QuickSort.getData(dataAmount, threadAmount, turns);
        MergeSort.getData(dataAmount, threadAmount, turns);
        CountingSort.getData(dataAmount, threadAmount, turns);
        BubbleSort.getData(dataAmount, threadAmount, turns);

        System.out.println("Tempo total de teste dos algoritmos: "+ (System.currentTimeMillis() - start)/1000+ " segundos.");

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
