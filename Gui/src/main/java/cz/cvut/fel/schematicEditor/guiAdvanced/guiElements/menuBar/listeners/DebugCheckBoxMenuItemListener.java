package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;

/**
 * This class implements {@link ActionListener} for <code>debugCheckBoxMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public class DebugCheckBoxMenuItemListener implements ActionListener {
    /**
     * Default constructor for {@link DebugCheckBoxMenuItemListener} instance. It calls only super class contructor.
     */
    public DebugCheckBoxMenuItemListener() {
        super();
    }

    /**
     * Method invoked as result to an action. It sets global debugging status.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param ae {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public final void actionPerformed(final ActionEvent ae) {
        GuiConfiguration config = GuiConfiguration.getInstance();

        config.setSchemeDebugged(((JCheckBoxMenuItem) ae.getSource()).isSelected());
        Gui.getActiveScenePanel().repaint();
    }
}
