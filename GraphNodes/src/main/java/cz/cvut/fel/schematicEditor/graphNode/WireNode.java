package cz.cvut.fel.schematicEditor.graphNode;

import cz.cvut.fel.schematicEditor.element.element.Element;

/**
 * This class represents Wire Node.
 * 
 * @author uk
 */
public class WireNode extends ElementNode {

    /**
     * @param element
     */
    public WireNode(Element element) {
        super(element);
        // TODO Auto-generated constructor stub
    }

    /**
     * @see cz.cvut.fel.schematicEditor.graphNode.Node#duplicate()
     */
    @Override
    public Node duplicate() {
        WireNode result = new WireNode(getElement());

        return result;
    }
}
