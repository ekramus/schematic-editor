package cz.cvut.fel.schematicEditor.element.element.shape;

import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

public class BezierCurve extends Shape {

    public BezierCurve() {
        super();
    }

    public BezierCurve(Vector<Unit> x, Vector<Unit> y) {
        super(x, y);
    }

    /**
     * @see element.Element#getBounds()
     */
    @Override
    public UnitRectangle getBounds(Graphics2D g2d) {
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

    /*
     * (non-Javadoc)
     *
     * @see element.Element#isHit(java.awt.geom.cz.cvut.fel.schematicEditor.types.Point2D.Double)
     */
    @Override
    public boolean isHit(Rectangle2D rectangle) {
        // check, whether is not within limit
        CubicCurve2D cc2d = new CubicCurve2D.Double(getStart().getX(), getStart().getY(), getControl1().getX(),
                getControl1().getY(), getControl2().getX(), getControl2().getY(), getEnd().getX(), getEnd().getY());

        if (cc2d.intersects(rectangle)) {
            return true;
        }
        return false;
    }

    public UnitPoint getStart() {
        return new UnitPoint(getX().get(0), getY().get(0));
    }

    public UnitPoint getEnd() {
        return new UnitPoint(getX().get(1), getY().get(1));
    }

    public UnitPoint getControl1() {
        try {
            return new UnitPoint(getX().get(2), getY().get(2));
        } catch (ArrayIndexOutOfBoundsException e) {
            return new UnitPoint(getX().get(0), getY().get(0));
        }
    }

    public UnitPoint getControl2() {
        try {
            return new UnitPoint(getX().get(3), getY().get(3));
        } catch (ArrayIndexOutOfBoundsException e) {
            return new UnitPoint(getX().get(1), getY().get(1));
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see cz.cvut.fel.schematicEditor.element.Element#getElementType()
     */
    @Override
    public ElementType getElementType() {
        return ElementType.T_BEZIER;
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
     *
     * @see cz.cvut.fel.schematicEditor.element.Element#newInstance()
     */
    @Override
    public Element duplicate() {
        BezierCurve bc = new BezierCurve();

        bc.duplicateCoordinates(this);

        return bc;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "BEZIERE";
    }
}
