package cz.cvut.fel.schematicEditor.graphNode;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.element.element.shape.Arc;
import cz.cvut.fel.schematicEditor.element.element.shape.ArcSegment;
import cz.cvut.fel.schematicEditor.element.element.shape.BezierCurve;
import cz.cvut.fel.schematicEditor.element.element.shape.Ellipse;
import cz.cvut.fel.schematicEditor.element.element.shape.Line;
import cz.cvut.fel.schematicEditor.element.element.shape.Polygon;
import cz.cvut.fel.schematicEditor.element.element.shape.Polyline;
import cz.cvut.fel.schematicEditor.element.element.shape.Rectangle;
import cz.cvut.fel.schematicEditor.element.element.shape.Text;
import cz.cvut.fel.schematicEditor.element.element.shape.Triangle;
import cz.cvut.fel.schematicEditor.support.Transformation;
import cz.cvut.fel.schematicEditor.unit.oneDimensional.Unit;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitPoint;
import cz.cvut.fel.schematicEditor.unit.twoDimesional.UnitRectangle;

/**
 * This class represents Element Node in scene graph.
 * 
 * @author uk
 */
@XStreamAlias("ElementNode")
public class ElementNode extends Node {
    /**
     * This field represents <code>element</code> of this node.
     */
    private Element     element;
    /**
     * Type of element.
     */
    private ElementType elementType;
    /**
     * Indicates edit state.
     */
    private boolean     edited;
    /**
     * Point, which is being edited.
     */
    private UnitPoint   editedPoint;

    /**
     * This is constructor.
     * 
     * @param element
     *            <code>Element</code> to create <code>ElementNode</code> with.
     */
    protected ElementNode(Element element) {
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
    protected ElementNode(Element element, String id) {
        super(id);
        initialize(element);
    }

    /**
     * This method is used to initialize element.
     * 
     * @param element
     */
    private void initialize(Element element) {
        // TODO complete rewrite
        this.element = element.duplicate();
        if (element instanceof Rectangle) {
            setElementType(ElementType.T_RECTANGLE);
        } else if (element instanceof Triangle) {
            setElementType(ElementType.T_TRIANGLE);
        } else if (element instanceof Polygon) {
            setElementType(ElementType.T_POLYGON);
        } else if (element instanceof Line) {
            setElementType(ElementType.T_LINE);
        } else if (element instanceof Polyline) {
            setElementType(ElementType.T_POLYLINE);
        } else if (element instanceof ArcSegment) {
            setElementType(ElementType.T_ARC_SEGMENT);
        } else if (element instanceof Arc) {
            setElementType(ElementType.T_ARC);
        } else if (element instanceof BezierCurve) {
            setElementType(ElementType.T_BEZIER);
        } else if (element instanceof Ellipse) {
            setElementType(ElementType.T_ELLIPSE);
        } else if (element instanceof Text) {
            setElementType(ElementType.T_TEXT);
        } else if (element instanceof Part) {
            setElementType(ElementType.T_PART);
        }
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String result = this.id + "[ElementNode]";
        return result;
    }

    /**
     * Getter for element bounds.
     * 
     * @param boundModifier
     *            modifier which affects boundary size of element.
     * @param Graphics2D
     *            context.
     * @return Bounds of element.
     */
    public UnitRectangle getBounds(Unit boundModifier, Graphics2D g2d) {
        double x = getElement().getBounds(g2d).getX() - boundModifier.doubleValue();
        double y = getElement().getBounds(g2d).getY() - boundModifier.doubleValue();
        double w = getElement().getBounds(g2d).getWidth() + 2 * boundModifier.doubleValue();
        double h = getElement().getBounds(g2d).getHeight() + 2 * boundModifier.doubleValue();

        return new UnitRectangle(x, y, w, h);
    }

    /**
     * Getter for <code>element</code>.
     * 
     * @return the element
     */
    public Element getElement() {
        return this.element;
    }

    protected boolean isHit(Rectangle2D r2d, Graphics2D g2d) {
        if (isDisabled()) {
            return false;
        }

        // check element coordinates
        for (int i = 0; i < this.element.getX().size(); i++) {
            if (r2d.contains(this.element.getX().get(i).doubleValue(),
                             this.element.getY().get(i).doubleValue())) {
                return true;
            }
        }

        // check element itself
        return this.element.isHit(r2d, g2d);
    }

    protected Element startEdit(Rectangle2D.Double r2d) {
        if (isDisabled()) {
            return null;
        }
        setEdited(true);
        return getElement().startEdit(r2d);
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
    protected void setEdited(boolean edited) {
        this.edited = edited;
    }

    /**
     *
     */
    protected void stopEdit(UnitPoint delta) {
        if (isEdited()) {
            getElement().setEditedCoordinate(delta);
            setEdited(false);
        }
    }

    /**
     *
     */
    protected void switchEdit(UnitPoint delta) {
        if (isEdited()) {
            getElement().setEditedCoordinate(delta);
        }
    }

    /**
     * @see cz.cvut.fel.schematicEditor.graphNode.Node#duplicate()
     */
    @Override
    protected Node duplicate() {
        ElementNode result = new ElementNode(getElement());

        return result;
    }

    /**
     * @param elementType
     *            the elementType to set
     */
    private void setElementType(ElementType elementType) {
        this.elementType = elementType;
    }

    /**
     * Modifies coordinates according to given {@link Transformation}.
     * 
     * @param t
     *            {@link Transformation} to apply.
     */
    public void modifyCoordinates(Transformation t) {
        Element element = getElement();

        for (int i = 0; i < element.getX().size(); i++) {
            UnitPoint up = new UnitPoint(element.getX().get(i), element.getY().get(i));
            up = Transformation.multiply(t, up);

            element.getX().set(i, up.getUnitX());
            element.getY().set(i, up.getUnitY());
        }
    }

    /**
     * Getter for rotation center of element stored in element node.
     * 
     * @return {@link UnitPoint} of rotation center.
     */
    public UnitPoint getRotationCenter() {
        return getElement().getRotationCenter();
    }
}
