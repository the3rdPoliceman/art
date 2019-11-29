package com.dave.art.generator;

import com.dave.art.GeometryUtils;
import com.dave.art.Util;
import com.dave.art.color.ColorFrequencyMap;
import com.dave.art.variable.Variable;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;

public class RandomCircles extends AbstractImageGenerator {
    private ColorFrequencyMap<Color, Integer> map;
    private Variable circleDiameter;
    private int padding;
    private boolean useFrequency;
    private int missLimit;

    public RandomCircles(ColorFrequencyMap<Color, Integer> map, boolean useFrequency, int imageWidth, int imageHeight, Variable circleDiameter, int padding, int missLimit, Color background) {
        super(imageWidth, imageHeight, background);
        this.map = map;
        this.circleDiameter = circleDiameter;
        this.padding = padding;
        this.useFrequency = useFrequency;
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
            svg.setColor(map.getRandomColor());
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
        return "RandomCircles{" +
                "map=" + map +
                ", circleDiameter=" + circleDiameter +
                ", padding=" + padding +
                ", useFrequency=" + useFrequency +
                ", missLimit=" + missLimit +
                '}';
    }
}
