import java.util.Arrays;

public class Main {
    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {
        arrayWithoutThread();
        arrayWithThread();
    }

    private static void arrayWithoutThread() {
        float[] array = new float[size];

        for (int i = 0; i < array.length; i++) {
            array[i] = 1;
        }
        long a = System.currentTimeMillis();

        for (int i = 0; i < array.length; i++) {
            array[i] = (float) (array[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("Время выполнения для первого массива: " + (System.currentTimeMillis() - a));
    }

    public static void arrayWithThread() {

        float[] array = new float[size];

        for (int i = 0; i < array.length; i++) {
            array[i] = 1;
        }
        long a = System.currentTimeMillis();
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];

        System.arraycopy(array, 0, arr1, 0, h);
        System.arraycopy(array, h, arr2, 0, h);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < arr1.length; i++) {
                arr1[i] = (float) (arr1[i] * Math.sin(0.2f + (i+h) / 5) * Math.cos(0.2f + (i+h) / 5) * Math.cos(0.4f + (i+h) / 2));
            }
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < arr2.length; i++) {
                arr2[i] = (float) (arr2[i] * Math.sin(0.2f + (i+h) / 5) * Math.cos(0.2f + (i+h) / 5) * Math.cos(0.4f + (i+h) / 2));
            }
        });
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(arr1, 0, array, 0, h);
        System.arraycopy(arr2, 0, array, h, h);

        System.out.println("Время выполнения для второго массива: " + (System.currentTimeMillis() - a));

    }
}