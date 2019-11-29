package com.dave.art;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class Util {
    public static final Random RANDOM = initRandom();

    public static Random initRandom() {
        return new Random(System.currentTimeMillis());
    }

    public static File getUserDirectory() {
        String userDir = System.getProperty("user.home");

        return new File(userDir);
    }

    public static File getArtDirectory() {
        File file = new File(Util.getUserDirectory() + File.separator + "art");

        return file;
    }

    public static String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }

    public static  BufferedImage loadImage(File file) throws IOException {
        return ImageIO.read(file);
    }

    public static File getImageDetailsFile(){
        File file = new File(getArtDirectory(),"image-details.txt");

        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("could not get image details file", e);
            }
        }

        return file;
    }

}
