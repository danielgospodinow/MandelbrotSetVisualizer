package com.danielgospodinow.fmi.projects.mandelbrotfractal;

import com.danielgospodinow.fmi.projects.mandelbrotfractal.image.Image;
import com.danielgospodinow.fmi.projects.mandelbrotfractal.utils.Bounds;
import com.danielgospodinow.fmi.projects.mandelbrotfractal.utils.MandelbrotEntity;
import org.apache.commons.math3.complex.Complex;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Mandelbrot {

    private static final int DISTANCE = 8;
    private static final int MAX_ITERATIONS = 150;

    private final int width;
    private final int height;
    private final Bounds<Float> bounds;

    public Mandelbrot(int width, int height, Bounds<Float> bounds) {
        this.width = width;
        this.height = height;
        this.bounds = bounds;
    }

    public Image getImage(int maxWorkers, int granularityLevel) {
        final int jobsPerWorker = ((width * height) / (maxWorkers * granularityLevel));

        Image image = new Image(width, height);

        ExecutorService workersPool = Executors.newFixedThreadPool(maxWorkers);
        List<List<MandelbrotEntity>> mandelbrotBatches = getMandelbrotBatches(jobsPerWorker);

        Instant start = Instant.now();

        mandelbrotBatches.forEach(batch -> workersPool.submit(new MandelbrotRunnable(image, batch, MAX_ITERATIONS, DISTANCE)));
        awaitExecutorTermination(workersPool);

        Instant finish = Instant.now();
        long timeElapsed = Duration.between(start, finish).toMillis();
        System.out.println(String.format("%.3f", (float) timeElapsed / 1000));

        return image;
    }

    public Image getImage() {
        return getImage(1, 1);
    }

    private List<List<MandelbrotEntity>> getMandelbrotBatches(int workerCapacity) {
        List<List<MandelbrotEntity>> batches = new ArrayList<>();
        List<MandelbrotEntity> allEntities = new LinkedList<>();

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Complex currentNum = getComplexFromPixel(x, y);
                allEntities.add(new MandelbrotEntity(x, y, currentNum));
            }
        }

        while (!allEntities.isEmpty()) {
            List<MandelbrotEntity> currentBatch = new ArrayList<>();
            for (int i = 0; i < workerCapacity && !allEntities.isEmpty(); ++i) {
                currentBatch.add(allEntities.remove(0));
            }

            batches.add(currentBatch);
        }

        return batches;
    }

    private Complex getComplexFromPixel(int x, int y) {
        float x0 = bounds.getMinX() + (bounds.getMaxX() - bounds.getMinX()) * ((float) x / width);
        float y0 = bounds.getMinY() + (bounds.getMaxY() - bounds.getMinY()) * ((float) y / height);
        return new Complex(x0, y0);
    }

    private static void awaitExecutorTermination(ExecutorService workersSpawner) {
        workersSpawner.shutdown();
        try {
            if (!workersSpawner.awaitTermination(1, TimeUnit.MINUTES)) {
                workersSpawner.shutdownNow();
            }
        } catch (InterruptedException e) {
            workersSpawner.shutdownNow();
            System.out.println("Image generation was interrupted!");
            e.printStackTrace();
        }
    }
}
