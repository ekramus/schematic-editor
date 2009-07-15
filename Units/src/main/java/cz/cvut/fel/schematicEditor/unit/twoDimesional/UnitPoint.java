package cz.cvut.fel.schematicEditor.unit.twoDimesional;

import java.awt.geom.Point2D;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.computer.Pixel;

/**
 * This class represents point in two dimensional space, where coordinates are stored in form of {@link Unit}.
 *
 * @author Urban Kravjansky
 *
 */
@XStreamAlias("UnitPoint")
public class UnitPoint extends Point2D implements Comparable<UnitPoint> {
    /**
     * Point {@link Unit} x coordinate.
     */
    private Unit unitX;
    /**
     * Point {@link Unit} y coordinate.
     */
    private Unit unitY;

    /**
     * Default constructor. It initializes new {@link UnitPoint} instance with zero coordinates.
     */
    public UnitPoint() {
        setUnitX(new Pixel(0));
        setUnitY(new Pixel(0));
    }

    /**
     * Constructor with coordinate parameters. It initializes new {@link UnitPoint} instance with given coordinates.
     *
     * @param x <code>x</code> coordinate of point.
     * @param y <code>y</code> coordinate of point.
     */
    public UnitPoint(final Unit x, final Unit y) {
        setUnitX(x);
        setUnitY(y);
    }

    /**
     * Constructor with coordinate parameter. It initializes new {@link UnitPoint} instance with given coordinate.
     *
     * @param p {@link Point2D} defined coordinates of point.
     */
    public UnitPoint(Point2D p) {
        setUnitX(new Pixel(p.getX()));
        setUnitY(new Pixel(p.getY()));
    }

    /**
     * Constructor with coordinate parameter. It initializes new {@link UnitPoint} instance with given coordinate.
     *
     * @param p {@link UnitPoint} defined coordinates of point.
     */
    public UnitPoint(UnitPoint p) {
        setUnitX(p.getUnitX().duplicate());
        setUnitY(p.getUnitY().duplicate());
    }

    /**
     * Constructor with coordinate parameters. It initializes new {@link UnitPoint} instance with given double
     * coordinates.
     *
     * @param x <code>x</code> coordinate of point.
     * @param y <code>y</code> coordinate of point.
     */
    public UnitPoint(final double x, final double y) {
        setUnitX(new Pixel(x));
        setUnitY(new Pixel(y));
    }

    /**
     * Returns the X coordinate of this <code>Point2D</code> in <code>double</code> precision.
     *
     * @return the X coordinate of this <code>Point2D</code>.
     */
    @Override
    public final double getX() {
        return getUnitX().doubleValue();
    }

    /**
     * Returns the Y coordinate of this <code>Point2D</code> in <code>double</code> precision.
     *
     * @return the Y coordinate of this <code>Point2D</code>.
     * @since 1.2
     */
    @Override
    public final double getY() {
        return getUnitY().doubleValue();
    }

    /**
     * Sets the location of this <code>Point2D</code> to the specified <code>double</code> coordinates.
     *
     * @param x the new X coordinate of this {@code Point2D}
     * @param y the new Y coordinate of this {@code Point2D}
     * @since 1.2
     */
    @Override
    public final void setLocation(final double x, final double y) {
        setUnitX(new Pixel(x));
        setUnitY(new Pixel(y));
    }

    /**
     * Getter for {@link Unit} <code>x</code> coordinate.
     *
     * @return <code>x</code> coordinate {@link Unit} value.
     */
    public final Unit getUnitX() {
        return this.unitX;
    }

    /**
     * Setter of {@link Unit} <code>x</code> coordinate.
     *
     * @param unitX <code>x</code> coordinate.
     */
    public final void setUnitX(final Unit unitX) {
        this.unitX = unitX;
    }

    /**
     * Getter for {@link Unit} <code>y</code> coordinate.
     *
     * @return <code>y</code> coordinate {@link Unit} value.
     */
    public final Unit getUnitY() {
        return this.unitY;
    }

    /**
     * Setter of {@link Unit} <code>y</code> coordinate.
     *
     * @param unitY <code>y</code> coordinate.
     */
    public final void setUnitY(final Unit unitY) {
        this.unitY = unitY;
    }

    /**
     * Sets the X coordinate of this {@link UnitPoint} in <code>double</code> precision.
     *
     * @param x the X coordinate of this {@link UnitPoint}.
     */
    public final void setX(final double x) {
        getUnitX().setDouble(x);
    }

    /**
     * Sets the Y coordinate of this {@link UnitPoint} in <code>double</code> precision.
     *
     * @param y the Y coordinate of this {@link UnitPoint}.
     */
    public final void setY(final double y) {
        getUnitY().setDouble(y);
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "[" + getUnitX() + "," + getUnitY() + "]";
    }

    /**
     * @param up
     * @return
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(UnitPoint up) {
        // first compare in X axis
        if (getUnitX().compareTo(up.getUnitX()) < 0) {
            return -1;
        } else if (getUnitX().compareTo(up.getUnitX()) > 0) {
            return 1;
        }
        // now compare in Y axis
        else if (getUnitY().compareTo(up.getUnitY()) < 0) {
            return -1;
        } else if (getUnitY().compareTo(up.getUnitY()) > 0) {
            return 1;
        }
        // both unit points are the same
        else {
            return 0;
        }
    }
}
