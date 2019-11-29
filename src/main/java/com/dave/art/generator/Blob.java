package com.dave.art.generator;

import com.dave.art.SVGUtils;
import com.dave.art.Util;
import com.dave.art.GeometryUtils;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.*;

public class Blob extends AbstractImageGenerator implements OutputStringGenerator
{

    private final Color blobColor;
    private final int points;
    private String output;
    private int radiusVariation;
    private int handleOffsetAngleVariation;
    private int handleOffsetAngle;

    public Blob(Color blobColor, int imageWidth, int imageHeight, Color background, int points, int radiusVariation, int handleOffsetAngleVariation, int handleOffsetAngle) {
        super(imageWidth, imageHeight, background);
        this.points = points;
        this.radiusVariation = radiusVariation;
        this.handleOffsetAngleVariation = handleOffsetAngleVariation;
        this.handleOffsetAngle = handleOffsetAngle;
        this.blobColor = blobColor;
    }

    @Override
    public SVGGraphics2D build() throws IOException {
        Polygon blob = createCircularPolygon(200, new Point(getImageWidth()/2, getImageHeight()/2));

        return null;
    }

    
    private Polygon createCircularPolygon(int radius, Point center) {
        Polygon blob = new Polygon();

        java.util.List<Point2D.Double> handles = new ArrayList<>();
        for (int i = 0; i < points; i++) {
            int angularStep = 360 / points;
            double angleInRadians = Math.toRadians(angularStep*i);
            double xOffset = Math.cos(angleInRadians)*radius;
            double yOffset = Math.sin(angleInRadians)*radius;

            int x = (int) (center.x + xOffset);
            int y = (int) (center.y + yOffset);

            blob.addPoint(x, y);

            int offset = Util.RANDOM.nextInt(handleOffsetAngleVariation) + handleOffsetAngle;
            int radiusOffset = Util.RANDOM.nextInt(radiusVariation *2) - radiusVariation;

            double handleAngleInRadians = Math.toRadians(angularStep*i + offset);
            int handleRadius = radius + radiusOffset;

            double xHandleOffset = Math.cos(handleAngleInRadians)*handleRadius;
            double yHandleOffset = Math.sin(handleAngleInRadians)*handleRadius;

            int handleX = (int)(center.x+xHandleOffset);
            int handleY = (int)(center.y+yHandleOffset);

            Point2D.Double newHandle = new Point2D.Double(handleX, handleY);
            if (handles.size() == 0){
                handles.add(newHandle);
            }
            else{
                Point2D.Double oppositeHandle = GeometryUtils.createOppositeHandle(GeometryUtils.getPoint(blob, blob.npoints - 1), newHandle);

                handles.add(oppositeHandle);
                handles.add(newHandle);
            }
        }


        handles.add(GeometryUtils.createOppositeHandle(GeometryUtils.getPoint(blob, 0), handles.get(0)));

        String path = "M";
        Iterator<Point2D.Double> handleIterator = handles.iterator();
        for (int i = 0; i < blob.npoints; i++) {
            path += blob.xpoints[i] + "," +blob.ypoints[i] + " ";
            Point2D.Double handle1 = handleIterator.next();
            path += "C" +handle1.x+ "," +handle1.y + " ";
            Point2D.Double handle2 = handleIterator.next();
            path += handle2.x+ "," +handle2.y + " ";
        }

        path += blob.xpoints[0] + "," +blob.ypoints[0] + " ";

        StringBuilder svgString = new StringBuilder();
        svgString.append(String.format(SVGUtils.SVG_FILE_HEADER, getImageWidth(), getImageHeight()));
        svgString.append(String.format(SVGUtils.SVG_G_ELEMENT_TEMPLATE_HEADER, blobColor.getRed(), blobColor.getGreen(), blobColor.getBlue()));
        svgString.append(String.format(SVGUtils.SVG_PATH_ELEMENT_TEMPLATE, path));
        svgString.append(SVGUtils.SVG_G_ELEMENT_TEMPLATE_FOOTER);

        svgString.append(SVGUtils.SVG_FILE_FOOTER);

        output = svgString.toString();
        return null;
    }

    @Override
    public String toString() {
        return "Blob{" +
                "blobColor=" + blobColor +
                ", points=" + points +
                '}';
    }

    @Override
    public String getOutput() {
        return output;
    }
}
