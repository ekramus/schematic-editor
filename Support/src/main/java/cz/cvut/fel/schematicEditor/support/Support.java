package cz.cvut.fel.schematicEditor.support;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

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
     * @param p given point.
     * @param l given line.
     * @return Distance between given point and line.
     */
    public static double distance(Point2D.Double p, Line2D.Double l) {
        return l.ptLineDist(p);
    }

    /**
     * Calculates distance between two {@link UnitPoint} instances.
     *
     * @param p1 First {@link UnitPoint} instance.
     * @param p2 Second {@link UnitPoint} instance.
     * @return {@link Unit} instance representing distance.
     */
    public static Unit distance(UnitPoint p1, UnitPoint p2) {
        Unit result;

        result = new Pixel(distance(new Point2D.Double(p1.getX(), p1.getY()), new Point2D.Double(p2.getX(), p2.getY())));

        return result;
    }

    /**
     * Calculates distance between two given points.
     *
     * @param p1 given 1st point.
     * @param p2 given 2nd point.
     * @return Distance between given points.
     */
    public static double distance(Point2D.Double p1, Point2D.Double p2) {
        return p1.distance(p2);
    }

    /**
     * Calculates middle point between two given points.
     *
     * @param p1 given 1st point.
     * @param p2 given 2nd point.
     * @return Middle point between given points.
     */
    public static Point2D.Double middle(Point2D.Double p1, Point2D.Double p2) {
        return new Point2D.Double((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
    }

    /**
     * Calculates middle point between two given points.
     *
     * @param p1 given 1st point.
     * @param p2 given 2nd point.
     * @return Middle point between given points.
     */
    public static UnitPoint middle(UnitPoint p1, UnitPoint p2) {
        try {
            return new UnitPoint((p1.getX() + p2.getX()) / 2, (p1.getY() + p2.getY()) / 2);
        } catch (NullPointerException e) {
            return (p1 == null) ? p2 : p1;
        }
    }

    /**
     * Calculates average of two given {@link Unit}s.
     *
     * @param a given 1st {@link Unit}.
     * @param b given 2nd {@link Unit}.
     * @return Average of two given {@link Unit}s.
     */
    public static Unit average(Unit a, Unit b) {
        return new Pixel((a.doubleValue() + b.doubleValue()) / 2);
    }

    /**
     * Calculates rectangle of given size around given point.
     *
     * @param point center point of rectangle.
     * @param size diameter of rectangle.
     * @return Calculated {@link Rectangle2D.Double}.
     */
    public static Rectangle2D.Double createPointerRectangle(Point2D.Double point, Point2D.Double size) {
        Rectangle2D.Double result = new Rectangle2D.Double(point.getX() - size.getX(), point.getY() - size.getY(),
                2 * size.getX(), 2 * size.getY());

        return result;
    }
}
