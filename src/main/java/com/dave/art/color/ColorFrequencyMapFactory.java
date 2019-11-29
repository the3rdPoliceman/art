package com.dave.art.color;

import java.awt.*;

/**
 * Hello world!
 *
 */
public class ColorFrequencyMapFactory
{
    private ColorFrequencyMapFactory(){}

    public static ColorFrequencyMap<Color, Integer> getFallenAngelColorMap(){
        ColorFrequencyMap<Color, Integer> colorFrequencyMap = new ColorFrequencyMap<>();

        colorFrequencyMap.put(new Color(14,147,173), 623880);
        colorFrequencyMap.put(new Color(27,26,29), 266893);
        colorFrequencyMap.put(new Color(143,148,148), 234524);
        colorFrequencyMap.put(new Color(215,218,219), 110638);
        colorFrequencyMap.put(new Color(137,62,56), 78714);
        colorFrequencyMap.put(new Color(229,169,72), 73257);
        colorFrequencyMap.put(new Color(219,201,165), 40578);
        colorFrequencyMap.put(new Color(165,202,216), 39823);
        colorFrequencyMap.put(new Color(28,161,199), 37686);
        colorFrequencyMap.put(new Color(157,116,70), 37302);
        colorFrequencyMap.put(new Color(211,172,135), 36493);
        colorFrequencyMap.put(new Color(141,181,203), 32340);
        colorFrequencyMap.put(new Color(57,86,106), 18135);
        colorFrequencyMap.put(new Color(204,63,51), 6881);
        colorFrequencyMap.put(new Color(129,86,105), 5288);
        colorFrequencyMap.put(new Color(242,195,81), 3747);
        colorFrequencyMap.put(new Color(181,195,182), 2667);
        colorFrequencyMap.put(new Color(206,185,199), 2206);
        colorFrequencyMap.put(new Color(76,102,87), 1603);
        colorFrequencyMap.put(new Color(71,198,222), 539);
        colorFrequencyMap.put(new Color(205,83,107), 192);
        colorFrequencyMap.put(new Color(62,197,175), 13);

        return colorFrequencyMap;
    }

    public static ColorFrequencyMap<Color, Integer> getFallenAngelColorMapWithoutMainColor(){
        ColorFrequencyMap<Color, Integer> colorFrequencyMap = new ColorFrequencyMap<>();

        colorFrequencyMap.put(new Color(27,26,29), 266893);
        colorFrequencyMap.put(new Color(143,148,148), 234524);
        colorFrequencyMap.put(new Color(215,218,219), 110638);
        colorFrequencyMap.put(new Color(137,62,56), 78714);
        colorFrequencyMap.put(new Color(229,169,72), 73257);
        colorFrequencyMap.put(new Color(219,201,165), 40578);
        colorFrequencyMap.put(new Color(165,202,216), 39823);
        colorFrequencyMap.put(new Color(28,161,199), 37686);
        colorFrequencyMap.put(new Color(157,116,70), 37302);
        colorFrequencyMap.put(new Color(211,172,135), 36493);
        colorFrequencyMap.put(new Color(141,181,203), 32340);
        colorFrequencyMap.put(new Color(57,86,106), 18135);
        colorFrequencyMap.put(new Color(204,63,51), 6881);
        colorFrequencyMap.put(new Color(129,86,105), 5288);
        colorFrequencyMap.put(new Color(242,195,81), 3747);
        colorFrequencyMap.put(new Color(181,195,182), 2667);
        colorFrequencyMap.put(new Color(206,185,199), 2206);
        colorFrequencyMap.put(new Color(76,102,87), 1603);
        colorFrequencyMap.put(new Color(71,198,222), 539);
        colorFrequencyMap.put(new Color(205,83,107), 192);
        colorFrequencyMap.put(new Color(62,197,175), 13);

        return colorFrequencyMap;
    }


    public static ColorFrequencyMap<Color, Integer> getPoliakoffBookCoverColorMap(){
        ColorFrequencyMap<Color, Integer> colorFrequencyMap = new ColorFrequencyMap<>();

        colorFrequencyMap.put(new Color(4, 74, 110),2000);
        colorFrequencyMap.put(new Color(209,138,31),1000);
        colorFrequencyMap.put(new Color(197,200,146),1000);

        return colorFrequencyMap;
    }

    public static ColorFrequencyMap<Color, Integer> getColorListMap(Color... colors) {
        ColorFrequencyMap<Color, Integer> colorFrequencyMap = new ColorFrequencyMap<>();

        for (Color color : colors) {
            colorFrequencyMap.put(color,100000);
        }

        return colorFrequencyMap;
    }
}
