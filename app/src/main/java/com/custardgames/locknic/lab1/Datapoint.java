package com.custardgames.locknic.lab1;

/**
 * Created by locknic on 2/9/18.
 */

public class Datapoint {

    private float timestamp;
    private float value;

    public Datapoint(float value, float timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }

    public float getTimestamp() {
        return timestamp;
    }

    public float getValue() {
        return value;
    }
}
