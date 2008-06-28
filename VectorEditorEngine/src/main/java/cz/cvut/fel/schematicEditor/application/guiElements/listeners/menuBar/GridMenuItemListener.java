package cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import cz.cvut.fel.schematicEditor.application.guiElements.MenuBar;
import cz.cvut.fel.schematicEditor.application.guiElements.resources.MenuBarResources;
import cz.cvut.fel.schematicEditor.core.Structures;
import cz.cvut.fel.schematicEditor.unit.UnitType;

/**
 * This class implements {@link ActionListener} for <code>gridMenuItem</code> in {@link MenuBar}.
 * 
 * @author Urban Kravjansky
 */
public final class GridMenuItemListener implements ActionListener {

    /**
     * Default constructor. It only calls constructor of super class.
     */
    public GridMenuItemListener() {
        super();
    }

    /**
     * Method invoked as result to an action. It shows {@link JOptionPane} instance to set grid size value.
     * 
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     * @param e
     *            {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(ActionEvent e) {
        String s = JOptionPane.showInputDialog(MenuBarResources.GRID_MENU_ITEM_DIALOG.getText(),
                                               Structures.getScenePanel().getGridSize());
        try {
            if (s != null) {
                Structures.getScenePanel().setGridSize(UnitType.parseUnit(s));
                Structures.getScenePanel().repaint();
            }
        } catch (NumberFormatException nfe) {
            System.err.println(nfe.getLocalizedMessage());
        }
    }
}
