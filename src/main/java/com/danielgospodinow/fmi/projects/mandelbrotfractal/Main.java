package com.danielgospodinow.fmi.projects.mandelbrotfractal;

import com.danielgospodinow.fmi.projects.mandelbrotfractal.image.Image;
import com.danielgospodinow.fmi.projects.mandelbrotfractal.utils.MandelbrotCommandOptions;
import org.apache.commons.cli.*;

public class Main {

    public static void main(String[] args) {
        MandelbrotCommandOptions options = getOptions(args);

        Mandelbrot mandelbrot = new Mandelbrot(options.getImageWidth(), options.getImageHeight(), options.getBounds());

        Image mandelbrotImage = mandelbrot.getImage(options.getMaxThreads(), options.getGranularityLevel());
        mandelbrotImage.export(options.getOutputImageFilename());
    }

    private static MandelbrotCommandOptions getOptions(String[] args) {
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

        MandelbrotCommandOptions commandOptions = new MandelbrotCommandOptions(
                commandLine.getOptionValue("s"),
                commandLine.getOptionValue("r"),
                commandLine.getOptionValue("t"),
                commandLine.getOptionValue("o"),
                commandLine.getOptionValue("g"));

        return commandOptions;
    }
}
