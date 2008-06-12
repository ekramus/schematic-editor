package cz.cvut.fel.schematicEditor.element.shape;

import java.awt.geom.Point2D;

import cz.cvut.fel.schematicEditor.types.ElementType;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class represents triangle.
 * 
 * @author uk
 */
public class Triangle extends Polygon {
    /**
     * This is constructor.
     * 
     * @param a
     *            triangle point.
     * @param b
     *            triangle point.
     * @param c
     *            triangle point.
     */
    public Triangle(UnitPoint a, UnitPoint b, UnitPoint c) {
        super();

        this.x.add(a.getUnitX());
        this.y.add(a.getUnitY());
        this.x.add(b.getUnitX());
        this.y.add(b.getUnitY());
        this.x.add(c.getUnitX());
        this.y.add(c.getUnitY());
        this.x.add(a.getUnitX());
        this.y.add(a.getUnitY());
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TRIA(" + x + ":" + y + ")";
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.element.shape.Polyline#getElementType()
     */
    @Override
    public int getElementType() {
        return ElementType.T_TRIANGLE;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.shape.Polyline#getNumberOfCoordinates()
     */
    @Override
    public int getNumberOfCoordinates() {
        // TODO Auto-generated method stub
        return 3;
    }
}
