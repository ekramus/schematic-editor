package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.graphNode.PartNode;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;

/**
 * This class implements {@link ActionListener} for <code>doneEditPartMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class DoneEditPartMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public DoneEditPartMenuItemListener() {
        super();
    }

    /**
     * Method invoked as result to an action. It initializes finalization of part edititng.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     *
     * @param ae {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(final ActionEvent ae) {
        PartNode pn = GuiAdvanced.getInstance().getSchemeScenePanel().getEditedPartNode();
        pn.initialize(GuiAdvanced.getInstance().getPartScenePanel().getSceneGraph().getTopNode());

        // finish editing part node
        GuiAdvanced.getInstance().getSchemeScenePanel().setEditedPartNode(null);

        // clean up part scene panel
        GuiAdvanced.getInstance().getPartScenePanel().getSceneGraph().initSceneGraph();
        GuiAdvanced.getInstance().getPartScenePanel().schemeInvalidate(null);

        // refresh scene scene panel
        GuiAdvanced.getInstance().getSchemeScenePanel().schemeInvalidate(null);

        // set correct menu bar items
        MenuBar.getInstance().setEditPartMenuItemEnabled(true);
        MenuBar.getInstance().setDoneEditPartMenuItemEnabled(false);
    }
}
