package com.dave.art.generator;

import com.dave.art.color.ColorFrequencyMap;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.io.IOException;

public class HirstCircles extends AbstractImageGenerator {
    private ColorFrequencyMap<Color, Integer> map;
    private int rows;
    private int columns;
    private int circleDiameter;
    private int padding;
    private boolean useFrequency;

    public HirstCircles(ColorFrequencyMap<Color, Integer> map, boolean useFrequency, int imageWidth, int imageHeight, int rows, int columns, int circleDiameter, int padding, Color background) {
        super(imageWidth, imageHeight, background);
        this.map = map;
        this.rows = rows;
        this.columns = columns;
        this.circleDiameter = circleDiameter;
        this.padding = padding;
        this.useFrequency = useFrequency;
    }

    @Override
    public SVGGraphics2D build() throws IOException {
        SVGGraphics2D svg = createSVG();


        int totalWidth = 0;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Color randomColor = null;

                if (useFrequency){
                    randomColor = map.getRandomColorWithFrequency();
                }
                else{
                    randomColor = map.getRandomColor();
                }

                svg.setColor(randomColor);
                svg.fillOval(padding + column*(circleDiameter+padding), padding + row*(circleDiameter+padding), circleDiameter, circleDiameter);
            }
        }

        return svg;
    }

    @Override
    public String toString() {
        return "HirstCircles{" +
                "rows=" + rows +
                ", columns=" + columns +
                ", circleDiameter=" + circleDiameter +
                ", padding=" + padding +
                ", useFrequency=" + useFrequency +
                '}';
    }
}
