package com.danielgospodinow.fmi.projects.mandelbrotfractal;

import com.danielgospodinow.fmi.projects.mandelbrotfractal.image.Image;
import org.apache.commons.math3.complex.Complex;

public class Main {

    private static final int WIDTH = 2000;
    private static final int HEIGHT = 1800;

    private static final float X_MIN = -2.0f;
    private static final float X_MAX = 2.0f;
    private static final float Y_MIN = -2.0f;
    private static final float Y_MAX = 2.0f;

    private static final int DISTANCE = 4;

    private static final short MAX_ITERATIONS = 255;

    public static void main(String[] args) {
        Image image = new Image(WIDTH, HEIGHT);

        for (int y = 0; y < HEIGHT; ++y) {
            for (int x = 0; x < WIDTH; ++x) {
                Complex currentNum = getComplexFromPixel(x, y);

                int iterations = getMandelbrotIterations(currentNum);
                if(iterations < MAX_ITERATIONS) {
                    image.setPixelRGBA(x, y, 255, iterations * 255, iterations * 50, iterations * 20);
                }
            }
        }

        image.exportImage("mandelbrot.png");
    }

    private static int getMandelbrotIterations(Complex number) {
        int i = MAX_ITERATIONS;
        Complex complexIterator = number;

        while ((i-- > 0) && (complexIterator.abs() <= DISTANCE)) {
            complexIterator = complexIterator.pow(2).multiply(Math.pow(Math.E, complexIterator.pow(2).abs())).add(number);
        }

        return MAX_ITERATIONS - i;
    }

    private static Complex getComplexFromPixel(int x, int y) {
        float x0 = X_MIN + (X_MAX - X_MIN) * ((float) x / WIDTH);
        float y0 = Y_MIN + (Y_MAX - Y_MIN) * ((float) y / HEIGHT);
        return new Complex(x0, y0);
    }
}
