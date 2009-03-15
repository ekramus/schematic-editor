package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser;

import java.awt.BorderLayout;
import java.io.File;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser.listeners.PartBrowserTreeSelectionListener;

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
        tree.addTreeSelectionListener(new PartBrowserTreeSelectionListener());

        for (String file : getFiles()) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(file);
            top.add(node);
        }

        top.add(generatePartTree("parts"));

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

    /**
     * Generates part tree based on folder.
     *
     * @param path
     * @return
     */
    private DefaultMutableTreeNode generatePartTree(String path) {
        DefaultMutableTreeNode result;

        // strip folder from path
        String folderName;
        try {
            folderName = path.substring(path.lastIndexOf(System.getProperty("file.separator")));
        } catch (IndexOutOfBoundsException e) {
            folderName = path;
        }
        // create root node with folder name
        result = new DefaultMutableTreeNode(folderName);

        // recursively add folders and parts
        File folder = new File(path);
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                result.add(generatePartTree(file.getPath()));
            } else {
                result.add(new DefaultMutableTreeNode(file.getName()));
            }
        }

        return result;
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
