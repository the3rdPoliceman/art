package com.dave.art.color;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class ColorFrequencyMap<C, I extends Number> extends HashMap<Color,Integer>{
    private static Random random = new Random();

    public Color getRandomColor(){
        if (size() == 0){
            throw new RuntimeException("Map is empty");
        }

        java.awt.Color[] colors = new java.awt.Color[size()];

        return (keySet().toArray(colors))[randInt(0,size()-1)];
    }

    public Color getRandomColorWithFrequency(){
        if (size() == 0){
            throw new RuntimeException("Map is empty");
        }

        int totalFrequency = getTotalFrequency();
        int total = 0;
        Color currentColor = null;
        int position = randInt(0, totalFrequency - 1);
        for (Entry<Color, Integer> entry : entrySet()) {
            currentColor = entry.getKey();
            total+= ((java.lang.Integer)entry.getValue()).intValue();

            if (total > position){
                break;
            }
        }

        return currentColor;
    }

    private int getTotalFrequency() {
        int total = 0;
        for (Entry<Color, Integer> entry : entrySet()) {
            total+= ((java.lang.Integer)entry.getValue()).intValue();
        }

        return total;
    }

    public static int randInt(int min, int max) {
        int randomNum = random.nextInt((max - min) + 1) + min;

        return randomNum;
    }
}
