package cz.cvut.fel.schematicEditor.original.graphNode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cz.cvut.fel.schematicEditor.element.element.shape.Shape;

/**
 * This class represents Shape Node.
 *
 * @author uk
 */
@XStreamAlias("ShapeNode")
public class ShapeNode extends ElementNode {
    /**
     * This is constructor.
     *
     * @param shape <code>Shape</code> stored in this <code>ShapeNode</code>.
     */
    protected ShapeNode(Shape shape) {
        super(shape);
    }

    /**
     * This is constructor.
     *
     * @param shape <code>Shape</code> stored in this <code>ShapeNode</code>.
     * @param id identifier of this <code>ShapeNode</code>.
     */
    protected ShapeNode(Shape shape, String id) {
        super(shape, id);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.original.graphNode.Node#duplicate()
     */
    @Override
    protected Node duplicate() {
        ShapeNode result = new ShapeNode((Shape) getElement());

        return result;
    }
}
