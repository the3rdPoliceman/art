package com.dave.art.generator;

import com.dave.art.Util;
import com.dave.art.color.ColorFrequencyMap;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class TwoPolygons extends AbstractImageGenerator{
    ColorFrequencyMap<Color, Integer> map;
    private boolean useFrequency;
    private int points;
    private int pointDeviation;

    public TwoPolygons(ColorFrequencyMap<Color, Integer> map, boolean useFrequency, int imageWidth, int imageHeight, int points,
                       int pointDeviation, Color background) {
        super(imageWidth, imageHeight, background);
        this.map = map;
        this.useFrequency = useFrequency;
        this.points = points;
        this.pointDeviation = pointDeviation;
    }

    public SVGGraphics2D build() throws IOException {
        SVGGraphics2D svg = new SVGGraphics2D(getImageWidth(), getImageHeight());

        Polygon polygon1 = new Polygon();
        Polygon polygon2 = new Polygon();

        drawPolygon(svg, polygon1);
        drawPolygon(svg, polygon2);



        return svg;
    }

    private Polygon generateBlob(){
        Polygon polygon = new Polygon();




        return polygon;
    }

    private void drawPolygon(SVGGraphics2D svg, Polygon polygon) {
        svg.drawPolygon(polygon.xpoints, polygon.ypoints, polygon.npoints);
    }
}
