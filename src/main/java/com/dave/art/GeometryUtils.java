package com.dave.art;

import com.dave.art.variable.Variable;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Created by dave on 13.06.18.
 */
public class GeometryUtils {
    public static Point2D.Double createOppositeHandle(Point2D.Double point, Point2D.Double handle) {
        double x = point.x;
        double y = point.y;
        double handleX = handle.x;
        double handleY = handle.y;
        double x1 = x - (handleX - x);
        double y1 = y - (handleY - y);

        return new Point2D.Double(x1, y1);
    }

    public static Point2D.Double getPoint(Polygon polygon, int pointIndex) {
        return new Point2D.Double(polygon.xpoints[pointIndex], polygon.ypoints[pointIndex]);
    }

    public static Polygon createCircularPolygon(int radius, Point2D.Double center, int points) {
        Polygon polygon = new Polygon();

        for (int i = 0; i < points; i++) {
            int degreesPerPoint = 360 / points;
            double radiansPerPoint = Math.toRadians(degreesPerPoint * i);

            double xOffset = Math.cos(radiansPerPoint) * radius;
            double yOffset = Math.sin(radiansPerPoint) * radius;

            int x = (int) (center.x + xOffset);
            int y = (int) (center.y + yOffset);

            polygon.addPoint(x, y);
        }

        return polygon;
    }

    public static java.util.List<Point2D.Double> createCubicBezierHandles(Polygon polygon, Variable handleLength){

        java.util.List<Point2D.Double> handles = new ArrayList<>();

        System.out.println("Point List");
        for (int i = 0; i < polygon.npoints; i++) {
            System.out.println(polygon.xpoints[i] + "," + polygon.ypoints[i]);
        }
        System.out.println();

        for (int i = 0; i < polygon.npoints; i++) {
            Point2D.Double currentPoint = getPoint(polygon, i);
            Point2D.Double pointBefore = ((i == 0) ? getPoint(polygon, polygon.npoints - 1) : getPoint(polygon, i-1)) ;
            Point2D.Double pointAfter = ((i == polygon.npoints-1) ? getPoint(polygon, 0) : getPoint(polygon, i+1)) ;

            System.out.println("Generating handles for point " + i);
            System.out.println("pointBefore " + pointBefore);
            System.out.println("Point       " + currentPoint);
            System.out.println("pointAfter  " + pointAfter);

            double angleToPointAfter = getBearing(currentPoint, pointAfter);
            double angleToPointBefore = getBearing(currentPoint, pointBefore);

            System.out.println("angleToPointAfter  " + angleToPointAfter);
            System.out.println("angleToPointBefore  " + angleToPointBefore);

            double normalAngle = getNormalAngle(angleToPointAfter, angleToPointBefore);
            double tangentAngle= getTangentFromNormal(normalAngle);
            double tangentAngle2= oppositeAngle(tangentAngle);

            tangentAngle = Math.abs(tangentAngle-angleToPointBefore) < Math.abs(tangentAngle2-angleToPointBefore)?tangentAngle:tangentAngle2;

            System.out.println("normalAngle  " + normalAngle);
            System.out.println("tangentAngle  " + tangentAngle);

            int generatedHandleLength = handleLength.getValue();

            Point2D.Double handle1 = getRelativePoint(currentPoint, tangentAngle, generatedHandleLength);
            Point2D.Double handle2 = createOppositeHandle(currentPoint,handle1);

            handles.add(handle1);
            handles.add(handle2);
        }

        // switch first (handle before point 0), to last
        handles.add(handles.remove(0));

        return handles;
    }

    public static Point point2DtoPoint(Point2D.Double p2d){
        return new Point((int)p2d.getX(), (int)p2d.getY());
    }

    public static Point2D.Double pointtoPoint2D(Point point){
        return new Point2D.Double(point.getX(), point.getY());
    }

    private static double getTangentFromNormal(double normalAngle) {
        return normalAngle +90;
    }

    private static double getNormalAngle(double angleToPointAfter, double angleToPointBefore) {
        return (angleToPointAfter+angleToPointBefore)/2;
    }

