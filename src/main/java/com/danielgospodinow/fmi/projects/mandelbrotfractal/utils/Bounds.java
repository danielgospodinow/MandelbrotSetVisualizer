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

    public T getMaxX() {
        return this.xMax;
    }

    public T getMinX() {
        return this.xMin;
    }

    public T getMaxY() {
        return this.yMax;
    }

    public T getMinY() {
        return this.yMin;
    }
}
