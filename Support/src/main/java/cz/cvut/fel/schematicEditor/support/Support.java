package cz.cvut.fel.schematicEditor.support;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;

/**
 * This class is used for static support methods. It will be deprecated in time.
 *
 * @author Urban Kravjansk�
 */
@Deprecated
public class Support {

    /**
     * Calculates distance between given point and line.
     *
     * @param p
     *            given point.
     * @param l
     *            given line.
     * @return Distance between given point and line.
     */
    public static double distance(Point2D.Double p, Line2D.Double l) {
        double a = p.getX();
        double b = p.getY();
        double x1 = l.getX1();
        double x2 = l.getX2();
        double y1 = l.getY1();
        double y2 = l.getY2();

        return Math.abs((x2 - x1) * (y1 - b) - (x1 - a) * (y2 - y1))
               / Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    /**
     * Calculates distance between two given points.
     *
     * @param p1
     *            given 1st point.
     * @param p2
     *            given 2nd point.
     * @return Distance between given points.
     */
    public static double distance(Point2D.Double p1, Point2D.Double p2) {
        double x1 = p1.getX();
        double y1 = p1.getY();
        double x2 = p2.getX();
        double y2 = p2.getY();

        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    /**
     * Calculates middle point between two given points.
     *
     * @param p1
     *            given 1st point.
     * @param p2
     *            given 2nd point.
     * @return Middle point between given points.
     */
    public static Point2D.Double middle(Point2D.Double p1, Point2D.Double p2) {
        return new Point2D.Double((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
    }

    /**
     * Calculates average of two given {@link Unit}s.
     *
     * @param a
     *            given 1st {@link Unit}.
     * @param b
     *            given 2nd {@link Unit}.
     * @return Average of two given {@link Unit}s.
     */
    public static Unit average(Unit a, Unit b) {
        return new Pixel((a.doubleValue() + b.doubleValue()) / 2);
    }

    /**
     * Calculates rectangle of given size around given point.
     *
     * @param point
     *            center point of rectangle.
     * @param size
     *            diameter of rectangle.
     * @return Calculated {@link Rectangle2D.Double}.
     */
    public static Rectangle2D.Double createPointerRectangle(Point2D.Double point, double size) {
        Rectangle2D.Double result = new Rectangle2D.Double(point.getX() - size,
                point.getY() - size, 2 * size, 2 * size);

        return result;
    }
}
