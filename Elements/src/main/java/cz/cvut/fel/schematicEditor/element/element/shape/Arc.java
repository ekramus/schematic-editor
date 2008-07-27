package cz.cvut.fel.schematicEditor.element.element.shape;

import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.support.Support;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * This class implements arc shape. It is represented by two data vectors.
 * 
 * @author Urban Kravjansky
 */
public class Arc extends Ellipse {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    /**
     * Default constructor for {@link Arc}. It contains <code>logger</code> instance initialization.
     */
    public Arc() {
        super();

        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * Constructor with parameters. First element contains start point, second contains width and
     * height, third arc start point and fourth is arc end point. Arc is drawn in counterclockwise
     * direction.
     * 
     * @param x
     * @param y
     */
    public Arc(Vector<Unit> x, Vector<Unit> y) {
        super(x, y);

        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * This method returns value of angle, where arc starts. If no angle is set, it returns default
     * value, which is set to 0 degrees.
     * 
     * @return Value of arc start angle.
     */
    public double getStartAngle() {
        try {
            // calculate points
            Point2D.Double arcStart = new Point2D.Double(getX().get(2).doubleValue(),
                    getY().get(2).doubleValue());
            Point2D.Double arcCenter = new Point2D.Double(
                    Support.average(getX().get(0), getX().get(1)).doubleValue(),
                    Support.average(getY().get(0), getY().get(1)).doubleValue());
            Point2D.Double arcZero = new Point2D.Double(arcCenter.getX() + 1, arcCenter.getY());

            // cosine law
            double angle = cosineLaw(arcStart, arcCenter, arcZero);

            // bottom half correction
            if (arcCenter.getY() < arcStart.getY()) {
                angle = -angle;
            }

            return angle;
        } catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
    }

    /**
     * This method returns value of angle of arc. If no angle is set, it returns default value,
     * which is set to 360 degrees.
     * 
     * @return Value of arc angle.
     */
    public double getArcAngle() {
        try {
            // calculate points
            Point2D.Double arcEnd = new Point2D.Double(getX().get(3).doubleValue(),
                    getY().get(3).doubleValue());
            Point2D.Double arcCenter = new Point2D.Double(
                    Support.average(getX().get(0), getX().get(1)).doubleValue(),
                    Support.average(getY().get(0), getY().get(1)).doubleValue());
            Point2D.Double arcZero = new Point2D.Double(arcCenter.getX() + 1, arcCenter.getY());

            // cosine law
            double angle = cosineLaw(arcZero, arcCenter, arcEnd);
            logger.debug("angle: " + angle);

            // bottom half correction
            if (arcCenter.getY() < arcEnd.getY()) {
                angle = 360 - angle;
                logger.debug("corrected angle: " + angle);
            }
            // upper half correction
            else {
                angle = 360 + angle;
                logger.debug("corrected angle: " + angle);
            }

            return (angle - getStartAngle()) % 360;
        } catch (ArrayIndexOutOfBoundsException e) {
            return 360;
        }
    }

    private double cosineLaw(Point2D.Double a, Point2D.Double b, Point2D.Double c) {
        // calculate distances between points
        double j = Support.distance(b, c);
        double k = Support.distance(b, a);
        double l = Support.distance(a, c);

        // cosine law
        return Math.acos((j * j + k * k - l * l) / (2 * j * k)) / Math.PI * 180;
    }

    /*
     * (non-Javadoc)
     * @see element.Element#isHit(java.awt.geom.cz.cvut.fel.schematicEditor.types.Point2D.Double)
     */
    @Override
    public boolean isHit(Rectangle2D.Double rectangle) {
        Arc2D.Double a2d = new Arc2D.Double(getTopLeftX(), getTopLeftY(), getWidth(), getHeight(),
                getStartAngle(), getArcAngle(), Arc2D.PIE);
        if (a2d.intersects(rectangle) || a2d.contains(rectangle)) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     * @see cz.cvut.fel.schematicEditor.element.Element#getElementType()
     */
    @Override
    public int getElementType() {
        return ElementType.T_ARC;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.Element#getNumberOfCoordinates()
     */
    @Override
    public int getNumberOfCoordinates() {
        // TODO Auto-generated method stub
        return 4;
    }

    /*
     * (non-Javadoc)
     * @see cz.cvut.fel.schematicEditor.element.shape.Ellipse#newInstance()
     */
    @Override
    public Element newInstance() {
        Arc a = new Arc();
        return a;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.shape.Ellipse#toString()
     */
    @Override
    public String toString() {
        return "ARC";
    }
}
