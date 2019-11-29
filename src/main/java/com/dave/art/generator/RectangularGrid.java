package com.dave.art.generator;

import com.dave.art.color.ColorFrequencyMap;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.io.IOException;

public class RectangularGrid extends AbstractImageGenerator{
    ColorFrequencyMap<Color, Integer> map;
    int rectangleWidth;
    int rectangleHeight;
    int rows;
    int columns;
    private boolean useFrequency;

    public RectangularGrid(ColorFrequencyMap<Color, Integer> map, boolean useFrequency, int rectangleWidth, int rectangleHeight, int rows, int columns, Color background) {
        super(columns*rectangleWidth, rows*rectangleHeight, background);
        this.map = map;
        this.useFrequency = useFrequency;
        this.rectangleWidth = rectangleWidth;
        this.rectangleHeight = rectangleHeight;
        this.rows = rows;
        this.columns = columns;
    }

    public SVGGraphics2D build() throws IOException {
        SVGGraphics2D svg = new SVGGraphics2D(rectangleWidth*columns, rectangleHeight*rows);

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                Rectangle r = new Rectangle(column*rectangleWidth, row*rectangleHeight, rectangleWidth, rectangleHeight);

                Color randomColor = null;

                if (useFrequency){
                    randomColor = map.getRandomColorWithFrequency();
                }
                else{
                    randomColor = map.getRandomColor();
                }

                svg.setColor(randomColor);
                svg.fill(r);
            }
        }

        return svg;
    }


    @Override
    public String toString() {
        return "RectangularGrid{" +
                "rectangleWidth=" + rectangleWidth +
                ", rectangleHeight=" + rectangleHeight +
                ", rows=" + rows +
                ", columns=" + columns +
                ", useFrequency=" + useFrequency +
                '}';
    }
}
