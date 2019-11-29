package com.dave.art;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dave on 12.05.18.
 */
public class SVGUtils {
    public static final String SVG_FILE_HEADER = "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n" +
            "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:jfreesvg=\"http://www.jfree.org/jfreesvg/svg\" width=\"%d\" height=\"%d\" text-rendering=\"auto\" shape-rendering=\"auto\">\n" +
            "<defs></defs>\n";
    public static final String SVG_FILE_FOOTER = "</svg>";
    public static final String SVG_G_ELEMENT_TEMPLATE_HEADER = "<g style=\"fill: rgb(%d,%d,%d); fill-opacity: 1.0; stroke: none\" transform=\"matrix(1,0,0,1,0,0)\" >";
    public static final String SVG_G_ELEMENT_TEMPLATE_FOOTER = "</g>";
    public static final String SVG_PATH_ELEMENT_TEMPLATE= "<path d=\"%s \"/>";

    public static String setBackground(Color color, int imageWidth, int imageHeight){
        return null;
    }

}
