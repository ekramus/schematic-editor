package cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.propertiesPanel.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cz.cvut.fel.schematicEditor.manipulation.Manipulation;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationFactory;
import cz.cvut.fel.schematicEditor.manipulation.ManipulationType;
import cz.cvut.fel.schematicEditor.manipulation.exception.UnknownManipulationException;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.gui.Gui;
import cz.cvut.fel.schematicEditor.piccolo.guiAdvanced.guiElements.propertiesPanel.PartPropertiesPanel;

/**
 * This class implements listener for {@link PartPropertiesDialogPanel} partRotationCenterButton.
 *
 * @author Urban Kravjansky
 */
public class PartRotationCenterButtonActionListener implements ActionListener {

    /**
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (Gui.getActiveScenePanel().getActiveManipulation().getManipulationType() == ManipulationType.SELECT) {
            if (Gui.getActiveScenePanel().getActiveManipulation().getManipulatedGroup() != null) {
                try {
                    Manipulation m = ManipulationFactory.create(ManipulationType.SELECT_ROTATION_CENTER, Gui
                            .getActiveScenePanel().getSceneGraph().getTopNode(), e.getSource());
                    m.setManipulatedGroup(Gui.getActiveScenePanel().getActiveManipulation()
                            .getManipulatedGroup());
                    m.setActive(true);

                    Gui.getActiveScenePanel().setActiveManipulation(m);
                    // Structures.getManipulationQueue().execute(m);
                    //
                    // ScenePanel.getInstance().schemeInvalidate(null);
                } catch (UnknownManipulationException ume) {
                    ume.printStackTrace();
                }
            }
        }
    }
}
