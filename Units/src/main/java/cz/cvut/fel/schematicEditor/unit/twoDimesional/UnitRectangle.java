package cz.cvut.fel.schematicEditor.unit.twoDimesional;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * This class implements Rectangle defined by two {@link UnitPoint} instances (one top left coordinate, one dimension
 * coordinate).
 *
 * @author Urban Kravjansky
 *
 */
@XStreamAlias("UnitRectangle")
public class UnitRectangle extends Rectangle2D {
    /**
     * Top left rectangle coordinate.
     */
    private UnitPoint topLeft;
    /**
     * Dimensions rectangle coordinate.
     */
    private UnitPoint dim;

    /**
     * Default constructor. It initializes {@link UnitRectangle} with default values.
     */
    public UnitRectangle() {
        setTopLeft(new UnitPoint());
        setDim(new UnitPoint());
    }

    /**
     * Constructor with given {@link Rectangle2D}. It initializes new {@link UnitRectangle} with given
     * {@link Rectangle2D}.
     *
     * @param r2d {@link Rectangle2D} to duplicate.
     */
    public UnitRectangle(final Rectangle2D r2d) {
        setTopLeft(new UnitPoint(r2d.getX(), r2d.getY()));
        setDim(new UnitPoint(r2d.getWidth(), r2d.getHeight()));
    }

    /**
     * Constructor with given 4 {@link UnitPoint}s. It initializes new {@link UnitRectangle} with {@link Rectangle2D}
     * containing these 4 points.
     *
     * @param a 1st point.
     * @param b 2nd point.
     * @param c 3rd point.
     * @param d 4th point.
     */
    public UnitRectangle(final UnitPoint a, final UnitPoint b, final UnitPoint c, final UnitPoint d) {
        Rectangle2D.Double r2d = new Rectangle2D.Double();

        r2d.add(new Point2D.Double(a.getX(), a.getY()));
        r2d.add(new Point2D.Double(b.getX(), b.getY()));
        r2d.add(new Point2D.Double(c.getX(), c.getY()));
        r2d.add(new Point2D.Double(d.getX(), d.getY()));

        setTopLeft(new UnitPoint(r2d.getX(), r2d.getY()));
        setDim(new UnitPoint(r2d.getWidth(), r2d.getHeight()));
    }

    /**
     * Constructor with given rectangle coordinates.
     *
     * @param x rectangle <code>x</code> coordinate.
     * @param y rectangle <code>y</code> coordinate.
     * @param width rectangle <code>width</code>.
     * @param height rectangle <code>height</code>.
     */
    public UnitRectangle(final double x, final double y, final double width, final double height) {
        setTopLeft(new UnitPoint(x, y));
        setDim(new UnitPoint(width, height));
    }

    /**
     * @param r rectangle for intersection.
     * @return intersection result.
     *
     * @see java.awt.geom.Rectangle2D#createIntersection(java.awt.geom.Rectangle2D)
     */
    @Override
    public final Rectangle2D createIntersection(final Rectangle2D r) {
        Rectangle2D.Double r2d = new Rectangle2D.Double(getTopLeft().getX(), getTopLeft().getY(), getDim().getX(),
                getDim().getY());

        UnitRectangle result = new UnitRectangle(r2d.createIntersection(r));
        return result;
    }

    /**
     * @param r rectangle for union.
     * @return union result.
     *
     * @see java.awt.geom.Rectangle2D#createUnion(java.awt.geom.Rectangle2D)
     */
    @Override
    public final Rectangle2D createUnion(final Rectangle2D r) {
        Rectangle2D.Double r2d = new Rectangle2D.Double(getTopLeft().getX(), getTopLeft().getY(), getDim().getX(),
                getDim().getY());

        if (r == null) {
            return r2d;
        }

        UnitRectangle result = new UnitRectangle(r2d.createUnion(r));
        return result;
    }

