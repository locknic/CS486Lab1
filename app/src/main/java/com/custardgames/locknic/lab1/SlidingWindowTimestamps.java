package com.custardgames.locknic.lab1;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * Created by locknic on 2/8/18.
 */

public class SlidingWindowTimestamps {
    private LinkedList<Datapoint> window;


    public SlidingWindowTimestamps() {
        window = new LinkedList<Datapoint>();
    }

    public void add(float value, float timestamp) {
        cullOld(timestamp);
        window.add(new Datapoint(value, timestamp));
    }

    public float getMean() {
        float total = 0;

        for (Datapoint datapoint : window) {
            total += datapoint.getValue();
        }

        return total / window.size();
    }

    private void cullOld(float currentTime) {
        Iterator<Datapoint> iterator = window.iterator();

        while(iterator.hasNext()) {
            if (iterator.next().getTimestamp() < currentTime - 10000) {
                iterator.remove();
            } else {
                break;
            }
        }
    }
}
