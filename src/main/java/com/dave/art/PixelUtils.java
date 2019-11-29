package com.dave.art;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 12.05.18.
 */
public class PixelUtils {
    public static List<Point> listPixelsInShape(Shape shape){
        List<Point> pointList = new ArrayList<>();
        Rectangle bounds = shape.getBounds();

        for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
            for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
                Point point = new Point(x, y);
                if (shape.contains(point)){
                    pointList.add(point );
                }
            }
        }

        return pointList;
    }

    public static Color getAverageColor(Shape shape, BufferedImage image){
        List<Point> pixels = listPixelsInShape(shape);

        int pixelCount = 0;
        double[]hsvTotals = new double[]{0.0,0.0,0.0};
        for (Point pixel : pixels) {
            System.out.println(pixel);
            if (pixelWithinBounds(pixel, image)) {

                int rgb = image.getRGB((int) pixel.getX(), (int) pixel.getY());
                int red = (rgb & 0x00ff0000) >> 16;
                int green = (rgb & 0x0000ff00) >> 8;
                int blue = rgb & 0x000000ff;


                float[] hsv = new float[3];
                Color.RGBtoHSB(red, green, blue, hsv);

                for (int i = 0; i < 3; i++) {
                    hsvTotals[i] = hsvTotals[i] + hsv[i];
                }

                pixelCount++;
            }
        }

        float[] hsvAverage = new float[3];
        for (int i = 0; i < 3; i++) {
            hsvAverage[i] = (float)hsvTotals[i]/pixelCount;
        }

        return Color.getHSBColor(hsvAverage[0], hsvAverage[1], hsvAverage[2]);
    }

    private static boolean pixelWithinBounds(Point pixel, BufferedImage image) {
        return pixel.x < image.getWidth() && pixel.y < image.getHeight() && pixel.x >= 0 && pixel.y >= 0;
    }
}
