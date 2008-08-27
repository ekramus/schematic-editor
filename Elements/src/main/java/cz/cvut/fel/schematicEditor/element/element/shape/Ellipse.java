package cz.cvut.fel.schematicEditor.element.element.shape;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

public class Ellipse extends Shape {

    public Ellipse() {
        super();
    }

    public Ellipse(Vector<Unit> x, Vector<Unit> y) {
        super(x, y);
    }

    public Ellipse(UnitPoint topLeft, UnitPoint bottomRight) {
        super(topLeft, bottomRight);
    }

    /**
     * @see element.Element#getBounds()
     */
    @Override
    public UnitRectangle getBounds() {
        return new UnitRectangle(getTopLeftX() - 2, getTopLeftY() - 2, getWidth() + 5, getHeight() + 5);
    }

    /*
     * (non-Javadoc)
     * 
     * @see element.Element#isHit(java.awt.geom.cz.cvut.fel.schematicEditor.types.Point2D.Double)
     */
    @Override
    public boolean isHit(Rectangle2D.Double rectangle) {
        // check, whether is not within limit
        Ellipse2D e2d = new Ellipse2D.Double(getTopLeftX(), getTopLeftY(), getWidth(), getHeight());

        if (e2d.intersects(rectangle) || e2d.contains(rectangle)) {
            return true;
        }
        return false;
    }

    public double getTopLeftX() {
        // x is in right half
        if (getX().get(1).doubleValue() - getX().get(0).doubleValue() > 0) {
            return getX().get(0).doubleValue();
        }
        // x is in left half
        return getX().get(0).doubleValue() - getWidth();
    }

    public double getTopLeftY() {
        // y is in bottom half
        if (getY().get(1).doubleValue() - getY().get(0).doubleValue() > 0) {
            return getY().get(0).doubleValue();
        }
        // y is in upper half
        return getY().get(0).doubleValue() - getHeight();
    }

    public double getWidth() {
        double w = Math.abs(getX().get(1).doubleValue() - getX().get(0).doubleValue());
        double h = Math.abs(getY().get(1).doubleValue() - getY().get(0).doubleValue());

        // normalize both
        if (getElementModificator() == ElementModificator.SYMMETRIC_ELEMENT) {
            w = (w < h) ? w : h;
        }

        return w;
    }

    public double getHeight() {
        double w = Math.abs(getX().get(1).doubleValue() - getX().get(0).doubleValue());
        double h = Math.abs(getY().get(1).doubleValue() - getY().get(0).doubleValue());

        // normalize both
        if (getElementModificator() == ElementModificator.SYMMETRIC_ELEMENT) {
            h = (w < h) ? w : h;
        }

        return h;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.Element#getElementType()
     */
    @Override
    public ElementType getElementType() {
        return ElementType.T_ELLIPSE;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.Element#getNumberOfCoordinates()
     */
    @Override
    public int getNumberOfCoordinates() {
        return 2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.element.Element#newInstance()
     */
    @Override
    public Element duplicate() {
        Ellipse e = new Ellipse();

        e.duplicateCoordinates(getX(), getY());

        return e;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ELLIPSE";
    }
}
