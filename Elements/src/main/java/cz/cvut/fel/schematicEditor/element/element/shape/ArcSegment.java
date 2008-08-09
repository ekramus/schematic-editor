package cz.cvut.fel.schematicEditor.element.element.shape;

import java.awt.geom.Arc2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Vector;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;

/**
 * This class implements arc segment shape. It is represented by two data vectors.
 * 
 * @author Urban Kravjansky
 */
public class ArcSegment extends Arc {
    /**
     * {@link Logger} instance for logging purposes.
     */
    private static Logger logger;

    /**
     * Default constructor for {@link ArcSegment}. It contains <code>logger</code> instance initialization.
     */
    public ArcSegment() {
        super();

        logger = Logger.getLogger(ArcSegment.class.getName());
    }

    /**
     * Constructor with parameters. First element contains start point, second contains width and height, third arc
     * start point and fourth is arc end point. Arc is drawn in counterclockwise direction.
     * 
     * @param x
     * @param y
     */
    public ArcSegment(Vector<Unit> x, Vector<Unit> y) {
        super(x, y);

        logger = Logger.getLogger(ArcSegment.class.getName());
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.Element#getElementType()
     */
    @Override
    public int getElementType() {
        return ElementType.T_ARC_SEGMENT;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.Element#getNumberOfCoordinates()
     */
    @Override
    public int getNumberOfCoordinates() {
        // TODO Auto-generated method stub
        return 4;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cz.cvut.fel.schematicEditor.element.shape.Ellipse#newInstance()
     */
    @Override
    public Element duplicate() {
        ArcSegment as = new ArcSegment();

        as.duplicateCoordinates(getX(), getY());

        return as;
    }

    /**
     * @see cz.cvut.fel.schematicEditor.element.element.shape.Ellipse#toString()
     */
    @Override
    public String toString() {
        return "ARC_SEGMENT";
    }
}
