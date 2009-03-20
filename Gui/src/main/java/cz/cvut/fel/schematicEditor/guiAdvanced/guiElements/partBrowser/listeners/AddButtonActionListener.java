package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        PartNode partNode = PartBrowserPanel.getInstance().getSelectedPartNode();
        GroupNode groupNode = new GroupNode();
        ParameterNode parameterNode = new ParameterNode();

        groupNode.add(partNode);
        groupNode.add(parameterNode);

        // finally add to SceneGraph
        Gui.getActiveScenePanel().getSceneGraph().getTopNode().add(groupNode);
        Gui.getActiveScenePanel().getSceneGraph().fireSceneGraphUpdateEvent();
        Gui.getActiveScenePanel().sceneInvalidate(null);
    }
}
