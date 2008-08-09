package cz.cvut.fel.schematicEditor.element.element.shape;

import java.awt.geom.Point2D;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This class represents line.
 * 
 * @author uk
 */
public class Line extends Polyline {

    /**
     * Default constructor for manipulation purposes.
     */
    public Line() {
        super();

        // nothing else to do, just empty Line element.
    }

    /**
     * This is constructor.
     * 
     * @param a starting point.
     * @param b ending point.
     */
    public Line(UnitPoint a, UnitPoint b) {
        super();

        this.x.add(a.getUnitX());
        this.y.add(a.getUnitY());
        this.x.add(b.getUnitX());
        this.y.add(b.getUnitY());
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "LINE";
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.element.shape.Polyline#getElementType()
     */
    @Override
    public int getElementType() {
        return ElementType.T_LINE;
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
        Line l = new Line();

        l.duplicateCoordinates(getX(), getY());

        return l;
    }
}
