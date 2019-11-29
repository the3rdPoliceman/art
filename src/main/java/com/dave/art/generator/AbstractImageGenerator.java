package com.dave.art.generator;

import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;

public abstract class AbstractImageGenerator implements ImageGenerator {
    private int imageWidth;
    private int imageHeight;
    private Color background;

    public AbstractImageGenerator(int imageWidth, int imageHeight, Color background) {
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.background = background;
    }

    public Color getBackground() {
        return background;
    }

    @Override
    public int getImageWidth() {
        return imageWidth;
    }

    @Override
    public int getImageHeight() {
        return imageHeight;
    }

    public SVGGraphics2D createSVG() {
        SVGGraphics2D svgGraphics2D = new SVGGraphics2D(imageWidth, imageHeight);

        if (background != null) {
            svgGraphics2D.setColor(background);
            svgGraphics2D.fillRect(-1,-1,imageWidth+2, imageHeight+2);
        }

        return svgGraphics2D;
    }
}
