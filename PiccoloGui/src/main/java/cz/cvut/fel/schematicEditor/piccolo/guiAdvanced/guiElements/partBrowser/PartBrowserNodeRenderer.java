package cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.partBrowser;

import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 * @author Urban Kravjansky
 *
 */
public class PartBrowserNodeRenderer extends DefaultTreeCellRenderer {
    /**
     * @see javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent(javax.swing.JTree, java.lang.Object,
     *      boolean, boolean, boolean, int, boolean)
     */
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded,
            boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        try {
            if (leaf) {
                DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
                PartBrowserNode pbn = (PartBrowserNode) node.getUserObject();

                setIcon(pbn.getIcon());
                setText(pbn.getVariant() + " - " + pbn.getDescription());
            }
        } catch (ClassCastException e) {
            // nothing to do
        }

        return this;
    }
}
