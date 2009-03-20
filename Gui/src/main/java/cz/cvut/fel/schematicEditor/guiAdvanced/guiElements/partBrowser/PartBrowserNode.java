package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser;

import javax.swing.Icon;

import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;

/**
 * This class incorporates part browser node. It contains reference to part node and part specifying icon.
 *
 * @author Urban Kravjansky
 *
 */
public class PartBrowserNode {
    /**
     * Icon of this node.
     */
    private Icon     icon;
    /**
     * {@link PartNode} defining this node.
     */
    private PartNode partNode;

    /**
     * @param icon the icon to set
     */
    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    /**
     * @return the icon
     */
    public Icon getIcon() {
        return this.icon;
    }

    /**
     * @param partNode the partNode to set
     */
    public void setPartNode(PartNode partNode) {
        this.partNode = partNode;
    }

    /**
     * @return the partNode
     */
    public PartNode getPartNode() {
        return this.partNode;
    }

    /**
     * @return the partVariant
     */
    public String getVariant() {
        return ((Part) getPartNode().getElement()).getPartProperties().getPartVariant();
    }

    /**
     * @return the partDescription
     */
    public String getDescription() {
        return ((Part) getPartNode().getElement()).getPartProperties().getPartDescription();
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getVariant() + " (" + getDescription() + ")";
    }
}
