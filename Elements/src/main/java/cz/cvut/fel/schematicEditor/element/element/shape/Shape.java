package cz.cvut.fel.schematicEditor.element.element.shape;

import java.util.Vector;

import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;

public abstract class Shape extends Element {

    /**
     * 
     */
    public Shape() {
        super();
    }

    /**
     * @param x
     * @param y
     */
    public Shape(Vector<Unit> x, Vector<Unit> y) {
        super(x, y);
    }

    public Shape(UnitPoint a, UnitPoint b) {
        super(a, b);
    }

}
