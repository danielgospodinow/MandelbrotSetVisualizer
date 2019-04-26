package com.danielgospodinow.fmi.projects.mandelbrotfractal;

import com.danielgospodinow.fmi.projects.mandelbrotfractal.image.Image;
import com.danielgospodinow.fmi.projects.mandelbrotfractal.utils.MandelbrotEntity;
import org.apache.commons.math3.complex.Complex;

import java.util.List;

public class MandelbrotRunnable implements Runnable {

    private final Image image;
    private final List<MandelbrotEntity> batch;
    private final int maxIterations;
    private final float distance;

    public MandelbrotRunnable(Image image, List<MandelbrotEntity> batch, int maxIterations, float distance) {
        this.image = image;
        this.batch = batch;
        this.maxIterations = maxIterations;
        this.distance = distance;
    }

    @Override
    public void run() {
        batch.forEach(entity -> {
            int iterations = getIterations(entity.getComplexNumber());
            if (iterations < this.maxIterations) {
                image.setPixelRGBA(entity.getX(), entity.getY(), 255, iterations * 255, iterations * 50, iterations * 20);
            } else {
                image.setPixelRGBA(entity.getX(), entity.getY(), 255, 0, 0, 0);
            }
        });
    }

    private int getIterations(Complex number) {
        Complex complexIterator = number;

        int i;
        for (i = 0; (i < this.maxIterations) && (complexIterator.abs() <= this.distance); ++i) {
            complexIterator = complexIterator.pow(2).multiply(Math.pow(Math.E, complexIterator.pow(2).abs())).add(number);
        }

        return this.maxIterations - i;
    }
}
