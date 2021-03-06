package cz.cvut.fel.schematicEditor.element.element.shape;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * This class represents polygon element.
 *
 * @author Urban Kravjansky
 */
public class Polygon extends Polyline {
    private static Logger logger;

    /**
     * This is default constructor.
     */
    public Polygon() {
        super();

        logger = Logger.getLogger(Polygon.class.getName());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.shape.Polyline#getElementType()
     */
    @Override
    public ElementType getElementType() {
        return ElementType.T_POLYGON;
    }

    /**
     * @see Polyline#isHit(Rectangle2D, Graphics2D)
     */
    @Override
    public boolean isHit(Rectangle2D rectangle, Graphics2D g2d) {
        java.awt.Polygon p = new java.awt.Polygon();

        Vector<Unit> xPg = getX();
        Vector<Unit> yPg = getY();

        for (int i = 0; i < xPg.size(); i++) {
            p.addPoint(xPg.get(i).intValue(), yPg.get(i).intValue());
        }

        if (p.intersects(rectangle) || p.contains(rectangle)) {
            return true;
        }
        return false;
    }

    /*
     * (non-Javadoc)
     *
     * @see cz.cvut.fel.schematicEditor.element.shape.Polyline#newInstance()
     */
    @Override
    public Element duplicate() {
        Polygon p = new Polygon();

        p.duplicateCoordinates(this);

        return p;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.shape.Polyline#toString()
     */
    @Override
    public String toString() {
        return "POLYGON";
    }
}
