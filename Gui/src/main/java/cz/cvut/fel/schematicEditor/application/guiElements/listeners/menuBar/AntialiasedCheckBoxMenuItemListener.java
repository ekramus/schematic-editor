package cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

import cz.cvut.fel.schematicEditor.application.guiElements.MenuBar;
import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanel;
import cz.cvut.fel.schematicEditor.properties.GuiConfiguration;

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
        ScenePanel.getInstance().setSchemeAntialiased(config.isSchemeAntialiased());
        ScenePanel.getInstance().repaint();
    }
}
