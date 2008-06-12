package cz.cvut.fel.schematicEditor.element.part;

import java.awt.geom.Rectangle2D;

import cz.cvut.fel.schematicEditor.element.Element;
import cz.cvut.fel.schematicEditor.types.ElementType;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

public class Part extends Element {

    public UnitRectangle getBounds() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see element.Element#isHit(java.awt.geom.cz.cvut.fel.schematicEditor.types.Point2D.Double)
     */
    @Override
    public boolean isHit(Rectangle2D.Double rectangle) {
        // TODO Auto-generated method stub
        return false;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.Element#getElementType()
     */
    @Override
    public int getElementType() {
        return ElementType.T_PART;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.Element#getNumberOfCoordinates()
     */
    @Override
    public int getNumberOfCoordinates() {
        // TODO Auto-generated method stub
        return Element.INFINITE_COORDINATES;
    }
}
