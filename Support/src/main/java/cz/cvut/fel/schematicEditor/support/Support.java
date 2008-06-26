package cz.cvut.fel.schematicEditor.support;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;

/**
 * @author Urban Kravjanský
 */
public class Support {
    /**
     * @param p
     * @param l
     * @return
     */
    public static double distance(Point2D.Double p, Line2D.Double l) {
        double a = p.getX();
        double b = p.getY();
        double x1 = l.getX1();
        double x2 = l.getX2();
        double y1 = l.getY1();
        double y2 = l.getY2();

        return Math.abs((x2 - x1) * (y1 - b) - (x1 - a) * (y2 - y1)) / Math
                .sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public static double distance(Point2D.Double p1, Point2D.Double p2) {
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();

        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    public static Point2D.Double avg(Point2D.Double p1, Point2D.Double p2) {
        return new Point2D.Double((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
    }

    public static double avg(double a, double b) {
        return (a + b) / 2;
    }

    public static Unit avg(Unit a, Unit b) {
        return new Pixel((a.doubleValue() + b.doubleValue()) / 2);
    }

    /**
     * This method returns rectangle around given point.
     * 
     * @param point
     * @param size
     * @return
     */
    public static Rectangle2D.Double createPointerRectangle(Point2D.Double point, double size) {
        Rectangle2D.Double result = new Rectangle2D.Double(point.getX() - size,
                point.getY() - size, 2 * size, 2 * size);

        return result;
    }
}
