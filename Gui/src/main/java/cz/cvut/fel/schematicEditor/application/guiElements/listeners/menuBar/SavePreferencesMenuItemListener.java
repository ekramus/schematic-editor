package cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.application.guiElements.MenuBar;
import cz.cvut.fel.schematicEditor.properties.Configuration;
import cz.cvut.fel.schematicEditor.properties.EnvironmentConfiguration;
import cz.cvut.fel.schematicEditor.properties.GuiConfiguration;

/**
 * This class implements {@link ActionListener} for <code>savePreferencesMenuItem</code> in {@link MenuBar}.
 * 
 * @author Urban Kravjansky
 */
public final class SavePreferencesMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public SavePreferencesMenuItemListener() {
        super();
    }

    // FIXME add externalized strings

    /**
     * Method invoked as result to an action. It exports global properties.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param e {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(ActionEvent e) {
        Configuration.serialize(GuiConfiguration.getInstance(), GuiConfiguration.getFile());
        Configuration.serialize(EnvironmentConfiguration.getInstance(), EnvironmentConfiguration.getFile());
    }
}
