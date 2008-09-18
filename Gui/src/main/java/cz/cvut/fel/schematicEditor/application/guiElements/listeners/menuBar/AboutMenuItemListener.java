package cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;

import cz.cvut.fel.schematicEditor.application.guiElements.AboutDialog;
import cz.cvut.fel.schematicEditor.application.guiElements.MenuBar;

/**
 * This class implements {@link ActionListener} for <code>aboutMenuItem</code> in {@link MenuBar}.
 * 
 * @author Urban Kravjansky
 */
public final class AboutMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public AboutMenuItemListener() {
        super();
    }

    /**
     * Method invoked as result to an action. It initializes new {@link AboutDialog} instance.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * 
     * @param ae
     *            {@link ActionEvent} parameter. This parameter is only for implementing purposes,
     *            it is not used nor needed.
     */
    public void actionPerformed(final ActionEvent ae) {
        JDialog aboutDialog = AboutDialog.getInstance();
        aboutDialog.pack();
        aboutDialog.setVisible(true);
    }
}
