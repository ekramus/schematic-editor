package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.log4j.Logger;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.Select;
import cz.cvut.fel.schematicEditor.original.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.original.graphNode.NodeFactory;
import cz.cvut.fel.schematicEditor.original.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.original.graphNode.PinNode;

/**
 * This class implements {@link ActionListener} for <code>editPartMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class EditPartMenuItemListener implements ActionListener {
    private static Logger logger;

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public EditPartMenuItemListener() {
        super();

        logger = Logger.getLogger(this.getClass());
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
        if (Gui.getInstance().getSchemeScenePanel().getActiveManipulation().getManipulationType() == ManipulationType.SELECT) {
            s = (Select) Gui.getInstance().getSchemeScenePanel().getActiveManipulation();
        } else {
            return;
        }

        // is part selected?
        if (s.getManipulatedGroup().getChildrenElementList().getFirst().getElement().getElementType() == ElementType.T_PART) {
            GroupNode topNode = NodeFactory.createGroupNode();
            topNode.add(NodeFactory.createParameterNode());

            // create GroupNode from partGroupNode
            PartNode pn = (PartNode) s.getManipulatedGroup().getChildrenElementList().getFirst();
            GroupNode partGroupNode = (GroupNode) NodeFactory.duplicate(pn.getPartGroupNode());

            // add all connectors from part node into group node
            for (PinNode pinNode : pn.getPartPins()) {
                GroupNode gn = NodeFactory.createGroupNode();
                gn.add(NodeFactory.duplicate(pinNode));
                gn.add(NodeFactory.createParameterNode());
                partGroupNode.add(gn);
            }
            // set top node as root for edit scene panel
            topNode.add(partGroupNode);
            Gui.getInstance().getPartScenePanel().getSceneGraph().initSceneGraph(topNode);

            // set edited part node
            Gui.getInstance().getSchemeScenePanel().setEditedPartNode(pn);

            // set correct state of menu items
            MenuBar.getInstance().setEditPartMenuItemEnabled(false);
            MenuBar.getInstance().setDoneEditPartMenuItemEnabled(true);
        }
    }
}
