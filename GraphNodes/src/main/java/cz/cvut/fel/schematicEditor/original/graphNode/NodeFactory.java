package cz.cvut.fel.schematicEditor.original.graphNode;

import cz.cvut.fel.schematicEditor.element.element.Element;
import cz.cvut.fel.schematicEditor.element.element.part.Junction;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.element.element.part.Pin;
import cz.cvut.fel.schematicEditor.element.element.part.Wire;
import cz.cvut.fel.schematicEditor.element.element.shape.Shape;
import cz.cvut.fel.schematicEditor.support.Serializable;
import cz.cvut.fel.schematicEditor.support.Transformation;

/**
 * This class implements factory pattern for {@link Node} creation.
 *
 * @author Urban Kravjansky
 */
public class NodeFactory implements Serializable {
    private static NodeFactory instance = null;

    /**
     * Default constructor, for future implementation purposes only. It is private to force static use only.
     */
    private NodeFactory() {
        // nothing to do
    }

    /**
     * @return the instance
     */
    public static NodeFactory getInstance() {
        if (NodeFactory.instance == null) {
            NodeFactory.instance = new NodeFactory();
        }
        return NodeFactory.instance;
    }

    public static ElementNode createElementNode(Element element) {
        return new ElementNode(element);
    }

    public static GroupNode createGroupNode() {
        return new GroupNode();
    }

    public static JunctionNode createJunctionNode(Junction junction) {
        return new JunctionNode(junction);
    }

    public static ParameterNode createParameterNode() {
        return new ParameterNode();
    }

    public static PartNode createPartNode(Part part, GroupNode partGroupNode) {
        return new PartNode(part, partGroupNode);
    }

    public static PinNode createPinNode(Pin pin) {
        return new PinNode(pin);
    }

    public static ShapeNode createShapeNode(Shape shape) {
        return new ShapeNode(shape);
    }

    public static TransformationNode createTransformationNode(Transformation t) {
        return new TransformationNode(t);
    }

    public static WireNode createWireNode(Wire wire) {
        return new WireNode(wire);
    }

    /**
     * Duplicates given {@link Node}.
     *
     * @param node {@link Node} instance to be duplicated.
     * @return New duplicated {@link Node}.
     */
    public static Node duplicate(Node node) {
        return node.duplicate();
    }

    /**
     * @see Serializable#getClassArray()
     */
    public Class[] getClassArray() {
        Class[] result = new Class[10];

        result[0] = ElementNode.class;
        result[1] = GroupNode.class;
        result[2] = JunctionNode.class;
        result[3] = Node.class;
        result[4] = ParameterNode.class;
        result[5] = PartNode.class;
        result[6] = PinNode.class;
        result[7] = ShapeNode.class;
        result[8] = TransformationNode.class;
        result[9] = WireNode.class;

        return result;
    }
}
