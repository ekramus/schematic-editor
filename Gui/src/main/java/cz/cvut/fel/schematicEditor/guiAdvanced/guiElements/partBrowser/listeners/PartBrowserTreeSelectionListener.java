package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser.listeners;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;

/**
 * @author Urban Kravjansky
 *
 */
public class PartBrowserTreeSelectionListener implements TreeSelectionListener {

    /**
     * @see javax.swing.event.TreeSelectionListener#valueChanged(javax.swing.event.TreeSelectionEvent)
     */
    public void valueChanged(TreeSelectionEvent e) {
        JTree tree = (JTree) e.getSource();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (node != null) {
            JOptionPane.showMessageDialog(Gui.getInstance(), node.getUserObject());
        }
    }

}
