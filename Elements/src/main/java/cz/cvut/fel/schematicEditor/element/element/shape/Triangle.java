package cz.cvut.fel.schematicEditor.element.element.shape;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
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
     * @param a triangle point.
     * @param b triangle point.
     * @param c triangle point.
     */
    public Triangle(UnitPoint a, UnitPoint b, UnitPoint c) {
        super();

        getX().add(a.getUnitX());
        getY().add(a.getUnitY());
        getX().add(b.getUnitX());
        getY().add(b.getUnitY());
        getX().add(c.getUnitX());
        getY().add(c.getUnitY());
        getX().add(a.getUnitX());
        getY().add(a.getUnitY());
    }

    /**
     *
     */
    public Triangle() {
        // TODO Auto-generated constructor stub
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "TRIA(" + getX() + ":" + getY() + ")";
    }

    /*
     * (non-Javadoc)
     *
     * @see cz.cvut.fel.schematicEditor.element.shape.Polyline#getElementType()
     */
    @Override
    public ElementType getElementType() {
        return ElementType.T_TRIANGLE;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.shape.Polyline#getNumberOfCoordinates()
     */
    @Override
    public int getNumberOfCoordinates() {
        // TODO Auto-generated method stub
        return 3;
    }

    /*
     * (non-Javadoc)
     *
     * @see cz.cvut.fel.schematicEditor.element.shape.Polyline#newInstance()
     */
    @Override
    public Element duplicate() {
        Triangle t = new Triangle();

        t.duplicateCoordinates(this);

        return t;
    }
}
