package com.dave.art.generator;

import com.dave.art.GeometryUtils;
import com.dave.art.Util;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.IOException;

public class Spikey extends AbstractImageGenerator {

    private final Color blobColor;
    private final int points;

    public Spikey(Color blobColor, Color background, int imageWidth, int imageHeight, int points) {
        super(imageWidth, imageHeight, background);
        this.blobColor = blobColor;
        this.points = points;
    }

    @Override
    public SVGGraphics2D build() throws IOException {
        SVGGraphics2D svg = createSVG();

        Polygon blob = null;
        boolean closesCorrectly = false;

        while (closesCorrectly == false) {
            blob = new Polygon();

            for (int i = 0; i < points; i++) {
                if (blob.npoints < 2) {
                    blob.addPoint(Util.RANDOM.nextInt(getImageWidth()), Util.RANDOM.nextInt(getImageHeight()));
                } else {
                    Line2D[] existingLines = GeometryUtils.getLines(blob);
                    Point lastPoint = GeometryUtils.getLastPoint(blob);
                    Point proposedPoint = null;
                    boolean validPointFound = false;
                    while (validPointFound == false) {
                        validPointFound = true;
                        proposedPoint = new Point(Util.RANDOM.nextInt(getImageWidth()), Util.RANDOM.nextInt(getImageHeight()));

                        Line2D proposedLine = new Line2D.Double(lastPoint.x, lastPoint.y, proposedPoint.x, proposedPoint.y);

                        for (int j = 0; j < existingLines.length - 1; j++) {
                            Line2D existingLine = existingLines[j];

                            if (GeometryUtils.linesCross(existingLine, proposedLine)) {
                                validPointFound = false;
                            }
                        }
                    }

                    blob.addPoint(proposedPoint.x, proposedPoint.y);
                }
            }

            Line2D[] existingLines = GeometryUtils.getLines(blob);
            Point lastPoint = GeometryUtils.getLastPoint(blob);
            Point firstPoint = GeometryUtils.getFirstPoint(blob);
            Line2D closingLine = new Line2D.Double(firstPoint.x, firstPoint.y, lastPoint.x, lastPoint.y);

            closesCorrectly = true;

            for (int j = 1; j < existingLines.length - 1; j++) {
                Line2D existingLine = existingLines[j];

                if (GeometryUtils.linesCross(existingLine, closingLine)) {
                    closesCorrectly = false;
                }
            }
        }

        svg.setColor(blobColor);
        svg.fill(blob);
        return svg;
    }


    @Override
    public String toString() {
        return "Spikey{" +
                "blobColor=" + blobColor +
                ", points=" + points +
                '}';
    }
}
