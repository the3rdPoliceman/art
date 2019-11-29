package com.dave.art;

import org.junit.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static org.junit.Assert.*;

/**
 * Created by dave on 15.06.18.
 */
public class GeometryUtilsTest {
    @Test
    public void getRelativePoint() throws Exception {
        Point2D.Double relativePoint;

        relativePoint = GeometryUtils.getRelativePoint(new Point2D.Double(100, 100), 0.0, 100);
        assertEquals(100.0,relativePoint.x, 0.1d);
        assertEquals(0.0, relativePoint.y, 0.1d);

        relativePoint = GeometryUtils.getRelativePoint(new Point2D.Double(100, 100), 30, 100);
        assertEquals(150.0,relativePoint.x, 0.1d);
        assertEquals(13.397, relativePoint.y, 0.1d);

        relativePoint = GeometryUtils.getRelativePoint(new Point2D.Double(100, 100), 45, 100);
        assertEquals(170.71,relativePoint.x, 0.1d);
        assertEquals(29.289, relativePoint.y, 0.1d);

        relativePoint = GeometryUtils.getRelativePoint(new Point2D.Double(100, 100), 60, 100);
        assertEquals(186.6,relativePoint.x, 0.1d);
        assertEquals(50.0, relativePoint.y, 0.1d);

        relativePoint = GeometryUtils.getRelativePoint(new Point2D.Double(100, 100), 90, 100);
        assertEquals(200.0,relativePoint.x, 0.1d);
        assertEquals(100.0, relativePoint.y, 0.1d);

        relativePoint = GeometryUtils.getRelativePoint(new Point2D.Double(100, 100), 135, 100);
        assertEquals(170.71,relativePoint.x, 0.1d);
        assertEquals(170.71, relativePoint.y, 0.1d);

        relativePoint = GeometryUtils.getRelativePoint(new Point2D.Double(100, 100), 180, 100);
        assertEquals(100.0,relativePoint.x, 0.1d);
        assertEquals(200.0, relativePoint.y, 0.1d);

        relativePoint = GeometryUtils.getRelativePoint(new Point2D.Double(100, 100), 225, 100);
        assertEquals(29.289,relativePoint.x, 0.1d);
        assertEquals(170.71, relativePoint.y, 0.1d);

        relativePoint = GeometryUtils.getRelativePoint(new Point2D.Double(100, 100), 270, 100);
        assertEquals(0.0,relativePoint.x, 0.1d);
        assertEquals(100.0, relativePoint.y, 0.1d);

        relativePoint = GeometryUtils.getRelativePoint(new Point2D.Double(100, 100), 315, 100);
        assertEquals(29.289,relativePoint.x, 0.1d);
        assertEquals(29.289, relativePoint.y, 0.1d);

        relativePoint = GeometryUtils.getRelativePoint(new Point2D.Double(100, 100), 330.0, 100);
        assertEquals(50.0,relativePoint.x, 0.1d);
        assertEquals(13.397, relativePoint.y, 0.1d);

        relativePoint = GeometryUtils.getRelativePoint(new Point2D.Double(100, 100), 360.0, 100);
        assertEquals(100.0,relativePoint.x, 0.1d);
        assertEquals(0.0, relativePoint.y, 0.1d);
    }


    @Test
    public void oppositeAngle() throws Exception {
        assertEquals(180.0,GeometryUtils.oppositeAngle(0.0),0.1d);
        assertEquals(270.0,GeometryUtils.oppositeAngle(90.0),0.1d);
        assertEquals(0.0,GeometryUtils.oppositeAngle(180.0),0.1d);
        assertEquals(90.0,GeometryUtils.oppositeAngle(270.0),0.1d);
        assertEquals(179.9,GeometryUtils.oppositeAngle(359.9),0.1d);
        assertEquals(359.9,GeometryUtils.oppositeAngle(179.9),0.1d);
        assertEquals(225.0,GeometryUtils.oppositeAngle(45.0),0.1d);
    }
    
    @Test
    public void getBearing() throws Exception {
        assertEquals(90.0, GeometryUtils.getBearing(new Point2D.Double(100,100), new Point2D.Double(150, 100)), 0.1d);
        assertEquals(180.0, GeometryUtils.getBearing(new Point2D.Double(100,100), new Point2D.Double(100, 150)), 0.1d);
        assertEquals(270.0, GeometryUtils.getBearing(new Point2D.Double(100,100), new Point2D.Double(50, 100)), 0.1d);
        assertEquals(0.0, GeometryUtils.getBearing(new Point2D.Double(100,100), new Point2D.Double(100, 50)), 0.1d);

        assertEquals(45.0, GeometryUtils.getBearing(new Point2D.Double(100,100), new Point2D.Double(150, 50)), 0.1d);
        assertEquals(135.0, GeometryUtils.getBearing(new Point2D.Double(100,100), new Point2D.Double(150, 150)), 0.1d);
        assertEquals(225.0, GeometryUtils.getBearing(new Point2D.Double(100,100), new Point2D.Double(50, 150)), 0.1d);
        assertEquals(315.0, GeometryUtils.getBearing(new Point2D.Double(100,100), new Point2D.Double(50, 50)), 0.1d);
    }

    @Test
    public void getQuadrant() throws Exception {
        assertEquals(GeometryUtils.Quadrant.NORTH_EAST, GeometryUtils.getQuadrant(new Point2D.Double(100,100), new Point2D.Double(150, 50)));
        assertEquals(GeometryUtils.Quadrant.SOUTH_EAST, GeometryUtils.getQuadrant(new Point2D.Double(100,100), new Point2D.Double(150, 150)));
        assertEquals(GeometryUtils.Quadrant.SOUTH_WEST, GeometryUtils.getQuadrant(new Point2D.Double(100,100), new Point2D.Double(50, 150)));
        assertEquals(GeometryUtils.Quadrant.NORTH_WEST, GeometryUtils.getQuadrant(new Point2D.Double(100,100), new Point2D.Double(50, 50)));
    }

}
