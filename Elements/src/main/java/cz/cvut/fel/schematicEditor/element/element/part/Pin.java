package cz.cvut.fel.schematicEditor.element.element.part;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This class encapsulates pin part. Pin serves as bridge between {@link Wire} and {@link Part}. It is visible in
 * application, exported is invisible.
 *
 * @author Urban Kravjansky
 */
public class Pin extends Element {

    /**
     * Default {@link Pin} constructor.
     */
    public Pin() {
        super();
    }

    /**
     * {@link Pin} constructor with coordinate vector.
     *
     * @param x {@link Vector} of <code>x</code> coordinates.
     * @param y {@link Vector} of <code>y</code> coordinates.
     */
    public Pin(Vector<Unit> x, Vector<Unit> y) {
        super(x, y);
    }

    /**
     * @see Element#getBounds()
     */
    @Override
    public UnitRectangle getBounds(Graphics2D g2d) {
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
        return ElementType.T_PIN;
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
        Pin pin = new Pin();

        pin.duplicateCoordinates(this);

        return pin;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PIN";
    }
}
