package cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.partBrowser;

import java.awt.BorderLayout;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.core.Serialization;
import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.original.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.partBrowser.listeners.AddButtonActionListener;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.partBrowser.listeners.PartBrowserTreeSelectionListener;

/**
 * @author Urban Kravjansky
 *
 */
public class PartBrowserPanel extends JPanel {
    /**
     * Logger instance.
     */
    private static Logger           logger;
    /**
     * {@link PartBrowserPanel} singleton instance.
     */
    private static PartBrowserPanel instance         = null;

    private static JButton          addButton        = null;
    private PartNode                selectedPartNode = null;

    private PartBrowserPanel() {
        logger = Logger.getLogger(getClass());

        setLayout(new BorderLayout());

        DefaultMutableTreeNode top = generatePartTree("parts");
        JTree tree = new JTree(top);

        tree.addTreeSelectionListener(new PartBrowserTreeSelectionListener());
        tree.setCellRenderer(new PartBrowserNodeRenderer());

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

    public PartNode getSelectedPartNode() {
        return this.selectedPartNode;
    }

    public void setSelectedPartNode(PartNode selectedPartNode) {
        this.selectedPartNode = selectedPartNode;
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
        // create root node with folder name (remove all forward slashes)
        result = new DefaultMutableTreeNode(folderName.replaceAll("[/\\\\]", ""));

        // recursively add folders and parts
        File folder = new File(path);
        try {
            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    result.add(generatePartTree(file.getPath()));
                } else if (file.getName().indexOf("prt") > -1) {
                    // deserialize
                    PartNode pn = (PartNode) Serialization.deserialize(PartNode.class, file);
                    // update part properties
                    boolean updateStatus = ((Part) pn.getElement()).getPartProperties().update();
                    logger.trace("Status of part updating process (true=updated/false=not updates): " + updateStatus);
                    // set image icon
                    ImageIcon icon = new ImageIcon(file.getPath().replaceAll("prt", "png"));

                    // create part browser node
                    PartBrowserNode pbn = new PartBrowserNode();
                    pbn.setPartNode(pn);
                    pbn.setIcon(icon);

                    // create tree node
                    DefaultMutableTreeNode node = new DefaultMutableTreeNode(pbn);

                    // add node to result
                    result.add(node);
                }
            }
        } catch (NullPointerException e) {
            logger.info("No parts folder found");
        }

        return result;
    }
}
