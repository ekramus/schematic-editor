package cz.cvut.fel.schematicEditor.graphNode;

import cz.cvut.fel.schematicEditor.element.element.Element;

/**
 * This class represents Part Node.
 * 
 * @author uk
 */
public class PartNode extends ElementNode {

    /**
     * @param element
     */
    public PartNode(Element element) {
        super(element);
        // TODO Auto-generated constructor stub
    }

    /**
     * @see cz.cvut.fel.schematicEditor.graphNode.Node#duplicate()
     */
    @Override
    public Node duplicate() {
        PartNode result = new PartNode(getElement());

        return result;
    }
}
