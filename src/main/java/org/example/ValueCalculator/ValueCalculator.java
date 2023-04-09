package org.example.ValueCalculator;
import java.util.Arrays;

public class ValueCalculator {
    private float[] arr;
    private int size;
    private int halfSize;

    public ValueCalculator(int size) {
        if (size < 1000000) {
            throw new IllegalArgumentException("Size must be at least 1000000");
        }
        this.size = size;
        this.halfSize = size / 2;
        this.arr = new float[size];
    }

    public int getSize() {
        return size;
    }

    public int getHalfSize() {
        return halfSize;
    }

    public void calculate() {
        long start = System.currentTimeMillis();
        Arrays.fill(arr, 1.0f);

        float[] a1 = new float[halfSize];
        float[] a2 = new float[halfSize];

        System.arraycopy(arr, 0, a1, 0, halfSize);
        System.arraycopy(arr, halfSize, a2, 0, halfSize);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < halfSize; i++) {
                int index = i;
                a1[index] = (float) (a1[index] * Math.sin(0.2f + index / 5) *
                        Math.cos(0.2f + index / 5) * Math.cos(0.4f + index / 2));
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < halfSize; i++) {
                int index = i;
                a2[index] = (float) (a2[index] * Math.sin(0.2f + (index + halfSize) / 5) *
                        Math.cos(0.2f + (index + halfSize) / 5) * Math.cos(0.4f + (index + halfSize) / 2));
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, halfSize);
        System.arraycopy(a2, 0, arr, halfSize, halfSize);

        long end = System.currentTimeMillis();
        System.out.println("Time elapsed: " + (end - start) + " ms");
    }
}
