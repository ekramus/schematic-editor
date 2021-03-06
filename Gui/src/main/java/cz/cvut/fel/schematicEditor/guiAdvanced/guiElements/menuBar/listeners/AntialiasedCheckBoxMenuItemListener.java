package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;

/**
 * This class implements {@link ActionListener} for <code>antialiasedCheckBoxMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public class AntialiasedCheckBoxMenuItemListener implements ActionListener {
    /**
     * Default constructor for {@link AntialiasedCheckBoxMenuItemListener} instance. It calls only super class
     * contructor.
     */
    public AntialiasedCheckBoxMenuItemListener() {
        super();
    }

    /**
     * Method invoked as result to an action. It sets global antialiasing status.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     *
     * @param ae {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public final void actionPerformed(final ActionEvent ae) {
        GuiConfiguration config = GuiConfiguration.getInstance();

        config.setSchemeAntialiased(((JCheckBoxMenuItem) ae.getSource()).isSelected());
        Gui.getActiveScenePanel().repaint();
    }
}
