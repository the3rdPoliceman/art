package com.dave.art.generator;

import com.dave.art.GeometryUtils;
import com.dave.art.PixelUtils;
import com.dave.art.Util;
import com.dave.art.color.ColorFrequencyMap;
import com.dave.art.variable.Variable;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class RandomCircleImage extends AbstractImageGenerator {
    private BufferedImage image;
    private Variable circleDiameter;
    private int padding;
    private int missLimit;

    public RandomCircleImage(BufferedImage image,Variable circleDiameter, int padding, int missLimit, Color background) {
        super(image.getWidth(), image.getHeight(), background);
        this.image = image;
        this.circleDiameter = circleDiameter;
        this.padding = padding;
        this.missLimit = missLimit;
    }

    @Override
    public SVGGraphics2D build() throws IOException {
        SVGGraphics2D svg = createSVG();

        ArrayList<Circle> circles = new ArrayList<>();
        int misses = 0;

        long totalCirclesCreated = 0;
        long totalComparisons = 0;


        while (misses < missLimit){
            Circle proposedCircle = new Circle(Util.RANDOM.nextInt(getImageWidth()), Util.RANDOM.nextInt(getImageHeight()));
            totalCirclesCreated++;
            double closestDistance = Integer.MAX_VALUE;

            for (Circle existingCircle : circles) {
                double distance = existingCircle.distanceFrom(proposedCircle.x, proposedCircle.y);
                totalComparisons++;
                if (distance < closestDistance){
                    closestDistance = distance;
                }

                if (closestDistance < 0){
                    break;
                }
            }

            int maxRadius = (int) (closestDistance - padding);
            int suggestedDiameter = circleDiameter.getValue();

            if (suggestedDiameter < maxRadius){
                proposedCircle.radius = suggestedDiameter;
                circles.add(proposedCircle);
                misses = 0;
            }
            else{
                misses++;
            }

            System.out.println(circles.size() + ", " + misses);
        }

        for (Circle circle : circles) {
            svg.setColor(PixelUtils.getAverageColor(circle.asEllipse(), image));
            svg.fill(circle.asEllipse());
        }

        System.out.println(totalCirclesCreated + " Circles created");
        System.out.println(totalComparisons + " Comparisons");
        return svg;
    }

    private class Circle{
        int x;
        int y;
        int radius;

        public Circle(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Ellipse2D asEllipse(){
            Ellipse2D.Float ellipse = new Ellipse2D.Float();
            ellipse.setFrame(x - radius, y-radius, radius*2, radius*2);

            return ellipse;
        }

        public double distanceFrom(int x, int y){
            return GeometryUtils.distanceFrom(this.x, this.y, x, y) - radius;
        }
    }

    @Override
    public String toString() {
        return "RandomCircleImage{" +
                "image=" + image +
                ", circleDiameter=" + circleDiameter +
                ", padding=" + padding +
                ", missLimit=" + missLimit +
                '}';
    }
}
