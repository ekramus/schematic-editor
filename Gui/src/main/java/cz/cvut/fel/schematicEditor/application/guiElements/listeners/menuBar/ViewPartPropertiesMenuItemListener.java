package cz.cvut.fel.schematicEditor.application.guiElements.listeners.menuBar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.application.guiElements.MenuBar;
import cz.cvut.fel.schematicEditor.application.guiElements.PartPropertiesDialog;

/**
 * This class implements {@link ActionListener} for <code>viewPartPropertiesMenuItem</code> in {@link MenuBar}.
 *
 * @author Urban Kravjansky
 */
public class ViewPartPropertiesMenuItemListener implements ActionListener {

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        PartPropertiesDialog ppd = new PartPropertiesDialog(null, false);
        ppd.setVisible(true);
    }
}
