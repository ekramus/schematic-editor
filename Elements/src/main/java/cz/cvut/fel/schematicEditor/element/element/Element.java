package cz.cvut.fel.schematicEditor.element.element;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.ElementModificator;
import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.support.Transformation;
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
    public static final int    INFINITE_COORDINATES  = -1;
    /**
     * Zero number of coordinates.
     */
    public static final int    ZERO_COORDINATES      = 0;
    /**
     * Index of edited coordinate.
     */
    private int                editedCoordinateIndex = 0;
    /**
     * Original value of edited coordinate.
     */
    private UnitPoint          editedCoordinateOriginalValue;
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
    public abstract boolean isHit(Rectangle2D r2d);

    /**
     * Checks, whether given rectangle is in edit zone or not. In case it is, this <code>element</code> is returned.
     *
     * @param r2d hit rectangle to check.
     * @return Pointer to <em>this</em>, if hit, otherwise <em>null</em>.
     */
    public Element startEdit(Rectangle2D r2d) {
        for (int i = 0; i < getX().size(); i++) {
            Point2D.Double p = new Point2D.Double(getX().elementAt(i).doubleValue(), getY().elementAt(i).doubleValue());
            if (r2d.contains(p)) {
                setEditedCoordinateIndex(i);
                setEditedCoordinateOriginalValue(new UnitPoint(getX().get(getEditedCoordinateIndex()), getY()
                        .get(getEditedCoordinateIndex())));
                return this;
            }
        }

        setEditedCoordinateIndex(-1);
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
     * Setter for <code>elementModificator</code>.
     *
     * @param elementModificator the elementModificator to set
     */
    public void setElementModificator(ElementModificator elementModificator) {
        this.elementModificator = elementModificator;
    }

    /**
     * Duplicates element.
     *
     * @return Duplicated element.
     */
    public abstract Element duplicate();

    /**
     * Sets edited coordinate to new value relative to original by delta.
     *
     * @param delta delta against the original coordinates.
     */
    public void setEditedCoordinate(UnitPoint delta) {
        double x = getEditedCoordinateOriginalValue().getX();
        double y = getEditedCoordinateOriginalValue().getY();

        x += delta.getX();
        y += delta.getY();

        getX().set(getEditedCoordinateIndex(), new Pixel(x));
        getY().set(getEditedCoordinateIndex(), new Pixel(y));
    }

    /**
     * Getter for <code>editedCoordinateIndex</code>.
     *
     * @return the index of edited coordinate in coordinates vector.
     */
    public int getEditedCoordinateIndex() {
        return this.editedCoordinateIndex;
    }

    /**
     * Setter for <code>editedCoordinateIndex</code>.
     *
     * @param editedCoordinateIndex the index of edited coordinate in coordinates vector.
     */
    private void setEditedCoordinateIndex(int editedCoordinateIndex) {
        this.editedCoordinateIndex = editedCoordinateIndex;
    }

    /**
     * Setter for <code>editedCoordinateOriginalValue</code>.Fs
     *
     * @param editedCoordinateOriginalValue the original value of edited coordinate to set.
     */
    private void setEditedCoordinateOriginalValue(UnitPoint editedCoordinateOriginalValue) {
        this.editedCoordinateOriginalValue = editedCoordinateOriginalValue;
    }

    /**
     * @return the editedPointOriginalValue
     */
    private UnitPoint getEditedCoordinateOriginalValue() {
        return this.editedCoordinateOriginalValue;
    }

    /**
     * Duplicates coordinates of given element instance.
     *
     * @param element {@link Element} instance, which coordinates are to duplicate.
     */
    protected void duplicateCoordinates(Element element) {
        setX(new Vector<Unit>());
        setY(new Vector<Unit>());

        for (int i = 0; i < element.getX().size(); i++) {
            getX().add(new Pixel(element.getX().get(i).doubleValue()));
            getY().add(new Pixel(element.getY().get(i).doubleValue()));
        }
    }

    /**
     * Transforms original element coordinate into corrected coordinate with all transformations applied.
     *
     * @param t {@link Transformation} to apply.
     * @param x <code>x</code> coordinate.
     * @param y <code>y</code> coordinate.
     * @return Corrected coordinate.
     */
    private UnitPoint transformCoordinate(Transformation t, Unit x, Unit y) {
        UnitPoint result = new UnitPoint();

        result = new UnitPoint(x, y);
        result = Transformation.multiply(t, result);

        return result;
    }

}
