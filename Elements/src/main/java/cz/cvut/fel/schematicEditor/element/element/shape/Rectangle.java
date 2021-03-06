package cz.cvut.fel.schematicEditor.element.element.shape;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
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
    *
    */
    public double getTopLeftX() {
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
    public double getTopLeftY() {
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
    public double getWidth() {
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
     * @see Polygon#isHit(Rectangle2D, Graphics2D)
     */
    @Override
    public boolean isHit(Rectangle2D rectangle, Graphics2D g2d) {
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
