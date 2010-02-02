package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;

/**
 * This class implements {@link ActionListener} for <code>snapToGridCheckBoxMenuItem</code> in {@link MenuBar}.
 * 
 * @author Urban Kravjansky
 */
public final class SnapToGridCheckBoxMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public SnapToGridCheckBoxMenuItemListener() {
        super();
    }

    // FIXME add externalized strings

    /**
     * Method invoked as result to an action. It sets snap to grid parameter.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param e {@link ActionEvent} parameter. This parameter is used to obtain source object.
     */
    public void actionPerformed(ActionEvent e) {
        GuiConfiguration configuration = GuiConfiguration.getInstance();

        configuration.setSnapToGrid(((JCheckBoxMenuItem) e.getSource()).isSelected());
    }
}
