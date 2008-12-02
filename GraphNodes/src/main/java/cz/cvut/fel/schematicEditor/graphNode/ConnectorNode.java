package cz.cvut.fel.schematicEditor.graphNode;

import cz.cvut.fel.schematicEditor.element.element.part.Pin;

/**
 * This class represents Pin Node.
 *
 * @author Urban Kravjansky
 */
public class ConnectorNode extends ElementNode {
    /**
     * This is constructor.
     *
     * @param pin
     *            {@link Pin} stored in this {@link ConnectorNode}.
     */
    public ConnectorNode(Pin pin) {
        super(pin);
    }

    /**
     * This is constructor.
     *
     * @param pin
     *            {@link Pin} stored in this {@link ConnectorNode}.
     * @param id
     *            identifier of this {@link ConnectorNode}.
     */
    public ConnectorNode(Pin pin, String id) {
        super(pin, id);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.graphNode.Node#duplicate()
     */
    @Override
    public Node duplicate() {
        ConnectorNode result = new ConnectorNode((Pin) getElement());

        return result;
    }
}
