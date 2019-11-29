package com.dave.art.generator;

import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.io.IOException;

public interface ImageGenerator {
    int getImageWidth();

    int getImageHeight();

    SVGGraphics2D build() throws IOException;
}
