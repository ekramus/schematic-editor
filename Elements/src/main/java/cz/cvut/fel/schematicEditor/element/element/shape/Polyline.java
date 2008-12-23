package cz.cvut.fel.schematicEditor.element.element.shape;

import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This class represents polyline.
 *
 * @author uk
 */
public class Polyline extends Shape {

    /**
     * This is default constructor.
     */
    public Polyline() {
        super();
    }

    /**
     * This is constructor.
     *
     * @param x Vector of x coordinates.
     * @param y Vecotr of y coordinates.
     */
    public Polyline(Vector<Unit> x, Vector<Unit> y) {
        super(x, y);
    }

    public Polyline(UnitPoint a, UnitPoint b) {
        super(a, b);
    }

    /**
     * @see element.Element#getBounds()
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
     * @see element.Element#isHit(Rectangle2D.Double)
     */
    @Override
    public boolean isHit(Rectangle2D rectangle) {
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

    /*
     * (non-Javadoc)
     *
     * @see cz.cvut.fel.schematicEditor.element.Element#getElementType()
     */
    @Override
    public ElementType getElementType() {
        return ElementType.T_POLYLINE;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.Element#getNumberOfCoordinates()
     */
    @Override
    public int getNumberOfCoordinates() {
        // TODO Auto-generated method stub
        return Element.INFINITE_COORDINATES;
    }

    /*
     * (non-Javadoc)
     *
     * @see cz.cvut.fel.schematicEditor.element.Element#newInstance()
     */
    @Override
    public Element duplicate() {
        Polyline p = new Polyline();

        p.duplicateCoordinates(this);

        return p;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "POLYLINE";
    }
}
