package com.dave.art.generator;

import com.dave.art.SVGUtils;
import com.dave.art.Util;
import com.dave.art.color.ColorFrequencyMap;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RectangularGridCurved extends AbstractImageGenerator implements OutputStringGenerator{
    ColorFrequencyMap<Color, Integer> map;
    int imageWidth;
    int imageHeight;
    int rectangleWidth;
    int rectangleHeight;
    int rows;
    int columns;
    int borderRectangles;
    int pointDeviation;
    boolean useFrequency;
    ArrayList<Point> pointList = new ArrayList<>();
    Map<PointPair, String> pointPairBezierParametersMap = new HashMap<>();
    String output;

    public RectangularGridCurved(ColorFrequencyMap<Color, Integer> map, boolean useFrequency, int imageWidth, int imageHeight, int rectangleWidth, int rectangleHeight, int rows, int columns, int borderRectangles, int pointDeviation, Color background) {
        super(columns*rectangleWidth, rows*rectangleHeight, background);
        this.map = map;
        this.useFrequency = useFrequency;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.rectangleWidth = rectangleWidth;
        this.rectangleHeight = rectangleHeight;
        this.rows = rows;
        this.columns = columns;
        this.borderRectangles = borderRectangles;
        this.pointDeviation = pointDeviation;
    }

    private Point getPoint(int x,int y){
        for (Point point : pointList) {
            if (point.getX() == x && point.getY() == y){
                return point;
            }
        }

        Point point = new Point(x,y);
        pointList.add(point);

        return point;
    }

    public SVGGraphics2D build() throws IOException {
        ArrayList<Shape> shapes = new ArrayList<>();

        for (int row = borderRectangles; row < rows - borderRectangles; row++) {
            for (int column = borderRectangles; column < columns-borderRectangles; column++) {
                Shape shape = new Shape();
                shape.addPoint(getPoint(column*rectangleWidth, row*rectangleHeight));
                shape.addPoint(getPoint((column+1)*rectangleWidth, row*rectangleHeight));
                shape.addPoint(getPoint((column+1)*rectangleWidth, (row+1)*rectangleHeight));
                shape.addPoint(getPoint(column*rectangleWidth, (row+1)*rectangleHeight));
                shape.addPoint(getPoint(column*rectangleWidth, row*rectangleHeight));

                shapes.add(shape);
            }
        }

        for (Shape shape : shapes) {
            Iterator<Point> pointIterator = shape.points.iterator();
            for (int i = 0; i < shape.points.size() - 1; i++) {
                Point point1 = shape.points.get(i);
                Point point2 = shape.points.get(i+1);

                PointPair pointPair1 = new PointPair(point1, point2);
                PointPair pointPair2 = new PointPair(point2, point1);

                boolean found = false;
                for (PointPair pair : pointPairBezierParametersMap.keySet()) {
                    if (pair.equals(pointPair1)){
                        found = true;
                        break;
                    }
                    else if (pair.equals(pointPair2)){
                        found = true;
                        break;
                    }
                }

                if (!found){
                    pointPairBezierParametersMap.put(pointPair1, generateCurveParameter(pointPair1));
                }
            }
        }

        for (Point point : pointList) {
            int y = (int)point.getY();
            int x = (int)point.getX();

            int xDelta = 0;
            int yDelta = 0;

            if (pointDeviation > 0) {
                xDelta = Util.RANDOM.nextInt(pointDeviation * 2) - pointDeviation;
                yDelta = Util.RANDOM.nextInt(pointDeviation*2) - pointDeviation ;
            }

            point.move(x+xDelta, y+yDelta);
        }


        StringBuilder svgString = new StringBuilder();
        svgString.append(String.format(SVGUtils.SVG_FILE_HEADER, imageHeight, imageWidth));

        for (Shape shape : shapes) {
            Color randomColor = null;

            if (useFrequency){
                randomColor = map.getRandomColorWithFrequency();
            }
            else{
                randomColor = map.getRandomColor();
            }

            svgString.append(String.format(SVGUtils.SVG_G_ELEMENT_TEMPLATE_HEADER, randomColor.getRed(), randomColor.getGreen(), randomColor.getBlue()));

            String pathString = "M";
            
            for (int i = 0; i < shape.points.size() - 1; i++) {
                Point point1 = shape.points.get(i);
                Point point2 = shape.points.get(i+1);

                String bezierParameters = null;
                for (Map.Entry<PointPair, String> pointPairStringEntry : pointPairBezierParametersMap.entrySet()) {
                    PointPair key = pointPairStringEntry.getKey();

                    if (key.point1 == point1 && key.point2 == point2){
                        bezierParameters = pointPairStringEntry.getValue();
                    }
                    else if (key.point2 == point1 && key.point1 == point2) {
                        bezierParameters = reverseCurveParameters(pointPairStringEntry.getValue());
                    }

                }

                pathString += " " + point1.x + " " + point1.y + bezierParameters;

                if (i == shape.points.size() - 2){
                    pathString += " " + point2.x + " " + point2.y;
                }
            }

            pathString += " Z";
            svgString.append(String.format(SVGUtils.SVG_PATH_ELEMENT_TEMPLATE, pathString));
            svgString.append(SVGUtils.SVG_G_ELEMENT_TEMPLATE_FOOTER);
        }

        svgString.append(SVGUtils.SVG_FILE_FOOTER);

        output = svgString.toString();
        return null;
    }

    public String reverseCurveParameters(String value) {
        return value;
    }

    public String generateCurveParameter(PointPair pointPair) {
        int x = (pointPair.point1.x + pointPair.point2.x)/2;
        int y = (pointPair.point1.y + pointPair.point2.y)/2;
        
        return "Q " + x + " " + y;
    }

    private void printPointList() {
        for (Point point : pointList) {
            System.out.println(point);
        }
    }

    public String getOutput() {
        return output;
    }


    private class Shape{
        private ArrayList<Point> points = new ArrayList<>();

        void addPoint(Point point){
            points.add(point);
        }
    }

    static class PointPair{
        Point point1;
        Point point2;

        public PointPair(Point point1, Point point2) {
            this.point1 = point1;
            this.point2 = point2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PointPair pointPair = (PointPair) o;

            if (point1 != null ? !point1.equals(pointPair.point1) : pointPair.point1 != null) return false;
            return point2 != null ? point2.equals(pointPair.point2) : pointPair.point2 == null;
        }

        @Override
        public int hashCode() {
            int result = point1 != null ? point1.hashCode() : 0;
            result = 31 * result + (point2 != null ? point2.hashCode() : 0);
            return result;
        }
    }

    @Override
    public String toString() {
        return "RectangularGridCurved{" +
                ", imageWidth=" + imageWidth +
                ", imageHeight=" + imageHeight +
                ", rectangleWidth=" + rectangleWidth +
                ", rectangleHeight=" + rectangleHeight +
                ", rows=" + rows +
                ", columns=" + columns +
                ", borderRectangles=" + borderRectangles +
                ", pointDeviation=" + pointDeviation +
                ", useFrequency=" + useFrequency +
                '}';
    }
}
