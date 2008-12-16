package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cz.cvut.fel.schematicEditor.configuration.EnvironmentConfiguration;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.guiAdvanced.ExportFileFilter;
import cz.cvut.fel.schematicEditor.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;

/**
 * This class implements {@link ActionListener} for <code>importMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class AddPartMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public AddPartMenuItemListener() {
        super();
    }

    // FIXME add externalized strings

    /**
     * Method invoked as result to an action. It initializes new {@link JFileChooser} instance to select file and then
     * executes export process.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param e {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(ActionEvent e) {
        EnvironmentConfiguration env = EnvironmentConfiguration.getInstance();

        JFileChooser fileChooser = new JFileChooser(env.getLastImportFolder());
        fileChooser.setDialogTitle("Choose part to add");
        fileChooser.setFileFilter(new ExportFileFilter(ExportFileFilter.PRT, ExportFileFilter.PRTDESC));

        int retValue = fileChooser.showOpenDialog(GuiAdvanced.getActiveScenePanel());

        if (retValue == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            env.setLastImportFolder(file.getParent());

            // prepare PartNode, GroupNode and ParameterNode
            PartNode partNode = deserialize(PartNode.class, file);
            GroupNode groupNode = new GroupNode();
            ParameterNode parameterNode = new ParameterNode();

            groupNode.add(partNode);
            groupNode.add(parameterNode);

            // finally add to SceneGraph
            GuiAdvanced.getActiveScenePanel().getSceneGraph().getTopNode().add(groupNode);
            GuiAdvanced.getActiveScenePanel().getSceneGraph().fireSceneGraphUpdateEvent();
            GuiAdvanced.getActiveScenePanel().schemeInvalidate(null);
        }
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
