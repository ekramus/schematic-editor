package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser.listeners.AddButtonActionListener;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser.listeners.PartBrowserTreeSelectionListener;

/**
 * @author Urban Kravjansky
 *
 */
public class PartBrowserPanel extends JPanel {
    /**
     * {@link PartBrowserPanel} singleton instance.
     */
    private static PartBrowserPanel instance         = null;

    private static JButton          addButton        = null;
    private String                  selectedPartPath = null;

    private PartBrowserPanel() {
        setLayout(new BorderLayout());

        DefaultMutableTreeNode top = generatePartTree("parts");
        JTree tree = new JTree(top);
        tree.addTreeSelectionListener(new PartBrowserTreeSelectionListener());

        JScrollPane sp = new JScrollPane(tree);

        add(sp, BorderLayout.CENTER);
        add(getAddButton(), BorderLayout.SOUTH);
    }

    public static JButton getAddButton() {
        if (addButton == null) {
            addButton = new JButton("Add");
            addButton.addActionListener(new AddButtonActionListener());
            addButton.setEnabled(false);
        }
        return addButton;
    }

    public String getSelectedPartPath() {
        return this.selectedPartPath;
    }

    public void setSelectedPartPath(String selectedPartPath) {
        this.selectedPartPath = selectedPartPath;
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
}
