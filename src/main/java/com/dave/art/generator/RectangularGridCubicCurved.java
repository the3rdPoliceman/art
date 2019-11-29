package com.dave.art.generator;

import com.dave.art.Util;
import com.dave.art.color.ColorFrequencyMap;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class RectangularGridCubicCurved extends RectangularGridCurved{

    private int cubicDeviation;

    public RectangularGridCubicCurved(ColorFrequencyMap<Color, Integer> map, boolean useFrequency, int imageWidth, int imageHeight, int rectangleWidth, int rectangleHeight, int rows, int columns, int borderRectangles, int pointDeviation, int cubicDeviation, Color background) {
        super(map,  useFrequency, imageWidth, imageHeight, rectangleWidth, rectangleHeight, rows, columns, borderRectangles, pointDeviation, background);
        this.cubicDeviation = cubicDeviation;
    }

    public String generateCurveParameter(PointPair pointPair) {
        int x = generateCubicControlPoint(pointPair.point1.x, pointPair.point2.x, 0.25);
        int x1 = generateCubicControlPoint(pointPair.point1.x, pointPair.point2.x, 0.25);
        int y1 = generateCubicControlPoint(pointPair.point1.y,pointPair.point2.y, 0.25);
        int x2 = generateCubicControlPoint(pointPair.point1.x,pointPair.point2.x, 0.75);
        int y2 = generateCubicControlPoint(pointPair.point1.y,pointPair.point2.y, 0.75);
        
        return "C " + x1 + " " + y1 + " " + x2 + " " + y2;
    }

    private int generateCubicControlPoint(int i1, int i2, double shiftProportion) {
        int difference = Math.abs(i1 - i2);
        int requestedShift = (int) (difference * shiftProportion);
        int shiftedPoint = Math.min(i1, i2) + requestedShift;
        int randomShift = 0;
        if (cubicDeviation > 0)
        {
            randomShift = Util.RANDOM.nextInt(cubicDeviation * 2) - cubicDeviation;
        }
        int finalValue = shiftedPoint + randomShift;
        
        return finalValue;
    }

    @Override
    public String reverseCurveParameters(String value) {
        String[] parameters = value.split(" ");


        return parameters[0] + " " + parameters[3] + " " + parameters[4] + " " + parameters[1] + " " + parameters[2];
    }

    @Override
    public String toString() {
        return "RectangularGridCubicCurved{" +
                "cubicDeviation=" + cubicDeviation +
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
