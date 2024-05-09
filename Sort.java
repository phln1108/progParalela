public abstract class Sort {
    Sort(){}

    public static void sort(int[] v) {}

    public static void parallelSort(int[] v, int numThreads) {}


    public static void getTime(Sort self) {
        System.out.println(Sort.class.getName());
    }
}
