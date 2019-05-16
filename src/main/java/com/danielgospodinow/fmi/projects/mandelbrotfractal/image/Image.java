package com.danielgospodinow.fmi.projects.mandelbrotfractal.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {

    private BufferedImage image;
    private int[][] imageRGBArray;

    public Image(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        this.imageRGBArray = new int[height][width];
    }

    public void setPixelRGBA(int x, int y, int a, int r, int g, int b) {
        this.imageRGBArray[x][y] = (a << 24) | (r << 16) | (g << 8) | b;
    }

    public void export(String imageFilePath) {
        for (int y = 0; y < this.image.getHeight(); ++y) {
            for (int x = 0; x < this.image.getWidth(); ++x) {
                this.image.setRGB(x, y, this.imageRGBArray[x][y]);
            }
        }

        try {
            File fileImage = new File(imageFilePath);
            ImageIO.write(this.image, imageFilePath.substring(imageFilePath.lastIndexOf(".") + 1), fileImage);
        } catch (IOException e) {
            System.out.println("Failed to write the image to the filesystem");
            e.printStackTrace();
        }
    }
}