    /**
     * @param x <code>x</code> coordinate for outcode.
     * @param y <code>y</code> coordinate for outcode.
     * @return outcode result.
     *
     * @see java.awt.geom.Rectangle2D#outcode(double, double)
     */
    @Override
    public final int outcode(final double x, final double y) {
        Rectangle2D.Double r2d = new Rectangle2D.Double(getTopLeft().getX(), getTopLeft().getY(), getDim().getX(),
                getDim().getY());

        return r2d.outcode(x, y);
    }

    /**
     * @param x <code>x</code> coordinate of rectangle.
     * @param y <code>y</code> coordinate of rectangle.
     * @param w <code>width</code> of rectangle.
     * @param h <code>height</code> of rectangle.
     *
     * @see java.awt.geom.Rectangle2D#setRect(double, double, double, double)
     */
    @Override
    public final void setRect(final double x, final double y, final double w, final double h) {
        setTopLeft(new UnitPoint(x, y));
        setDim(new UnitPoint(x, y));
    }

    /**
     * @return <code>height</code> of rectangle.
     *
     * @see java.awt.geom.RectangularShape#getHeight()
     */
    @Override
    public final double getHeight() {
        return getDim().getY();
    }

    /**
     * @return <code>width</code> of rectangle.
     *
     * @see java.awt.geom.RectangularShape#getWidth()
     */
    @Override
    public final double getWidth() {
        return getDim().getX();
    }

    /**
     * @return <code>x</code> coordinate of rectangle.
     *
     * @see java.awt.geom.RectangularShape#getX()
     */
    @Override
    public final double getX() {
        return getTopLeft().getX();
    }

    /**
     * @return <code>y</code> coordinate of rectangle.
     *
     * @see java.awt.geom.RectangularShape#getY()
     */
    @Override
    public final double getY() {
        return getTopLeft().getY();
    }

    /**
     * @return is enclosed area empty?
     *
     * @see java.awt.geom.RectangularShape#isEmpty()
     */
    @Override
    public final boolean isEmpty() {
        Rectangle2D.Double r2d = new Rectangle2D.Double(getTopLeft().getX(), getTopLeft().getY(), getDim().getX(),
                getDim().getY());

        return r2d.isEmpty();
    }

    /**
     * Getter for <code>topLeft</code> {@link UnitRectangle} coordinate.
     *
     * @return the topLeft
     */
    public final UnitPoint getTopLeft() {
        return this.topLeft;
    }

    /**
     * Getter for <code>topRight</code> {@link UnitRectangle} coordinate.
     *
     * @return the topRight
     */
    public final UnitPoint getTopRight() {
        return new UnitPoint(getTopLeft().getX() + getWidth(), getTopLeft().getY());
    }

    /**
     * Getter for <code>bottomLeft</code> {@link UnitRectangle} coordinate.
     *
     * @return the bottomLeft
     */
    public final UnitPoint getBottomLeft() {
        return new UnitPoint(getTopLeft().getX(), getTopLeft().getY() + getHeight());
    }

    /**
     * Getter for <code>bottomRight</code> {@link UnitRectangle} coordinate.
     *
     * @return the bottomRight
     */
    public final UnitPoint getBottomRight() {
        return new UnitPoint(getTopLeft().getX() + getWidth(), getTopLeft().getY() + getHeight());
    }

    /**
     * Setter of <code>topLeft</code> {@link UnitRectangle} coordinate.
     *
     * @param topLeft the topLeft to set
     */
    public final void setTopLeft(final UnitPoint topLeft) {
        this.topLeft = topLeft;
    }

    /**
     * Getter for <code>dim</code> of {@link UnitRectangle}.
     *
     * @return the dim
     */
    public final UnitPoint getDim() {
        return this.dim;
    }

    /**
     * Setter of <code>dim</code> of {@link UnitRectangle}.
     *
     * @param dim the dim to set
     */
    public final void setDim(final UnitPoint dim) {
        this.dim = dim;
    }
}
