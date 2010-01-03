package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partBrowser.PartBrowserPanel;
import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.original.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.original.graphNode.NodeFactory;
import cz.cvut.fel.schematicEditor.original.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.original.graphNode.PartNode;

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

        Manipulation m;

        try {
            // set move manipulation as active manipulation
            m = ManipulationFactory.create(ManipulationType.MOVE, Gui.getActiveScenePanel().getSceneGraph()
                    .getTopNode(), this);
            m.setManipulatedGroup(groupNode);
            m.setActive(true);

            // add start and stop coordinates
            m.addManipulationCoordinates(partNode.getRotationCenter().getUnitX(), partNode.getRotationCenter()
                    .getUnitY());
            m.addManipulationCoordinates(partNode.getRotationCenter().getUnitX(), partNode.getRotationCenter()
                    .getUnitY());

            // set manipulation as active
            Gui.getActiveScenePanel().setActiveManipulation(m);
        } catch (UnknownManipulationException ume) {
            logger.error("Unknown manipulation");
        }
    }
}
