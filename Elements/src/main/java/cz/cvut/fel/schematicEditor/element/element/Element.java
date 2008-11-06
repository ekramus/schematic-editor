package cz.cvut.fel.schematicEditor.element.element;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.element.ElementType;
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
    /**
     * Original value of edited coordinate.
     */
    private UnitPoint          editedPointOriginalValue;
    /**
     * Vector of <code>x</code> coordinates of element.
     */
    private Vector<Unit>       x;
    /**
     * Vector of <code>y</code> coordinates of element.
     */
    private Vector<Unit>       y;
    /**
     * Instance of {@link ElementModificator} class. It indicates special element modifications (e.g. symmetric element
     * which in case of ellipse results into circle).
     */
    private ElementModificator elementModificator;

    /**
     * Default constructor. It instantiates new {@link Element} instance with default values set.
     */
    public Element() {
        setX(new Vector<Unit>());
        setY(new Vector<Unit>());
        setElementModificator(ElementModificator.NO_MODIFICATION);
    }

    /**
     * Constructor with coordinates. It instantiates new {@link Element} instance with coordinates set.
     *
     * @param x Vector of x coordinates.
     * @param y Vector of y coordinates.
     */
    public Element(Vector<Unit> x, Vector<Unit> y) {
        setX(x);
        setY(y);
    }

    /**
     * Constructor with coordinates. It instantiates new {@link Element} instance with two coordinates (usefull e.g. for
     * line, circle, etc..).
     *
     * @param a first element coordinate.
     * @param b second element coordinate.
     */
    public Element(UnitPoint a, UnitPoint b) {
        setX(new Vector<Unit>());
        setY(new Vector<Unit>());

        getX().add(a.getUnitX());
        getX().add(b.getUnitX());
        getY().add(a.getUnitY());
        getY().add(b.getUnitY());
    }

    /**
     * Getter for <code>x</code> coordinates.
     *
     * @return the {@link Vector} of <code>x</code> coordinates.
     */
    public Vector<Unit> getX() {
        return this.x;
    }

    /**
     * Getter for <code>y</code> coordinates.
     *
     * @return the {@link Vector} of <code>y</code> coordinates.
     */
    public Vector<Unit> getY() {
        return this.y;
    }

    /**
     * Setter for <code>x</code> coordinates.
     *
     * @param x {@link Vector} of <code>c</code> coordinates.
     */
    public void setX(Vector<Unit> x) {
        this.x = x;
    }

    /**
     * Setter for <code>y</code> coordinates.
     *
     * @param y {@link Vector} of <code>c</code> coordinates.
     */
    public void setY(Vector<Unit> y) {
        this.y = y;
    }

    /**
     * Calculates and then returns bounds of the element. This bound does not need to be necesserilly the closest.
     *
     * @return Bounds of element.
     */
    public abstract UnitRectangle getBounds();

    /**
     * Indicates, whether given rectangle hits this element.
     *
     * @param r2d hit rectangle to check.
     * @return Status of hit.
     */
    public abstract boolean isHit(Rectangle2D.Double r2d);

    /**
     * Checks, whether given rectangle is in edit zone or not. In case it is, this <code>element</code> is returned.
     *
     * @param r2d hit rectangle to check.
     * @return Pointer to <em>this</em>, if hit, otherwise <em>null</em>.
     */
    public Element startEdit(Rectangle2D.Double r2d) {
        for (int i = 0; i < getX().size(); i++) {
            Point2D.Double p = new Point2D.Double(getX().elementAt(i).doubleValue(), getY().elementAt(i).doubleValue());
            if (r2d.contains(p)) {
                setEditedPointIndex(i);
                setEditedPointOriginalValue(new UnitPoint(getX().get(getEditedPointIndex()), getY()
                        .get(getEditedPointIndex())));
                return this;
            }
        }

        setEditedPointIndex(-1);
        return null;
    }

    /**
     * Getter for element type. Element type is one of {@link ElementType}.
     *
     * @return type of element.
     */
    public abstract ElementType getElementType();

    /**
     * Getter for number of coordinates needed to create given {@link Element}.
     *
     * @return Number of coordinates necessary to create given element.
     */
    public abstract int getNumberOfCoordinates();

    /**
     * Getter for <code>elementModificator</code>.
     *
     * @return the elementModificator
     */
    public ElementModificator getElementModificator() {
        return this.elementModificator;
    }

    /**
     * @param elementModificator the elementModificator to set
     */
    public void setElementModificator(ElementModificator elementModificator) {
        this.elementModificator = elementModificator;
    }

    public abstract Element duplicate();

    /**
     * @param delta
     */
    public void stopEdit(UnitPoint delta) {
        double x = getEditedPointOriginalValue().getX();
        double y = getEditedPointOriginalValue().getY();

        x += delta.getX();
        y += delta.getY();

        getX().set(getEditedPointIndex(), new Pixel(x));
        getY().set(getEditedPointIndex(), new Pixel(y));

        // setEditedPointIndex(-1);
        // setEditedPointOriginalValue(null);
    }

    /**
     * @param delta
     */
    public void switchEdit(UnitPoint delta) {
        double x = getEditedPointOriginalValue().getX();
        double y = getEditedPointOriginalValue().getY();

        x += delta.getX();
        y += delta.getY();

        getX().set(getEditedPointIndex(), new Pixel(x));
        getY().set(getEditedPointIndex(), new Pixel(y));
    }

    /**
     * @return the editedPointIndex
     */
    public int getEditedPointIndex() {
        return this.editedPointIndex;
    }

    /**
     * @param editedPointIndex the editedPointIndex to set
     */
    private void setEditedPointIndex(int editedPointIndex) {
        this.editedPointIndex = editedPointIndex;
    }

    /**
     * @param editedPointOriginalValue the editedPointOriginalValue to set
     */
    private void setEditedPointOriginalValue(UnitPoint editedPointOriginalValue) {
        this.editedPointOriginalValue = editedPointOriginalValue;
    }

    /**
     * @return the editedPointOriginalValue
     */
    private UnitPoint getEditedPointOriginalValue() {
        return this.editedPointOriginalValue;
    }

    /**
     * Duplicates coordinates of
     *
     * @param element
     */
    protected void duplicateCoordinates(Vector<Unit> x, Vector<Unit> y) {
        for (int i = 0; i < x.size(); i++) {
            getX().add(new Pixel(x.get(i).doubleValue()));
            getY().add(new Pixel(y.get(i).doubleValue()));
        }
    }
}
