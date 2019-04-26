package com.danielgospodinow.fmi.projects.mandelbrotfractal;

import com.danielgospodinow.fmi.projects.mandelbrotfractal.image.Image;
import com.danielgospodinow.fmi.projects.mandelbrotfractal.utils.Bounds;

import java.time.Duration;
import java.time.Instant;

public class Main {

    private static final int WIDTH = 2000;
    private static final int HEIGHT = 2000;

    private static final float X_MIN = -2.0f;
    private static final float X_MAX = 2.0f;
    private static final float Y_MIN = -2.0f;
    private static final float Y_MAX = 2.0f;

    private static final int DISTANCE = 8;

    private static final short MAX_ITERATIONS = 50;

    public static void main(String[] args) {
        Mandelbrot mandelbrot = new Mandelbrot(WIDTH, HEIGHT, MAX_ITERATIONS, DISTANCE, new Bounds<>(X_MIN, X_MAX, Y_MIN, Y_MAX));

        Instant start = Instant.now();
        Image mandelbrotImage = mandelbrot.getImage();
        Instant finish = Instant.now();

        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println(String.format("Time to generate an image: %.2f", (float) timeElapsed / 1000));

        mandelbrotImage.export("mandelbrot.png");
    }
}
