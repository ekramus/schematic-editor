package cz.cvut.fel.schematicEditor.application.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.application.guiElements.menuBar.MenuBar;

/**
 * This class implements {@link ActionListener} for <code>exitMenuItem</code> in {@link MenuBar}.
 * 
 * @author Urban Kravjansky
 */
public final class ExitMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public ExitMenuItemListener() {
        super();
    }

    /**
     * Method invoked as result to an action. It initializes application termination.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * 
     * @param ae
     *            {@link ActionEvent} parameter. This parameter is only for implementing purposes,
     *            it is not used nor needed.
     */
    public void actionPerformed(final ActionEvent ae) {
        System.exit(0);
    }
}
