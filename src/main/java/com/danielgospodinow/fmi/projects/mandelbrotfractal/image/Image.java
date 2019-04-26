package com.danielgospodinow.fmi.projects.mandelbrotfractal.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Image {

    private BufferedImage image;

    public Image(int width, int height) {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void setPixelRGBA(int x, int y, int a, int r, int g, int b) {
        this.image.setRGB(x, y, (a << 24) | (r << 16) | (g << 8) | b);
    }

    public void export(String imageFilePath) {
        try {
            File fileImage = new File(imageFilePath);
            ImageIO.write(this.image, imageFilePath.substring(imageFilePath.lastIndexOf(".") + 1), fileImage);
        } catch (IOException e) {
            System.out.println("Failed to write the image to the filesystem");
            e.printStackTrace();
        }
    }
}
