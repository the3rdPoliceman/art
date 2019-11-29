package com.dave.art.generator;

import com.dave.art.color.ColorFrequencyMap;
import com.dave.art.variable.Variable;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.io.IOException;

public class Stripes extends AbstractImageGenerator {
    private ColorFrequencyMap<Color, Integer> map;
    private Variable width;
    private boolean useFrequency;

    public Stripes(ColorFrequencyMap<Color, Integer> map, boolean useFrequency, int imageWidth, int imageHeight, Variable width, Color background) {
        super(imageWidth, imageHeight, background);
        this.map = map;
        this.useFrequency = useFrequency;
        this.width = width;
    }

    public SVGGraphics2D build() throws IOException {
        SVGGraphics2D svg = createSVG();

        int totalWidth = 0;
        while(totalWidth < getImageWidth()){
            int barWidth = width.getValue();

            if (barWidth+totalWidth > getImageWidth()){
                barWidth = getImageWidth() - totalWidth;
            }

            Rectangle r = new Rectangle(totalWidth, 0, barWidth, getImageWidth());

            Color randomColor = null;

            if (useFrequency){
                randomColor = map.getRandomColorWithFrequency();
            }
            else{
                randomColor = map.getRandomColor();
            }

            svg.setColor(randomColor);
            svg.fill(r);

            totalWidth += barWidth;
        }

        return svg;
    }

    @Override
    public String toString() {
        return "Stripes{" +
                "width=" + width +
                ", useFrequency=" + useFrequency +
                '}';
    }
}
