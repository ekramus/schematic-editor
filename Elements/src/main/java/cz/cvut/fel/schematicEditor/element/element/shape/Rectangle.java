package cz.cvut.fel.schematicEditor.element.element.shape;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class represents rectangle.
 *
 * @author uk
 */
public class Rectangle extends Polygon {
    /**
     * This is constructor for manipulations.
     */
    public Rectangle() {
        super();
    }

    /**
     * This is constructor.
     *
     * @param tl top left corner.
     * @param br bottom right corner.
     */
    public Rectangle(UnitPoint tl, UnitPoint br) {
        super();

        getX().add(tl.getUnitX());
        getY().add(tl.getUnitY());
        getX().add(br.getUnitX());
        getY().add(br.getUnitY());
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "RECT";
    }

    /**
     * Computes top left point after {@link Transformation} is applied.
     *
     * @param t {@link Transformation} to apply.
     * @return Transformed top left coordinate.
     */
    public UnitPoint getTopLeft(Transformation t) {
        double resultX;
        double resultY;

        UnitPoint a = new UnitPoint(getX().get(0).doubleValue(), getY().get(0).doubleValue());
        UnitPoint b = new UnitPoint(getX().get(1).doubleValue(), getY().get(1).doubleValue());

        a = Transformation.multiply(t, a);
        b = Transformation.multiply(t, b);

        // a is in right half
        if (a.getX() < b.getX()) {
            resultX = a.getX();
        }
        // a is in left half
        else {
            resultX = b.getX();
        }

        // a is in upper half
        if (a.getY() < b.getY()) {
            resultY = a.getY();
        }
        // a is in lower half
        else {
            resultY = b.getY();
        }

        return new UnitPoint(resultX, resultY);
    }

    /**
    *
    */
    private double getTopLeftX() {
        // x is in right half
        if (getX().get(1).doubleValue() - getX().get(0).doubleValue() > 0) {
            return getX().get(0).doubleValue();
        }
        // x is in left half
        return getX().get(0).doubleValue() - getWidth();
    }

    /**
    *
    */
    private double getTopLeftY() {
        // y is in bottom half
        if (getY().get(1).doubleValue() - getY().get(0).doubleValue() > 0) {
            return getY().get(0).doubleValue();
        }
        // y is in upper half
        return getY().get(0).doubleValue() - getHeight();
    }

    /**
     *
     */
    private double getWidth() {
        double w = Math.abs(getX().get(1).doubleValue() - getX().get(0).doubleValue());
        double h = Math.abs(getY().get(1).doubleValue() - getY().get(0).doubleValue());

        // normalize both
        if (getElementModificator() == ElementModificator.SYMMETRIC_ELEMENT) {
            w = (w < h) ? w : h;
        }

        return w;
    }

    /**
    *
    */
    public UnitPoint getDim(Transformation t) {
        double w = getWidth();
        double h = getHeight();

        // convert by given tranformation
        UnitPoint result = new UnitPoint(w, h);
        // result = Transformation.multiply(t, result);

        return result;
    }

    /**
     *
     */
    private double getHeight() {
        double w = Math.abs(getX().get(1).doubleValue() - getX().get(0).doubleValue());
        double h = Math.abs(getY().get(1).doubleValue() - getY().get(0).doubleValue());

        // normalize both
        if (getElementModificator() == ElementModificator.SYMMETRIC_ELEMENT) {
            h = (w < h) ? w : h;
        }

        return h;
    }

    /*
     * (non-Javadoc)
     *
     * @see cz.cvut.fel.schematicEditor.element.shape.Polygon#isHit(java.awt.geom.Rectangle2D.Double)
     */
    @Override
    public boolean isHit(Double rectangle) {
        Rectangle2D r2d = new Rectangle2D.Double(getTopLeftX(), getTopLeftY(), getWidth(), getHeight());

        if (r2d.intersects(rectangle) || r2d.contains(rectangle)) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see cz.cvut.fel.schematicEditor.element.shape.Polyline#getElementType()
     */
    @Override
    public ElementType getElementType() {
        return ElementType.T_RECTANGLE;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.shape.Polyline#getNumberOfCoordinates()
     */
    @Override
    public int getNumberOfCoordinates() {
        return 2;
    }

    /*
     * (non-Javadoc)
     *
     * @see cz.cvut.fel.schematicEditor.element.shape.Polyline#newInstance()
     */
    @Override
    public Element duplicate() {
        Rectangle r = new Rectangle();

        r.duplicateCoordinates(this);

        return r;
    }
}
