package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;

/**
 * This class implements {@link ActionListener} for <code>newSchemeMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class NewPartMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public NewPartMenuItemListener() {
        super();
    }

    /**
     * Method invoked as result to an action. It initializes creation of new scheme.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     *
     * @param ae {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(final ActionEvent ae) {
        int result = JOptionPane.showConfirmDialog(Gui.getInstance(), "Do you want to discard current part?",
                                                   "New part", JOptionPane.OK_CANCEL_OPTION,
                                                   JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            Gui.getInstance().getPartScenePanel().getSceneGraph().initSceneGraph();
            Gui.getInstance().getPartScenePanel().schemeInvalidate(null);
        }
    }
}
