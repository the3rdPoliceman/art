package com.dave.art.generator;

import com.dave.art.SVGUtils;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.Iterator;

public class Blob2 extends AbstractImageGenerator implements OutputStringGenerator
{

    private final Color blobColor;
    private String output;
    private int radiusVariation;
    private int handleOffsetAngleVariation;
    private int handleOffsetAngle;
    private Polygon polygon;
    private java.util.List<Point2D.Double> handles;

    public Blob2(Color blobColor, int imageWidth, int imageHeight, Color background, Polygon polygon, java.util.List<Point2D.Double> handles) {
        super(imageWidth, imageHeight, background);
        this.polygon = polygon;
        this.handles = handles;
        this.radiusVariation = radiusVariation;
        this.handleOffsetAngleVariation = handleOffsetAngleVariation;
        this.handleOffsetAngle = handleOffsetAngle;
        this.blobColor = blobColor;
    }

    @Override
    public SVGGraphics2D build() throws IOException {
        String path = "M";
        Iterator<Point2D.Double> handleIterator = handles.iterator();
        for (int i = 0; i < polygon.npoints; i++) {
            path += polygon.xpoints[i] + "," +polygon.ypoints[i] + " ";
            Point2D.Double handle1 = handleIterator.next();
            path += "C" +handle1.x+ "," +handle1.y + " ";
            Point2D.Double handle2 = handleIterator.next();
            path += handle2.x+ "," +handle2.y + " ";
        }

        path += polygon.xpoints[0] + "," +polygon.ypoints[0] + " ";

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
    public String getOutput() {
        return output;
    }
}
