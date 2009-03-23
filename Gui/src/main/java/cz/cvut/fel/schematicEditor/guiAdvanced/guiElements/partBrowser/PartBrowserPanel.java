package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cz.cvut.fel.schematicEditor.graphNode.PartNode;
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
    private PartNode                selectedPartNode = null;

    private PartBrowserPanel() {
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
        result = new DefaultMutableTreeNode(folderName.replaceAll("/", ""));

        // recursively add folders and parts
        File folder = new File(path);
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                result.add(generatePartTree(file.getPath()));
            } else if (file.getName().indexOf("prt") > -1) {
                PartNode pn = deserialize(PartNode.class, file);
                ImageIcon icon = new ImageIcon(file.getPath().replaceAll("prt", "png"));

                PartBrowserNode pbn = new PartBrowserNode();
                pbn.setPartNode(pn);
                pbn.setIcon(icon);

                DefaultMutableTreeNode node = new DefaultMutableTreeNode(pbn);

                result.add(node);
            }
        }

        return result;
    }

    /**
     * Deserializes {@link PartNode} from given file.
     *
     * @param clazz Class of deserialized {@link PartNode}.
     * @param file Path to file, where is serialized {@link PartNode}.
     * @return Deserialized {@link PartNode} class.
     */
    protected static PartNode deserialize(Class<? extends PartNode> clazz, File file) {
        XStream xstream = new XStream(new DomDriver());

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            processAnnotations(xstream, clazz);
            return (PartNode) xstream.fromXML(br);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Processes all {@link XStream} annotations in entered classes.
     *
     * @param xstream {@link XStream} instance to configure.
     * @param clazz Class of {@link PartNode} object.
     */
    private static void processAnnotations(XStream xstream, Class<? extends PartNode> clazz) {
        xstream.processAnnotations(clazz);
    }
}
