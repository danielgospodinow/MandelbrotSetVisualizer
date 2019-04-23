package com.danielgospodinow.fmi.projects.mandelbrotfractal.utils;

public class Bounds <T> {

    private T xMin;
    private T xMax;
    private T yMin;
    private T yMax;

    public Bounds (T xMin, T xMax, T yMin, T yMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
    }

    public T getxMax() {
        return this.xMax;
    }

    public T getxMin() {
        return this.xMin;
    }

    public T getyMax() {
        return this.yMax;
    }

    public T getyMin() {
        return this.yMin;
    }
}
