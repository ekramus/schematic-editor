package cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import cz.cvut.fel.schematicEditor.configuration.GuiConfiguration;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.menuBar.MenuBar;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.menuBar.resources.MenuBarResources;
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
     * @param e {@link ActionEvent} parameter. This parameter is only for implementing purposes, it is not used nor
     *            needed.
     */
    public void actionPerformed(ActionEvent e) {
        GuiConfiguration configuration = GuiConfiguration.getInstance();
        String s = JOptionPane.showInputDialog(MenuBarResources.GRID_MENU_ITEM_DIALOG.getText(), configuration
                .getGridSize());
        try {
            if (s != null) {
                configuration.setGridSize(UnitType.parseUnit(s));
                Gui.getActiveScenePanel().setGridValid(false);
                Gui.getActiveScenePanel().repaint();
            }
        } catch (NumberFormatException nfe) {
            System.err.println(nfe.getLocalizedMessage());
        }
    }
}
