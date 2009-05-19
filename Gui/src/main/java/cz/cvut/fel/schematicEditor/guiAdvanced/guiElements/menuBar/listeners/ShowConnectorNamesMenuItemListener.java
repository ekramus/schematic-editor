package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;

/**
 * This class implements {@link ActionListener} for <code>showConnectorNamesMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public final class ShowConnectorNamesMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public ShowConnectorNamesMenuItemListener() {
        super();
    }

    // FIXME add externalized strings

    /**
     * Method invoked as result to an action. It sets grid visible parameter.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param e {@link ActionEvent} parameter. This parameter is used to obtain source object.
     */
    public void actionPerformed(ActionEvent e) {
        GuiConfiguration configuration = GuiConfiguration.getInstance();

        configuration.setConnectorNamesVisible(((JCheckBoxMenuItem) e.getSource()).isSelected());
        Gui.getActiveScenePanel().repaint();
    }
}
