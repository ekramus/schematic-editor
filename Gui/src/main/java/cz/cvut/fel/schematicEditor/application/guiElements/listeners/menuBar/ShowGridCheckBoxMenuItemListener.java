package cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;

import cz.cvut.fel.schematicEditor.application.guiElements.MenuBar;
import cz.cvut.fel.schematicEditor.application.guiElements.ScenePanel;
import cz.cvut.fel.schematicEditor.properties.GuiConfiguration;

/**
 * This class implements {@link ActionListener} for <code>showGridCheckBoxMenuItem</code> in {@link MenuBar}.
 * 
 * @author Urban Kravjansky
 */
public final class ShowGridCheckBoxMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public ShowGridCheckBoxMenuItemListener() {
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

        configuration.setGridVisible(((JCheckBoxMenuItem) e.getSource()).isSelected());
        ScenePanel.getInstance().repaint();
    }
}
