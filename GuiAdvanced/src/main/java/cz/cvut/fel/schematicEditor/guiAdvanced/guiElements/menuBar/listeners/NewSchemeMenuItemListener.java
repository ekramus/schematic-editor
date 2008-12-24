package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.guiAdvanced.GuiAdvanced;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;

/**
 * This class implements {@link ActionListener} for <code>newSchemeMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class NewSchemeMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public NewSchemeMenuItemListener() {
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
        int result = JOptionPane.showConfirmDialog(GuiAdvanced.getInstance(), "Do you want to discard current scheme?",
                                                   "New scheme", JOptionPane.OK_CANCEL_OPTION,
                                                   JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            GuiAdvanced.getInstance().getSchemeScenePanel().getSceneGraph().initSceneGraph();
            GuiAdvanced.getInstance().getSchemeScenePanel().schemeInvalidate(null);
        }
    }
}
