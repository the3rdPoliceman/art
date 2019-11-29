package com.dave.art;

import com.dave.art.color.ColorFrequencyMapFactory;
import com.dave.art.generator.*;
import com.dave.art.variable.ExponentialDistribution;
import com.dave.art.variable.InverseExponentialDistribution;
import com.dave.art.variable.Variable;
import org.apache.commons.io.FileUtils;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.SVGUtils;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{


    public static void main( String[] args ) throws IOException {
        Util.getUserDirectory();
        RectangularGrid rectangularGrid = new RectangularGrid(ColorFrequencyMapFactory.getFallenAngelColorMap(), true, 100,100, 8    , 8, null);
        Stripes stripes = new Stripes(ColorFrequencyMapFactory.getFallenAngelColorMap(), true, 800,800, new Variable(4,130, new ExponentialDistribution(0.5f)),  null);
        HirstCircles hirstCircles = new HirstCircles(ColorFrequencyMapFactory.getFallenAngelColorMap(), true, 820,820, 8,8, 80  ,  20   , null);
        RectangularGridShaken rectangularGridShaken = new RectangularGridShaken(ColorFrequencyMapFactory.getFallenAngelColorMap(), true, 200,200, 10    , 10, 400, null);
        RectangularGridCurved rectangularGridCurved = new RectangularGridCurved(ColorFrequencyMapFactory.getPoliakoffBookCoverColorMap(), true, 800   ,800, 300,300, 10    , 10, -2, 150, null);
        RectangularGridCubicCurved rectangularGridCubicCurved = new RectangularGridCubicCurved(ColorFrequencyMapFactory.getPoliakoffBookCoverColorMap(), true, 600   ,800, 250,250, 6    , 7, -1, 100, 30, null);
        RandomCircles randomCircles = new RandomCircles(ColorFrequencyMapFactory.getFallenAngelColorMapWithoutMainColor(), true, 600   ,800, new Variable(1,150),0, 100000, new Color(14,147,173));
        RandomCircles randomSmallCircles = new RandomCircles(ColorFrequencyMapFactory.getFallenAngelColorMapWithoutMainColor(), true, 600   ,800, new Variable(1,15),0, 5000, new Color(14,147,173));
        Spikey spikey = new Spikey(Color.blue, Color.orange, 800,600, 6);
        NearestPointNet nearestPointNet = new NearestPointNet(800,600,3000, 3,new Variable(0,800, new InverseExponentialDistribution(1.0f)), new Variable(0,600, new InverseExponentialDistribution(1.0f)));
        //RandomCircleImage randomCircleImage = new RandomCircleImage(Util.loadImage(new File("\\\\UBSPROD.MSAD.UBS.NET\\UserData\\t308879\\RF\\Desktop\\starry night.png")), new Variable(1,150), 0, 100000, Color.white);
        Blob blob = new Blob(Color.blue, 500,500,Color.gray,5, 40, 20, 15);

        Polygon circularPolygon = GeometryUtils.createCircularPolygon(300, new Point2D.Double(300, 300), 6);
        GeometryUtils.scatter(circularPolygon, new Variable(-50, 50), new Variable(-50, 50));
        java.util.List<Point2D.Double> cubicBezierHandles = GeometryUtils.createCubicBezierHandles(circularPolygon, new Variable(40, 80));
        Polygon circularPolygon2 = GeometryUtils.createCircularPolygon(300, new Point2D.Double(500, 700), 13);
        GeometryUtils.scatter(circularPolygon2, new Variable(-50, 50), new Variable(-150, 150));
        java.util.List<Point2D.Double> cubicBezierHandles2 = GeometryUtils.createCubicBezierHandles(circularPolygon2, new Variable(20, 20));

        MultiBlob multiBlob = new MultiBlob( 1000,1000, Color.yellow);
        multiBlob.addBlob(circularPolygon2,cubicBezierHandles2, Color.yellow);
        multiBlob.addBlob(circularPolygon,cubicBezierHandles, Color.blue);

        BarnettNewman1 barnettNewman1 = new BarnettNewman1(ColorFrequencyMapFactory.getFallenAngelColorMapWithoutMainColor(), false, 1000, 1000, new Variable(5,40), new Color(14,147,173), 4);

        ImageGenerator[] imageGenerators = new ImageGenerator[]{barnettNewman1, barnettNewman1, barnettNewman1, barnettNewman1, barnettNewman1, barnettNewman1, barnettNewman1, barnettNewman1, barnettNewman1, barnettNewman1};
        for (ImageGenerator imageGenerator : imageGenerators) {
            SVGGraphics2D svg = imageGenerator.build();
            File outputfile = getNewFileName(imageGenerator);
            System.out.println("Printing " + imageGenerator + " to " + outputfile.getAbsolutePath());

            if (imageGenerator instanceof OutputStringGenerator){
                FileUtils.writeStringToFile(outputfile, ((OutputStringGenerator)imageGenerator).getOutput());
            }
            else{
                SVGUtils.writeToSVG(outputfile, svg.getSVGElement());
            }

            FileUtils.write(Util.getImageDetailsFile(), outputfile.getName() + " " + imageGenerator.toString() + " " + new Date().toString() + System.getProperty("line.separator"), true);
        }
    }

    private static void debug(Polygon circularPolygon, List<Point2D.Double> cubicBezierHandles) {
        SVGGraphics2D svgGraphics2D = new SVGGraphics2D(2000,2000);
        DebugUtils.drawHandles(svgGraphics2D, cubicBezierHandles, true);
        DebugUtils.drawPolygonVertices(svgGraphics2D, circularPolygon, true);
        DebugUtils.printSvgToConsole(svgGraphics2D);
    }

    private static File getNewFileName(ImageGenerator imageGenerator) throws IOException {
        File file = new File(Util.getUserDirectory() + File.separator + "art" + File.separator + imageGenerator.getClass().getSimpleName() + "_" + System.currentTimeMillis() + ".svg");

        file.createNewFile();

        return file;
    }
}
