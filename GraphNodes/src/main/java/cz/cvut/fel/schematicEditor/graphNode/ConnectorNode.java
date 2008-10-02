package cz.cvut.fel.schematicEditor.graphNode;

import cz.cvut.fel.schematicEditor.element.element.part.Connector;

/**
 * This class represents Connector Node.
 *
 * @author Urban Kravjansky
 */
public class ConnectorNode extends ElementNode {
    /**
     * This is constructor.
     *
     * @param connector
     *            {@link Connector} stored in this {@link ConnectorNode}.
     */
    public ConnectorNode(Connector connector) {
        super(connector);
    }

    /**
     * This is constructor.
     *
     * @param connector
     *            {@link Connector} stored in this {@link ConnectorNode}.
     * @param id
     *            identifier of this {@link ConnectorNode}.
     */
    public ConnectorNode(Connector connector, String id) {
        super(connector, id);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.graphNode.Node#duplicate()
     */
    @Override
    public Node duplicate() {
        ConnectorNode result = new ConnectorNode((Connector) getElement());

        return result;
    }
}
