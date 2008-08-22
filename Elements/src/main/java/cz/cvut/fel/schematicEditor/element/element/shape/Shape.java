package cz.cvut.fel.schematicEditor.element.element.shape;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

/**
 * This is abstract superclass for all shapes used in schematic editor.
 * 
 * @author Urban Kravjansky
 */
public abstract class Shape extends Element {

    /**
     * Default constructor. It only serves as facade for calling superclass constructor.
     */
    public Shape() {
        super();
    }

    /**
     * Constructor with coordinate vector. It only serves as facade for calling superclass constructor.
     * 
     * @param x <code>x</code> coordinate {@link Vector}
     * @param y <code>y</code> coordinate {@link Vector}
     */
    public Shape(Vector<Unit> x, Vector<Unit> y) {
        super(x, y);
    }

    /**
     * Constructor with two coordinates. It only serves as facade for calling superclass constructor.
     * 
     * @param a first coordinate {@link UnitPoint}
     * @param b second coordinate {@link UnitPoint}
     */
    public Shape(UnitPoint a, UnitPoint b) {
        super(a, b);
    }

}
