package cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.element.element.part.Part;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.menuBar.MenuBar;
import cz.cvut.fel.schematicEditor.guiAdvanced.guiElements.partPropertiesDialog.PartPropertiesDialog;

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
        PartPropertiesDialog ppd = new PartPropertiesDialog(null, true);
        // if dialog was accepted, modify part properties
        if (ppd.isAccepted()) {
            Part part = (Part) Gui.getActiveScenePanel().getActiveManipulation().getManipulatedGroup()
                    .getChildrenElementList().getFirst().getElement();
            // part.setPartProperties(PartPropertiesDialogPanel.getInstance().getPartProperties());
        }
    }
}
