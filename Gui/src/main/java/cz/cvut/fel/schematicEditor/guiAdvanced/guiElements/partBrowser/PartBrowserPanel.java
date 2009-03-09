package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser;

import java.awt.BorderLayout;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Urban Kravjansky
 *
 */
public class PartBrowserPanel extends JPanel {
    /**
     * {@link PartBrowserPanel} singleton instance.
     */
    private static PartBrowserPanel instance = null;

    private PartBrowserPanel() {
        setLayout(new BorderLayout());

        DefaultMutableTreeNode top = new DefaultMutableTreeNode("hokus");
        JTree tree = new JTree(top);

        for (String file : getFiles()) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(file);
            top.add(node);
        }

        JScrollPane sp = new JScrollPane(tree);
        add(sp, BorderLayout.CENTER);
    }

    /**
     * @return the instance
     */
    public static PartBrowserPanel getInstance() {
        if (instance == null) {
            instance = new PartBrowserPanel();
        }
        return instance;
    }

    private Vector<String> getFiles() {
        Vector<String> result = new Vector<String>();

        result.add("asdasd");
        result.add("asdggfhgfh");
        result.add("asdas345");
        result.add("67frht");

        return result;
    }
}
