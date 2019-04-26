package com.danielgospodinow.fmi.projects.mandelbrotfractal.utils;

import org.apache.commons.math3.complex.Complex;

public class MandelbrotEntity {

    private final int x;
    private final int y;
    private final Complex complexNumber;

    public MandelbrotEntity(int x, int y, Complex complexNumber) {
        this.x = x;
        this.y = y;
        this.complexNumber = complexNumber;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Complex getComplexNumber() {
        return complexNumber;
    }
}
