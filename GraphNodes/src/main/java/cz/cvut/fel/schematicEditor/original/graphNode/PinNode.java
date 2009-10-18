package cz.cvut.fel.schematicEditor.original.graphNode;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import cz.cvut.fel.schematicEditor.element.element.part.Pin;

/**
 * This class represents Pin Node.
 *
 * @author Urban Kravjansky
 */
@XStreamAlias("PinNode")
public class PinNode extends ElementNode {
    /**
     * This is constructor.
     *
     * @param pin {@link Pin} stored in this {@link PinNode}.
     */
    protected PinNode(Pin pin) {
        super(pin);
    }

    /**
     * This is constructor.
     *
     * @param pin {@link Pin} stored in this {@link PinNode}.
     * @param id identifier of this {@link PinNode}.
     */
    protected PinNode(Pin pin, String id) {
        super(pin, id);
    }

    /**
     * @see cz.cvut.fel.schematicEditor.original.graphNode.Node#duplicate()
     */
    @Override
    protected Node duplicate() {
        PinNode result = new PinNode((Pin) getElement());

        return result;
    }
}
