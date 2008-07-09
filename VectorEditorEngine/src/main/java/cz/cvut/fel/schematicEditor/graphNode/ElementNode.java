package cz.cvut.fel.schematicEditor.graphNode;

import cz.cvut.fel.schematicEditor.element.Element;
import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.shape.Arc;
import cz.cvut.fel.schematicEditor.element.shape.ArcSegment;
import cz.cvut.fel.schematicEditor.element.shape.BezierCurve;
import cz.cvut.fel.schematicEditor.element.shape.Ellipse;
import cz.cvut.fel.schematicEditor.element.shape.Line;
import cz.cvut.fel.schematicEditor.element.shape.Polygon;
import cz.cvut.fel.schematicEditor.element.shape.Polyline;
import cz.cvut.fel.schematicEditor.element.shape.Rectangle;
import cz.cvut.fel.schematicEditor.element.shape.Text;
import cz.cvut.fel.schematicEditor.element.shape.Triangle;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This class represents Element Node in scene graph.
 * 
 * @author uk
 */
public class ElementNode extends Node {
    /**
     * This field represents <code>element</code> of this node.
     */
    private Element   element;
    /**
     * Type of element.
     */
    private int       elementType;
    /**
     * Indicates edit state.
     */
    private boolean   edited;
    /**
     * Point, which is being edited.
     */
    private UnitPoint editedPoint;

    /**
     * This is constructor.
     * 
     * @param element
     *            <code>Element</code> to create <code>ElementNode</code> with.
     */
    public ElementNode(Element element) {
        super();
        initialize(element);
    }

    /**
     * This is constructor.
     * 
     * @param element
     *            <code>Element</code> to create <code>ElementNode</code> with.
     * @param id
     *            this <code>ElementNode</code> identifier.
     */
    public ElementNode(Element element, String id) {
        super(id);
        initialize(element);
    }

    /**
     * This method is used to initialize element.
     * 
     * @param element
     */

    // by strakz1 2007 07 25
    private void initialize(Element element) {
        this.element = element;
        if (element instanceof Rectangle) {
            this.elementType = ElementType.T_RECTANGLE;
        } else if (element instanceof Triangle) {
            this.elementType = ElementType.T_TRIANGLE;
        } else if (element instanceof Polygon) {
            this.elementType = ElementType.T_POLYGON;
        } else if (element instanceof Line) {
            this.elementType = ElementType.T_LINE;
        } else if (element instanceof Polyline) {
            this.elementType = ElementType.T_POLYLINE;
        } else if (element instanceof ArcSegment) {
            this.elementType = ElementType.T_ARC_SEGMENT;
        } else if (element instanceof Arc) {
            this.elementType = ElementType.T_ARC;
        } else if (element instanceof BezierCurve) {
            this.elementType = ElementType.T_BEZIER;
        } else if (element instanceof Ellipse) {
            this.elementType = ElementType.T_ELLIPSE;
        } else if (element instanceof Text) {
            this.elementType = ElementType.T_TEXT;
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String result = id + "[ElementNode]";
        return result;
    }

    /**
     * Getter for element bounds.
     * 
     * @param boundModifier
     *            modifier which affects boundary size of element.
     * @return Bounds of element.
     */
    public UnitRectangle getBounds(Unit boundModifier) {
        double x = getElement().getBounds().getX() - boundModifier.doubleValue();
        double y = getElement().getBounds().getY() - boundModifier.doubleValue();
        double w = getElement().getBounds().getWidth() + 2 * boundModifier.doubleValue();
        double h = getElement().getBounds().getHeight() + 2 * boundModifier.doubleValue();

        return new UnitRectangle(x, y, w, h);
    }

    /**
     * Getter for <code>elementType</code>.
     * 
     * @return the elementType
     */
    public int getElementType() {
        return this.elementType;
    }

    /**
     * Getter for <code>element</code>.
     * 
     * @return the element
     */
    public Element getElement() {
        return this.element;
    }

    protected boolean isHit(Rectangle2D.Double point) {
        if (isDisabled()) {
            return false;
        }
        return element.isHit(point);
    }

    protected boolean startEditing(Rectangle2D.Double point) {
        if (isDisabled()) {
            return false;
        }
        setEdited(true);
        return getElement().startEditing(point);
    }

    /**
     * @return the editing
     */
    protected boolean isEdited() {
        return this.edited;
    }

    /**
     * @param edited
     *            the editing to set
     */
    private void setEdited(boolean edited) {
        this.edited = edited;
    }

    /**
     * 
     */
    protected void stopEditing(UnitPoint delta) {
        if (isEdited()) {
            getElement().stopEditing(delta);
            setEdited(false);
        }
    }

}