    public static Point2D.Double getRelativePoint(Point2D.Double startingPoint, double bearing, double distance){
        double bearingInRadians = Math.toRadians(bearing);

        double x = startingPoint.getX() + distance*(Math.sin(bearingInRadians));
        double y = startingPoint.getY() - distance*(Math.cos(bearingInRadians));

        return new Point2D.Double(x,y);
    }

    public static double oppositeAngle(double angle){
        double oppositeAngle = angle + 180;

        if (oppositeAngle >= 360.0){
            oppositeAngle = oppositeAngle - 360.0;
        }

        return oppositeAngle;
    }
    
    
    public static double getBearing(Point2D.Double source, Point2D.Double destination){
        Quadrant quadrant = getQuadrant(source,destination);

        double aSquared = Math.pow(source.x-destination.x, 2.0);
        double bSquared = Math.pow(source.y-destination.y, 2.0);
        double hypoteneuse = Math.sqrt(aSquared+ bSquared);

        if (quadrant == Quadrant.NORTH_EAST){
            double opposite = destination.x-source.x;
            return Math.toDegrees(Math.asin(opposite/hypoteneuse));
        }
        else if (quadrant == Quadrant.SOUTH_EAST){
            double opposite = destination.y-source.y;
            return 90.0 + Math.toDegrees(Math.asin(opposite/hypoteneuse));
        }
        else if (quadrant == Quadrant.SOUTH_WEST){
            double opposite = source.x-destination.x;
            return 180.0 + Math.toDegrees(Math.asin(opposite/hypoteneuse));
        }
        else {
            double adjacent = source.x-destination.x;
            double result = 270.0 + Math.toDegrees(Math.acos(adjacent/hypoteneuse));

            if (result >= 360.0){
                result= result - 360.0;
            }

            return result;
        }
    }

    public static Quadrant getQuadrant(Point2D.Double origin, Point2D.Double destination){
        if (destination.x > origin.x){
            if (destination.y > origin.y){
                return Quadrant.SOUTH_EAST;
            }
            else{
                return Quadrant.NORTH_EAST;
            }
        }
        else{
            if (destination.y > origin.y){
                return Quadrant.SOUTH_WEST;
            }
            else{
                return Quadrant.NORTH_WEST;
            }
        }
    }

    public static void scatter(Polygon polygon, Variable xVariation, Variable yVariation) {
        for (int pointIndex = 0; pointIndex < polygon.npoints; pointIndex++) {
            polygon.xpoints[pointIndex] = polygon.xpoints[pointIndex] + xVariation.getValue();
            polygon.ypoints[pointIndex] = polygon.ypoints[pointIndex] + yVariation.getValue();
        }
    }

    public static double distanceFrom(int x1, int y1, int x2, int y2){
        return Math.pow(Math.pow(Math.abs(x1-x2), 2.0) + Math.pow(Math.abs(y1-y2), 2.0), 0.5);
    }

    public static double distanceFrom(Point point1, Point point2){
        return distanceFrom(point1.x, point1.y, point2.x, point2.y);
    }

    public static Point getFirstPoint(Polygon polygon) {
        return new Point(polygon.xpoints[0], polygon.ypoints[0]);
    }

    public static boolean linesCross(Line2D existingLine, Line2D proposedLine) {
        return existingLine.intersectsLine(proposedLine);
    }

    public static Line2D[] getLines(Polygon polygon) {
        Line2D[] result = new Line2D.Double[polygon.npoints-1];

        for (int i = 0; i < polygon.npoints - 1; i++) {
            result[i] = new Line2D.Double(polygon.xpoints[i], polygon.ypoints[i], polygon.xpoints[i+1], polygon.ypoints[i+1]);
        }

        return result;
    }

    public static Point getLastPoint(Polygon polygon) {
        return new Point(polygon.xpoints[polygon.npoints-1], polygon.ypoints[polygon.npoints-1]);
    }

    public enum Quadrant{
        NORTH_EAST, SOUTH_EAST, SOUTH_WEST, NORTH_WEST
    }
}
