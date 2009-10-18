package cz.cvut.fel.schematicEditor.original.graphNode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cz.cvut.fel.schematicEditor.element.element.part.Junction;

/**
 * This class represents Junction Node.
 *
 * @author Urban Kravjansky
 */
@XStreamAlias("JunctionNode")
public class JunctionNode extends ElementNode {
    /**
     * This is constructor.
     *
     * @param junction {@link Junction} stored in this {@link JunctionNode}.
     */
    protected JunctionNode(Junction junction) {
        super(junction);
    }

    /**
     * This is constructor.
     *
     * @param junction {@link Junction} stored in this {@link JunctionNode}.
     * @param id identifier of this {@link JunctionNode}.
     */
    protected JunctionNode(Junction junction, String id) {
        super(junction, id);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.original.graphNode.Node#duplicate()
     */
    @Override
    protected Node duplicate() {
        JunctionNode result = new JunctionNode((Junction) getElement());

        return result;
    }
}
