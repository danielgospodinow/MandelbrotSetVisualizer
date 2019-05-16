package com.danielgospodinow.fmi.projects.mandelbrotfractal;

import com.danielgospodinow.fmi.projects.mandelbrotfractal.image.Image;
import com.danielgospodinow.fmi.projects.mandelbrotfractal.utils.Bounds;
import org.apache.commons.cli.*;

public class Main {

    private static final int WIDTH = 2000;
    private static final int HEIGHT = 2000;

    private static final float X_MIN = -2.0f;
    private static final float X_MAX = 2.0f;
    private static final float Y_MIN = -2.0f;
    private static final float Y_MAX = 2.0f;

    private static final int DISTANCE = 4;

    private static final int MAX_ITERATIONS = 100;

    private static final int INIT_THREADS = 1;
    private static final int INIT_GRANULARITY = 8;

    public static void main(String[] args) {
        CommandLine commandLine = getCommandLineParser(args);

        int maxThreads = (commandLine.hasOption("t") ? Integer.parseInt(commandLine.getOptionValue("t")) : INIT_THREADS);
        int granularityLevel = (commandLine.hasOption("g") ? Integer.parseInt(commandLine.getOptionValue("g")) : INIT_GRANULARITY);

        Mandelbrot mandelbrot = new Mandelbrot(WIDTH, HEIGHT, MAX_ITERATIONS, DISTANCE, new Bounds<>(X_MIN, X_MAX, Y_MIN, Y_MAX));
        Image mandelbrotImage = mandelbrot.getImage(maxThreads, granularityLevel);
        mandelbrotImage.export("mandelbrot.png");
    }

    private static CommandLine getCommandLineParser(String[] args) {
        CommandLineParser commandLineParser = new DefaultParser();

        Options options = new Options();

        Option imageSize = new Option("s", "size", true, "size of the output image");
        Option complexNumberBounds = new Option("r", "rect", true, "sets x,y bounds in the complex pane");
        Option threads = new Option("t", "tasks", true, "maximum threads in the process");
        Option outputImageName = new Option("o", "output", true, "name of the output image");
        Option quietMode = new Option("q", "quiet", false, "omits the debug messages");
        Option granularity = new Option("g", "granularity", true, "granularity level");

        options.addOption(imageSize);
        options.addOption(complexNumberBounds);
        options.addOption(threads);
        options.addOption(outputImageName);
        options.addOption(quietMode);
        options.addOption(granularity);

        CommandLine commandLine = null;
        try {
            commandLine = commandLineParser.parse(options, args);
        } catch (ParseException e) {
            System.out.println("Failed to parse command line arguments!");
            System.exit(1);
        }

        return commandLine;
    }
}
