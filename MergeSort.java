
public class MergeSort {

    public static void parallelSort(int[] v) {
        if (v.length < 2) 
            return;

        int mid = v.length / 2;
        int[] l = new int[mid];
        int[] r = new int[v.length - mid];

        for (int i = 0, j = mid; j < v.length; i++, j++) {
            if (i < mid)
                l[i] = v[i];
            // if (j < v.length)
            r[i] = v[j];
        }

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                parallelSort(l);
            }
        });

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                parallelSort(r);
            }
        });

        t1.start();
        t2.start();

        try{
            t1.join();
            t2.join();
        } catch (InterruptedException e) {

        }

        merge(v, l, r);
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
}
