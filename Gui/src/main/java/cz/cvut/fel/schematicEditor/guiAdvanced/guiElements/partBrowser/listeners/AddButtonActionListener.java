package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser.PartBrowserPanel;

/**
 * @author uk
 *
 */
public class AddButtonActionListener implements ActionListener {

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        // prepare PartNode, GroupNode and ParameterNode
        PartNode partNode = deserialize(PartNode.class, new File(PartBrowserPanel.getInstance().getSelectedPartPath()));
        GroupNode groupNode = new GroupNode();
        ParameterNode parameterNode = new ParameterNode();

        groupNode.add(partNode);
        groupNode.add(parameterNode);

        // finally add to SceneGraph
        Gui.getActiveScenePanel().getSceneGraph().getTopNode().add(groupNode);
        Gui.getActiveScenePanel().getSceneGraph().fireSceneGraphUpdateEvent();
        Gui.getActiveScenePanel().sceneInvalidate(null);
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
