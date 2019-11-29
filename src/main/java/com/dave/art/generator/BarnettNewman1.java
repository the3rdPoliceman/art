package com.dave.art.generator;

import com.dave.art.Util;
import com.dave.art.color.ColorFrequencyMap;
import com.dave.art.variable.Variable;
import org.jfree.graphics2d.svg.SVGGraphics2D;

import java.awt.*;
import java.io.IOException;

public class BarnettNewman1 extends AbstractImageGenerator {
    private ColorFrequencyMap<Color, Integer> map;
    private boolean useFrequency;
    private final Variable zipWidth;
    private final int zips;

    public BarnettNewman1(ColorFrequencyMap<Color, Integer> map, boolean useFrequency, int imageWidth, int imageHeight, Variable zipWidth, Color background, int zips) {
        super(imageWidth, imageHeight, background);
        this.map = map;
        this.useFrequency = useFrequency;
        this.zipWidth = zipWidth;
        this.zips = zips;
    }

    public SVGGraphics2D build() throws IOException {
        SVGGraphics2D svg = createSVG();
        for (int i = 0; i < zips; i++) {
            int currentZipWidth = zipWidth.getValue();

            Color currentZipColor = null;
            if (useFrequency){
                currentZipColor = map.getRandomColorWithFrequency();
            }
            else{
                currentZipColor = map.getRandomColor();
            }

            int zipXCoord = Util.RANDOM.nextInt(getImageWidth());
            Rectangle r = new Rectangle(zipXCoord, 0, currentZipWidth, getImageWidth());

            svg.setColor(currentZipColor);
            svg.fill(r);
        }

        return svg;
    }

    @Override
    public String toString() {
        return "BarnettNewman1{" +
                "map=" + map +
                ", useFrequency=" + useFrequency +
                ", zipWidth=" + zipWidth +
                ", zips=" + zips +
                '}';
    }
}
