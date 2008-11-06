package cz.cvut.fel.schematicEditor.element.element.part;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This class encapsulates wire part. Wire has special parameters needed for electronic circuits.
 *
 * @author Urban Kravjansky
 */
public class Wire extends Element {

    /**
     * Default {@link Wire} constructor.
     */
    public Wire() {
        super();
    }

    /**
     * {@link Wire} constructor with coordinate vector.
     *
     * @param x {@link Vector} of <code>x</code> coordinates.
     * @param y {@link Vector} of <code>y</code> coordinates.
     */
    public Wire(Vector<Unit> x, Vector<Unit> y) {
        super(x, y);
    }

    /**
     * @see Element#getBounds()
     */
    @Override
    public UnitRectangle getBounds() {
        double top = Double.MAX_VALUE;
        double bottom = Double.MIN_VALUE;
        double left = Double.MAX_VALUE;
        double right = Double.MIN_VALUE;

        for (Unit u : getY()) {
            double d = u.doubleValue();
            top = (d < top) ? d : top;
            bottom = (d > bottom) ? d : bottom;
        }
        for (Unit u : getX()) {
            double d = u.doubleValue();
            left = (d < left) ? d : left;
            right = (d > right) ? d : right;
        }

        return new UnitRectangle(left - 2, top - 2, right - left + 5, bottom - top + 5);
    }

    /**
     * @see Element#isHit(Rectangle2D.Double)
     */
    @Override
    public boolean isHit(Rectangle2D.Double rectangle) {
        Iterator<Unit> itX = this.getX().iterator();
        Iterator<Unit> itY = this.getY().iterator();

        double x1 = itX.next().doubleValue();
        double y1 = itY.next().doubleValue();

        while (itX.hasNext()) {
            double x2 = itX.next().doubleValue();
            double y2 = itY.next().doubleValue();
            // check, whether rectangle intersects line
            Line2D.Double l2d = new Line2D.Double(x1, y1, x2, y2);
            if (l2d.intersects(rectangle)) {
                return true;
            }
            x1 = x2;
            y1 = y2;
        }
        return false;
    }

    /**
     * @see Element#getElementType()
     */
    @Override
    public ElementType getElementType() {
        return ElementType.T_WIRE;
    }

    /**
     * @see Element#getNumberOfCoordinates()
     */
    @Override
    public int getNumberOfCoordinates() {
        // TODO Auto-generated method stub
        return Element.INFINITE_COORDINATES;
    }

    /**
     * @see Element#duplicate()
     */
    @Override
    public Element duplicate() {
        Wire wire = new Wire();

        wire.duplicateCoordinates(getX(), getY());

        return wire;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "WIRE";
    }
}
