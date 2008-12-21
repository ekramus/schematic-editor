package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.element.ElementType;
import cz.cvut.fel.schematicEditor.graphNode.GroupNode;
import cz.cvut.fel.schematicEditor.graphNode.ParameterNode;
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
        MenuBar.getInstance().setEditPartMenuItemEnabled(false);
        MenuBar.getInstance().setDoneEditPartMenuItemEnabled(true);

        Select s;
        if (GuiAdvanced.getInstance().getSceneScenePanel().getActiveManipulation().getManipulationType() == ManipulationType.SELECT) {
            s = (Select) GuiAdvanced.getInstance().getSceneScenePanel().getActiveManipulation();
        } else {
            return;
        }

        if (s.getManipulatedGroup().getChildrenElementList().getFirst().getElement().getElementType() == ElementType.T_PART) {
            GroupNode gn = new GroupNode();
            gn.add(new ParameterNode());
            gn.add(s.getManipulatedGroup());
            GuiAdvanced.getInstance().getPartScenePanel().getSceneGraph().initSceneGraph(gn);

            // TODO create GroupNode from partGroupNode
            // TODO edit this GroupNode
            // TODO after done edit part, fill partGroupNode with modified GroupNode
        }
    }
}
