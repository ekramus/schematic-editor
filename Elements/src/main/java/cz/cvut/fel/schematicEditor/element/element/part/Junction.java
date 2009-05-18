package cz.cvut.fel.schematicEditor.element.element.part;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This class encapsulates junction part. Junction serves as bridge between two {@link Wire}s.
 *
 * @author Urban Kravjansky
 */
public class Junction extends Element {

    /**
     * Default {@link Junction} constructor.
     */
    public Junction() {
        super();
    }

    /**
     * {@link Junction} constructor with coordinate vector.
     *
     * @param x {@link Vector} of <code>x</code> coordinates.
     * @param y {@link Vector} of <code>y</code> coordinates.
     */
    public Junction(Vector<Unit> x, Vector<Unit> y) {
        super(x, y);
    }

    /**
     * @see Element#getBounds()
     */
    @Override
    public UnitRectangle getBounds() {
        double x = getX().firstElement().doubleValue();
        double y = getY().firstElement().doubleValue();

        return new UnitRectangle(x - 2, y - 2, 5, 5);
    }

    /**
     * @see Element#isHit(Rectangle2D)
     */
    @Override
    public boolean isHit(Rectangle2D rectangle) {
        double x = getX().firstElement().doubleValue();
        double y = getY().firstElement().doubleValue();

        Point2D.Double p2d = new Point2D.Double(x, y);
        return rectangle.contains(p2d);
    }

    /**
     * @see Element#getElementType()
     */
    @Override
    public ElementType getElementType() {
        return ElementType.T_JUNCTION;
    }

    /**
     * @see Element#getNumberOfCoordinates()
     */
    @Override
    public int getNumberOfCoordinates() {
        // TODO Auto-generated method stub
        return 1;
    }

    /**
     * @see Element#duplicate()
     */
    @Override
    public Element duplicate() {
        Junction junction = new Junction();

        junction.duplicateCoordinates(this);

        return junction;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "JUNCTION";
    }
}
