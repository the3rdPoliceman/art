package com.dave.art.generator;

import com.dave.art.GeometryUtils;
import com.dave.art.Util;
import com.dave.art.variable.Variable;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class NearestPointNet extends AbstractImageGenerator{
    private int pointCount;
    private int numberOfClosePoints;
    private Variable randomXCoordinate;
    private Variable randomYCoordinate;

    public NearestPointNet(int imageWidth, int imageHeight, int pointCount, int numberOfClosePoints, Variable randomXCoordinate, Variable randomYCoordinate) {
        super(imageWidth, imageHeight, null);
        this.pointCount = pointCount;
        this.numberOfClosePoints = numberOfClosePoints;
        this.randomXCoordinate = randomXCoordinate;
        this.randomYCoordinate = randomYCoordinate;
    }

    public SVGGraphics2D build() throws IOException {
        SVGGraphics2D svg = createSVG();

        ArrayList<Point> pointList = new ArrayList<>();

        for (int i = 0; i < pointCount; i++) {
            Point proposedPoint = new Point(randomXCoordinate.getValue(), randomYCoordinate.getValue());

            if (!pointList.contains(proposedPoint)){
                pointList.add(proposedPoint);
            }
        }

        for (Point currentPoint : pointList) {
            List<PointDistance> pointDistances = new ArrayList<>();

            for (Point point : pointList) {
                pointDistances.add(new PointDistance(point, GeometryUtils.distanceFrom(currentPoint, point)));
            }

            Collections.sort(pointDistances);

            for (int i = 1; i < numberOfClosePoints + 1; i++) {
                Line2D line = new Line2D.Double(currentPoint, pointDistances.get(i).point);
                svg.draw(line);
            }
        }

        return svg;
    }


    private class PointDistance implements Comparable
    {
        Point point;
        double distance;

        public PointDistance(Point point, double distance) {
            this.point = point;
            this.distance = distance;
        }

        @Override
        public int compareTo(Object o) {
            return Double.compare(distance, ((PointDistance)o).distance);
        }
    }

    @Override
    public String toString() {
        return "NearestPointNet{" +
                "pointCount=" + pointCount +
                '}';
    }
}
