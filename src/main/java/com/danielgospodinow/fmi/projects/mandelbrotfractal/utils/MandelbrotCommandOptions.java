package com.danielgospodinow.fmi.projects.mandelbrotfractal.utils;

public class MandelbrotCommandOptions {

    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;

    private static final float X_MIN = -2.0f;
    private static final float X_MAX = 2.0f;
    private static final float Y_MIN = -2.0f;
    private static final float Y_MAX = 2.0f;

    private int[] size = new int[]{WIDTH, HEIGHT};
    private float[] rectangle = new float[]{X_MIN, X_MAX, Y_MIN, Y_MAX};
    private int maxThreads = 1;
    private int granularityLevel = 12;
    private String outputImageFilename = "zad20.png";

    public MandelbrotCommandOptions(String size, String rectangle, String maxThreads, String outputImageFilename, String granularityLevel) {
        if (size != null && size.matches("[0-9]+x[0-9]+")) {
            String[] dimensions = size.split("x");
            this.size[0] = Integer.parseInt(dimensions[0]);
            this.size[1] = Integer.parseInt(dimensions[1]);
        }

        if (rectangle != null /*TODO: && rectangle.matches("REGEX")*/) {
            String[] bounds = rectangle.split(":");
            this.rectangle[0] = Float.parseFloat(bounds[0]);
            this.rectangle[1] = Float.parseFloat(bounds[1]);
            this.rectangle[2] = Float.parseFloat(bounds[2]);
            this.rectangle[3] = Float.parseFloat(bounds[3]);
        }

        if(maxThreads != null /*TODO: && maxThreads.matches("REGEX")*/) {
            this.maxThreads = Integer.parseInt(maxThreads);
        }

        if(outputImageFilename != null /*TODO: && outputImageFilename.matches("REGEX")*/) {
            this.outputImageFilename = outputImageFilename;
        }

        if(granularityLevel != null /*TODO: && granularityLevel.matches("REGEX")*/) {
            this.granularityLevel = Integer.parseInt(granularityLevel);
        }
    }

    public int getImageWidth() {
        return size[0];
    }

    public int getImageHeight() {
        return size[1];
    }

    public int getMaxThreads() {
        return maxThreads;
    }

    public int getGranularityLevel() {
        return granularityLevel;
    }

    public Bounds<Float> getBounds() {
        return new Bounds<>(rectangle[0], rectangle[1], rectangle[2], rectangle[3]);
    }

    public String getOutputImageFilename() {
        return outputImageFilename;
    }
}
