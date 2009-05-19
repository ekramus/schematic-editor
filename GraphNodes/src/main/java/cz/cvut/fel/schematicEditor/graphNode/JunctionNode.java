package cz.cvut.fel.schematicEditor.graphNode;

import cz.cvut.fel.schematicEditor.element.element.part.Junction;

/**
 * This class represents Junction Node.
 *
 * @author Urban Kravjansky
 */
public class JunctionNode extends ElementNode {
    /**
     * This is constructor.
     *
     * @param junction {@link Junction} stored in this {@link JunctionNode}.
     */
    public JunctionNode(Junction junction) {
        super(junction);
    }

    /**
     * This is constructor.
     *
     * @param junction {@link Junction} stored in this {@link JunctionNode}.
     * @param id identifier of this {@link JunctionNode}.
     */
    public JunctionNode(Junction junction, String id) {
        super(junction, id);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.graphNode.Node#duplicate()
     */
    @Override
    public Node duplicate() {
        JunctionNode result = new JunctionNode((Junction) getElement());

        return result;
    }
}
