package com.danielgospodinow.fmi.projects.mandelbrotfractal;

import com.danielgospodinow.fmi.projects.mandelbrotfractal.image.Image;
import com.danielgospodinow.fmi.projects.mandelbrotfractal.utils.Bounds;
import org.apache.commons.math3.complex.Complex;

public class Mandelbrot {

    private final int width;
    private final int height;
    private final int maxIterations;
    private final float distance;
    private final Bounds<Float> bounds;

    public Mandelbrot(int width, int height, int maxIterations, float distance, Bounds<Float> bounds) {
        this.width = width;
        this.height = height;
        this.maxIterations = maxIterations;
        this.distance = distance;
        this.bounds = bounds;
    }

    public Image getImage() {
        Image image = new Image(this.width, this.height);

        for (int y = 0; y < this.height; ++y) {
            for (int x = 0; x < this.width; ++x) {
                Complex currentNum = getComplexFromPixel(x, y);

                int iterations = getMandelbrotIterations(currentNum);
                if (iterations < this.maxIterations) {
                    image.setPixelRGBA(x, y, 255, iterations * 255, iterations * 50, iterations * 20);
                }
            }
        }

        return image;
    }

    private int getMandelbrotIterations(Complex number) {
        int i = this.maxIterations;
        Complex complexIterator = number;

        while ((i-- > 0) && (complexIterator.abs() <= this.distance)) {
            complexIterator = complexIterator.pow(2).multiply(Math.pow(Math.E, complexIterator.pow(2).abs())).add(number);
        }

        return this.maxIterations - i;
    }

    private Complex getComplexFromPixel(int x, int y) {
        float x0 = this.bounds.getxMin() + (this.bounds.getxMax() - this.bounds.getxMin()) * ((float) x / this.width);
        float y0 = this.bounds.getyMin() + (this.bounds.getyMax() - this.bounds.getyMin()) * ((float) y / this.height);
        return new Complex(x0, y0);
    }
}
