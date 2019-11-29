package com.dave.art.generator;

import com.dave.art.Util;
import com.dave.art.color.ColorFrequencyMap;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class RectangularGridShaken extends AbstractImageGenerator{
    ColorFrequencyMap<Color, Integer> map;
    int rectangleWidth;
    int rectangleHeight;
    int rows;
    int columns;
    private boolean useFrequency;
    private ArrayList<Point> pointList = new ArrayList<>();
    private int pointDeviation;

    public RectangularGridShaken(ColorFrequencyMap<Color, Integer> map, boolean useFrequency, int rectangleWidth, int rectangleHeight, int rows, int columns,
                                 int pointDeviation, Color background) {
        super(columns*rectangleWidth, rows*rectangleHeight, background);
        this.map = map;
        this.useFrequency = useFrequency;
        this.rectangleWidth = rectangleWidth;
        this.rectangleHeight = rectangleHeight;
        this.rows = rows;
        this.columns = columns;
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


        SVGGraphics2D svg = new SVGGraphics2D(rectangleWidth*columns, rectangleHeight*rows);

        ArrayList<Shape> shapes = new ArrayList<>();

        for (int row = 1; row < rows -1; row++) {
            for (int column = 1; column < columns-1; column++) {
                Shape shape = new Shape();
                shape.addPoint(getPoint(column*rectangleWidth, row*rectangleHeight));
                shape.addPoint(getPoint((column+1)*rectangleWidth, row*rectangleHeight));
                shape.addPoint(getPoint((column+1)*rectangleWidth, (row+1)*rectangleHeight));
                shape.addPoint(getPoint(column*rectangleWidth, (row+1)*rectangleHeight));
                shape.addPoint(getPoint(column*rectangleWidth, row*rectangleHeight));

                shapes.add(shape);
            }
        }

        for (Point point : pointList) {
            int y = (int)point.getY();
            int x = (int)point.getX();

            int xDelta = Util.RANDOM.nextInt(pointDeviation*2) - pointDeviation ;
            int yDelta = Util.RANDOM.nextInt(pointDeviation*2) - pointDeviation ;

            point.move(x+xDelta, y+yDelta);
        }

        for (Shape shape : shapes) {
            Color randomColor = null;

            if (useFrequency){
                randomColor = map.getRandomColorWithFrequency();
            }
            else{
                randomColor = map.getRandomColor();
            }

            svg.setColor(randomColor);

            int[] xpoints = new int[shape.points.size()];
            int[] ypoints = new int[shape.points.size()];
            int npoints = shape.points.size();

            for (int i = 0; i < shape.points.size(); i++) {
                Point point = shape.points.get(i);

                xpoints[i] = (int)point.getX();
                ypoints[i] = (int)point.getY();
            }

            svg.fillPolygon(xpoints,ypoints,npoints);
        }

        return svg;
    }

    private void printPointList() {
        for (Point point : pointList) {
            System.out.println(point);
        }
    }


    private class Shape{
        private ArrayList<Point> points = new ArrayList<>();

        void addPoint(Point point){
            points.add(point);
        }
    }

    @Override
    public String toString() {
        return "RectangularGridShaken{" +
                "rectangleWidth=" + rectangleWidth +
                ", rectangleHeight=" + rectangleHeight +
                ", rows=" + rows +
                ", columns=" + columns +
                ", useFrequency=" + useFrequency +
                ", pointDeviation=" + pointDeviation +
                '}';
    }
}
