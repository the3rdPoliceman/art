package com.dave.art;

import com.dave.art.variable.Variable;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;

/**
 * Created by dave on 13.06.18.
 */
public class DebugUtils {
    public static void drawPolygonVertices(SVGGraphics2D svg, Polygon polygon, boolean label){
        svg.setColor(Color.blue);
        for (int i = 0; i < polygon.npoints; i++) {
            svg.fillOval(polygon.xpoints[i] - 2, polygon.ypoints[i] -2, 4, 4);

            if (label){
                label(svg, polygon.xpoints[i]+4, polygon.ypoints[i], ""+i);
            }
        }
    }

    private static void label(SVGGraphics2D svg, int x, int y, String label) {
        svg.drawString(label, x, y);
    }

    public static void drawHandles(SVGGraphics2D svg, java.util.List<Point2D.Double> points, boolean label){
        svg.setColor(Color.red);
        for (int i = 0; i < points.size(); i++) {
            Point2D.Double point = points.get(i);

            svg.fillRect((int)point.x - 2, (int)point.y - 2, 4, 4);
            label(svg, (int)point.x+4, (int)point.y, ""+i);
        }
    }

    public static void printSvgToConsole(SVGGraphics2D svg){
        System.out.println(svg.getSVGElement());
    }   
}
