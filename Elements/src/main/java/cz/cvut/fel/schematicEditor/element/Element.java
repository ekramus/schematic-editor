package cz.cvut.fel.schematicEditor.element;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This is abstract class representing all elements.
 * 
 * @author uk
 */
public abstract class Element {
    /**
     * Infinite number of coordinates.
     */
    public static final int    INFINITE_COORDINATES = -1;
    /**
     * Zero number of coordinates.
     */
    public static final int    ZERO_COORDINATES     = 0;
    /**
     * Index of edited point.
     */
    private int                editedPointIndex     = 0;

    protected Vector<Unit>     x;
    protected Vector<Unit>     y;

    private ElementModificator elementModificator;

    /**
     * This is the default constructor.
     */
    public Element() {
        this.x = new Vector<Unit>();
        this.y = new Vector<Unit>();
        setElementModificator(ElementModificator.NO_MODIFICATION);
    }

    /**
     * This is the constructor.
     * 
     * @param x
     *            Vector of x coordinates.
     * @param y
     *            Vecotr of y coordinates.
     */
    public Element(Vector<Unit> x, Vector<Unit> y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @param a
     * @param b
     */
    public Element(UnitPoint a, UnitPoint b) {
        Vector<Unit> xv = new Vector<Unit>();
        Vector<Unit> yv = new Vector<Unit>();

        xv.add(a.getUnitX());
        yv.add(a.getUnitY());
        xv.add(b.getUnitX());
        yv.add(b.getUnitY());
    }

    /**
     * @return the x
     */
    public Vector<Unit> getX() {
        return this.x;
    }

    /**
     * @return the y
     */
    public Vector<Unit> getY() {
        return this.y;
    }

    public void setX(Vector<Unit> x) {
        this.x = x;
    }

    public void setY(Vector<Unit> y) {
        this.y = y;
    }

    /**
     * This method calculates and then returns bounds of element. This bound does not need to be necesserilly the
     * closest.
     * 
     * @return Bounds of element.
     */
    public abstract UnitRectangle getBounds();

    /**
     * This method indicates, whether given point hits this element.
     * 
     * @param point
     *            hit point to check.
     * @return Status of hit.
     */
    public abstract boolean isHit(Rectangle2D.Double r2d);

    /**
     * Indicates, whether given rectangle is in edit zone or not.
     */
    public boolean startEditing(Rectangle2D.Double r2d) {
        for (int i = 0; i < getX().size(); i++) {
            Point2D.Double p = new Point2D.Double(getX().elementAt(i).doubleValue(), getY().elementAt(i).doubleValue());
            if (r2d.contains(p)) {
                setEditedPointIndex(i);
                return true;
            }
        }

        setEditedPointIndex(-1);
        return false;
    }

    public abstract int getElementType();

    /**
     * This method returns number of coordinates needed to create given {@link Element}.
     * 
     * @return Number of coordinates neccesarry to create given element.
     */
    public abstract int getNumberOfCoordinates();

    /**
     * @return the elementModificator
     */
    public ElementModificator getElementModificator() {
        return this.elementModificator;
    }

    /**
     * @param elementModificator
     *            the elementModificator to set
     */
    public void setElementModificator(ElementModificator elementModificator) {
        this.elementModificator = elementModificator;
    }

    public abstract Element newInstance();

    /**
     * @param delta
     */
    public void stopEditing(UnitPoint delta) {
        double x = getX().get(getEditedPointIndex()).doubleValue();
        double y = getY().get(getEditedPointIndex()).doubleValue();

        x += delta.getX();
        y += delta.getY();

        getX().set(getEditedPointIndex(), new Pixel(x));
        getY().set(getEditedPointIndex(), new Pixel(y));

        setEditedPointIndex(-1);
    }

    /**
     * @return the editedPointIndex
     */
    private int getEditedPointIndex() {
        return this.editedPointIndex;
    }

    /**
     * @param editedPointIndex
     *            the editedPointIndex to set
     */
    private void setEditedPointIndex(int editedPointIndex) {
        this.editedPointIndex = editedPointIndex;
    }

}
