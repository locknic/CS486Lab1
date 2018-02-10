package com.custardgames.locknic.lab1;

/**
 * Created by locknic on 2/8/18.
 */

public class SlidingWindow {
    private int counter;
    private float window[];

    public SlidingWindow(int size) {
        counter = 0;
        window = new float[size];
    }

    public void add(float entry) {
        window[counter % window.length] = entry;
        counter++;
    }

    public float getMean() {
        if (counter >= window.length) {
            float total = 0;

            for (int x = 0; x < window.length; x++) {
                total += window[x];
            }

            return total / window.length;
        }

        return 0;
    }

    public float getWeighted() {
        if (counter >= window.length) {
            float total = 0;
            int weightCounter = 100;

            for (int x = counter % window.length - 1; x >= 0; x--) {
                total += window[x] * weightCounter;
                weightCounter--;
            }

            for (int x = 99; x > counter % window.length - 1; x--) {
                total += window[x] * weightCounter;
                weightCounter--;
            }

            return total / ((window.length * (window.length + 1)) / 2);
        }

        return 0;
    }
}
