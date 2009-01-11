package cz.cvut.fel.schematicEditor.graphNode;

import cz.cvut.fel.schematicEditor.element.element.part.Pin;

/**
 * This class represents Pin Node.
 *
 * @author Urban Kravjansky
 */
public class PinNode extends ElementNode {
    /**
     * This is constructor.
     *
     * @param pin
     *            {@link Pin} stored in this {@link PinNode}.
     */
    public PinNode(Pin pin) {
        super(pin);
    }

    /**
     * This is constructor.
     *
     * @param pin
     *            {@link Pin} stored in this {@link PinNode}.
     * @param id
     *            identifier of this {@link PinNode}.
     */
    public PinNode(Pin pin, String id) {
        super(pin, id);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.graphNode.Node#duplicate()
     */
    @Override
    public Node duplicate() {
        PinNode result = new PinNode((Pin) getElement());

        return result;
    }
}
