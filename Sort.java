import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public abstract class Sort {
    static String name = "sort";

    public static void sort(int[] v) {}

    public static void parallelSort(int[] v, int numThreads) {}

    static void make_csv(ArrayList<String> results, String sort, boolean parallel) {
        String dir = isWindows() ? "csvs\\\\" : "csvs/";

        try (FileWriter writer = new FileWriter(
                dir + sort + "_" + (parallel ? "Parallel" : "Serial") + ".csv")) {
            for (String result : results) {
                writer.append(result);
                writer.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String OS = null;

    public static String getOsName() {
        if (OS == null) {
            OS = System.getProperty("os.name");
        }
        return OS;
    }

    public static boolean isWindows() {
        return getOsName().startsWith("Windows");
    }

    static int[] randomV(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (Math.random() * 1000);
        }
        return array;
    }
}
