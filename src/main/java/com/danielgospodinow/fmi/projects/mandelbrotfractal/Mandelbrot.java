package com.danielgospodinow.fmi.projects.mandelbrotfractal;

import com.danielgospodinow.fmi.projects.mandelbrotfractal.image.Image;
import com.danielgospodinow.fmi.projects.mandelbrotfractal.utils.Bounds;
import com.danielgospodinow.fmi.projects.mandelbrotfractal.utils.MandelbrotEntity;
import org.apache.commons.math3.complex.Complex;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

    public Image getImage(int totalWorkers) {
        final int totalJobsPerWorker = 128;

        Image image = new Image(width, height);
        ExecutorService workersPool = Executors.newFixedThreadPool(totalWorkers);
        List<List<MandelbrotEntity>> mandelbrotBatches = getMandelbrotBatches(totalJobsPerWorker);

        mandelbrotBatches.forEach(batch -> workersPool.submit(new MandelbrotRunnable(image, batch, maxIterations, distance)));
        awaitExecutorTermination(workersPool);
        return image;
    }

    public Image getImage() {
        return getImage(1);
    }

    private List<List<MandelbrotEntity>> getMandelbrotBatches(int workerCapacity) {
        List<List<MandelbrotEntity>> batches = new LinkedList<>();
        List<MandelbrotEntity> allEntities = new LinkedList<>();

        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                Complex currentNum = getComplexFromPixel(x, y);
                allEntities.add(new MandelbrotEntity(x, y, currentNum));
            }
        }

        while (!allEntities.isEmpty()) {
            List<MandelbrotEntity> currentBatch = new LinkedList<>();
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
