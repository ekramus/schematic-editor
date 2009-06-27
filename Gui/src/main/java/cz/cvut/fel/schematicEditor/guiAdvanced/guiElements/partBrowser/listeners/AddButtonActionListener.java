package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.NodeFactory;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser.PartBrowserPanel;

/**
 * @author uk
 *
 */
public class AddButtonActionListener implements ActionListener {
    private static Logger logger;

    /**
     * This method instantiates new instance.
     *
     */
    public AddButtonActionListener() {
        logger = Logger.getLogger(this.getClass().getName());
    }

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        // prepare PartNode, GroupNode and ParameterNode
        PartNode partNode = (PartNode) NodeFactory.duplicate(PartBrowserPanel.getInstance().getSelectedPartNode());
        GroupNode groupNode = NodeFactory.createGroupNode();
        ParameterNode parameterNode = NodeFactory.createParameterNode();

        groupNode.add(partNode);
        groupNode.add(parameterNode);

        // finally add to SceneGraph
        Gui.getActiveScenePanel().getSceneGraph().getTopNode().add(groupNode);
        Gui.getActiveScenePanel().getSceneGraph().fireSceneGraphUpdateEvent();
        Gui.getActiveScenePanel().sceneInvalidate(null);
    }
}
