package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.graphNode.ConnectorNode;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.Select;

/**
 * This class implements {@link ActionListener} for <code>editPartMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class EditPartMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public EditPartMenuItemListener() {
        super();
    }

    /**
     * Method invoked as result to an action. It initializes part editing.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     *
     * @param ae {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(final ActionEvent ae) {
        Select s;
        // is select active?
        if (GuiAdvanced.getInstance().getSchemeScenePanel().getActiveManipulation().getManipulationType() == ManipulationType.SELECT) {
            s = (Select) GuiAdvanced.getInstance().getSchemeScenePanel().getActiveManipulation();
        } else {
            return;
        }

        // is part selected?
        if (s.getManipulatedGroup().getChildrenElementList().getFirst().getElement().getElementType() == ElementType.T_PART) {
            GroupNode topNode = new GroupNode();
            topNode.add(new ParameterNode());

            // create GroupNode from partGroupNode
            PartNode pn = (PartNode) s.getManipulatedGroup().getChildrenElementList().getFirst();
            GroupNode partGroupNode = (GroupNode) pn.getPartGroupNode().duplicate();

            // add all connectors from part node into group node
            for (ConnectorNode connectorNode : pn.getPartConnectors()) {
                GroupNode gn = new GroupNode();
                gn.add(connectorNode.duplicate());
                gn.add(new ParameterNode());
                partGroupNode.add(gn);
            }
            // set top node as root for edit scene panel
            topNode.add(partGroupNode);
            GuiAdvanced.getInstance().getPartScenePanel().getSceneGraph().initSceneGraph(topNode);

            // set edited part node
            GuiAdvanced.getInstance().getSchemeScenePanel().setEditedPartNode(pn);

            // set correct state of menu items
            MenuBar.getInstance().setEditPartMenuItemEnabled(false);
            MenuBar.getInstance().setDoneEditPartMenuItemEnabled(true);
        }
    }
}
