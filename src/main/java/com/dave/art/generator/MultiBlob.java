package com.dave.art.generator;

import com.dave.art.SVGUtils;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class MultiBlob extends AbstractImageGenerator implements OutputStringGenerator
{

    private String output;
    private List<PolygonWithHandles> polygonsAndHandles = new ArrayList<>();

    public MultiBlob(int imageWidth, int imageHeight, Color background) {
        super(imageWidth, imageHeight, background);
    }

    public void addBlob(Polygon polygon, java.util.List<Point2D.Double> handles, Color color){
        polygonsAndHandles.add(new PolygonWithHandles(polygon, handles, color));
    }

    @Override
    public SVGGraphics2D build() throws IOException {

        StringBuilder svgString = new StringBuilder();
        svgString.append(String.format(SVGUtils.SVG_FILE_HEADER, getImageWidth(), getImageHeight()));

        for (PolygonWithHandles polygonsAndHandle : polygonsAndHandles) {
            List<Point2D.Double> handles = polygonsAndHandle.getHandles();
            Polygon polygon = polygonsAndHandle.getPolygon();
            Color color = polygonsAndHandle.getColor();

            svgString.append(String.format(SVGUtils.SVG_G_ELEMENT_TEMPLATE_HEADER, color.getRed(), color.getGreen(), color.getBlue()));
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

            svgString.append(String.format(SVGUtils.SVG_PATH_ELEMENT_TEMPLATE, path));
            svgString.append(SVGUtils.SVG_G_ELEMENT_TEMPLATE_FOOTER);
        }

        svgString.append(SVGUtils.SVG_FILE_FOOTER);

        output = svgString.toString();
        return null;
    }


    @Override
    public String getOutput() {
        return output;
    }

    private static class PolygonWithHandles{
        private Polygon polygon;
        private java.util.List<Point2D.Double> handles;
        private Color color;

        public PolygonWithHandles(Polygon polygon, List<Point2D.Double> handles, Color color) {
            this.polygon = polygon;
            this.handles = handles;
            this.color = color;
        }

        public Polygon getPolygon() {
            return polygon;
        }

        public void setPolygon(Polygon polygon) {
            this.polygon = polygon;
        }

        public List<Point2D.Double> getHandles() {
            return handles;
        }

        public void setHandles(List<Point2D.Double> handles) {
            this.handles = handles;
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color color) {
            this.color = color;
        }
    }
}
