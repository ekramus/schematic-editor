package cz.cvut.fel.schematicEditor.graphNode;

import cz.cvut.fel.schematicEditor.element.element.shape.Shape;

/**
 * This class represents Shape Node.
 * 
 * @author uk
 */
public class ShapeNode extends ElementNode {
    /**
     * This is constructor.
     * 
     * @param shape
     *            <code>Shape</code> stored in this <code>ShapeNode</code>.
     */
    public ShapeNode(Shape shape) {
        super(shape);
    }

    /**
     * This is constructor.
     * 
     * @param shape
     *            <code>Shape</code> stored in this <code>ShapeNode</code>.
     * @param id
     *            identifier of this <code>ShapeNode</code>.
     */
    public ShapeNode(Shape shape, String id) {
        super(shape, id);
    }

}
